Feature: Customer Maintenance

  Scenario: Get all customers
    Given the following customers exist
      | id     | firstName | surname | balance |
      | 000000 | Michael   | Ruocco  | 10000   |
      | 000001 | Kiel      | Boatman | 5000    |
      | 000002 | Craig     | Betts   | 7500    |
    When a get request is made for all customers
    Then the service returns a response code 200
    And the following customers are returned
      | id     | firstName | surname | balance |
      | 000000 | Michael   | Ruocco  | 10000   |
      | 000001 | Kiel      | Boatman | 5000    |
      | 000002 | Craig     | Betts   | 7500    |
    And the response header contains "X-Total-Count" with value "3"

  Scenario: Get all customers paged
    Given the following customers exist
      | id     | firstName | surname | balance |
      | 000000 | Michael   | Ruocco  | 10000   |
      | 000001 | Kiel      | Boatman | 5000    |
      | 000002 | Craig     | Betts   | 7500    |
      | 000003 | Dominic   | Bishop  | 12345   |
      | 000004 | Richard   | Windley | 54321   |
      | 000005 | Derek     | Clark   | 33333   |
    When a get request is made for all customers page 1 with size 3
    Then the service returns a response code 200
    And the following customers are returned
      | id     | firstName | surname | balance |
      | 000000 | Michael   | Ruocco  | 10000   |
      | 000001 | Kiel      | Boatman | 5000    |
      | 000002 | Craig     | Betts   | 7500    |
    And the response header contains "X-Total-Count" with value "6"

  Scenario: Get single customer
    Given the following customers exist
      | id     | firstName | surname | balance |
      | 000007 | Micky     | Mouse   | 99999   |
    When a get request is made for customer "000007"
    Then the service returns a response code 200
    And the following customer is returned
      | id     | firstName | surname | balance |
      | 000007 | Micky     | Mouse   | 99999   |

  Scenario: Get single customer that does not exist
    Given no customer exists with id "999999"
    When a get request is made for customer "999999"
    Then the service returns a response code 404
    And the service returns error message "customer id 999999 does not exist"

  Scenario: Create customer
    Given we have a new customer to create with the following data
      | id     | firstName | surname | balance |
      | 000008 | Chris     | Stone   | 22222   |
    When the customer data is posted
    Then the service returns a response code 201
    And the following customer is returned
      | id     | firstName | surname | balance |
      | 000008 | Chris     | Stone   | 22222   |
    And the response header contains "Location" with value "http://localhost:8080/web-template/ws/v1/customers/000008"

  Scenario: Attempt to create customer with duplicate id
    Given the following customers exist
      | id     | firstName | surname | balance |
      | 000009 | Dean      | Heatlie | 33333   |
    And we have a new customer to create with the following data
      | id     | firstName | surname | balance |
      | 000009 | Dean      | Heatlie | 33333   |
    When the customer data is posted
    Then the service returns a response code 409
    And the service returns error message "customer id 000009 already in use"

  Scenario: Attempt to create customer with invalid customer id
    Given we have a new customer to create with the following data
      | id      | firstName | surname | balance |
      | 0000099 | Dean      | Heatlie | 33333   |
    When the customer data is posted
    Then the service returns a response code 422
    And the service returns error message "customer id 0000099 is not valid, it must be 6 digits"

  Scenario: Attempt to create customer with no name
    Given no customer exists with id "000009"
    And we have a new customer to create with the following data
      | id     | firstName | surname | balance |
      | 000009 |           |         | 33333   |
    When the customer data is posted
    Then the service returns a response code 422
    And the service returns error message "customer name  is not valid, customer must have a first name or surname"

  Scenario: Attempt to create customer with non numeric balance
    Given no customer exists with id "000009"
    And we have a new customer to create with the following data
      | id     | firstName | surname | balance |
      | 000009 | Dean      | Heatlie | test    |
    When the customer data is posted
    Then the service returns a response code 422
    And the service returns error message "customer balance test is not valid, it must be a numeric value"

  Scenario: Update customer
    Given the following customers exist
      | id     | firstName | surname | balance |
      | 000009 | Dean      | Heatlie | 33333   |
    And we want to update the customer data to
      | id     | firstName | surname  | balance |
      | 000009 | Updated   | Customer | 77777   |
    When the customer data is updated
    Then the service returns a response code 200
    And the following customer is returned
      | id     | firstName | surname  | balance |
      | 000009 | Updated   | Customer | 77777   |

  Scenario: Update customer with no name
    Given the following customers exist
      | id     | firstName | surname | balance |
      | 000009 | Dean      | Heatlie | 33333   |
    And we want to update the customer data to
      | id     | firstName | surname | balance |
      | 000009 |           |         | 77777   |
    When the customer data is updated
    Then the service returns a response code 422
    And the service returns error message "customer name  is not valid, customer must have a first name or surname"

  Scenario: Update customer with non numeric balance
    Given the following customers exist
      | id     | firstName | surname | balance |
      | 000009 | Dean      | Heatlie | 33333   |
    And we want to update the customer data to
      | id     | firstName | surname  | balance |
      | 000009 | Updated   | Customer | test    |
    When the customer data is updated
    Then the service returns a response code 422
    And the service returns error message "customer balance test is not valid, it must be a numeric value"

  Scenario: Update customer that does not exist
    Given no customer exists with id "000010"
    And we want to update the customer data to
      | id     | firstName | surname  | balance |
      | 000010 | Updated   | Customer | 77777   |
    When the customer data is updated
    Then the service returns a response code 404
    And the service returns error message "customer id 000010 does not exist"

  Scenario: Delete customer
    Given the following customers exist
      | id     | firstName | surname | balance |
      | 000011 | Laura     | Noble   | 88888   |
    And we want to delete the customer with id "000011"
    When the customer data is deleted
    Then the service returns a response code 204

  Scenario: Delete customer that does not exist
    Given no customer exists with id "000012"
    And we want to delete the customer with id "000012"
    When the customer data is deleted
    Then the service returns a response code 204