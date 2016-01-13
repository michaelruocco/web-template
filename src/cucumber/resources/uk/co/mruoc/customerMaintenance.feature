Feature: Customer Maintenance

  Scenario: List all customers
    Given the following customers exist
      | id     | firstName | surname | balance |
      | 000000 | Michael   | Ruocco  | 10000   |
      | 000001 | Kiel      | Boatman | 5000    |
      | 000002 | Craig     | Betts   | 7500    |
    When all customers are viewed
    Then the following customers are returned
      | id     | firstName | surname | balance |
      | 000000 | Michael   | Ruocco  | 10000   |
      | 000001 | Kiel      | Boatman | 5000    |
      | 000002 | Craig     | Betts   | 7500    |