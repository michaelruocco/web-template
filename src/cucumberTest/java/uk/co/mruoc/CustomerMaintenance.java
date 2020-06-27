package uk.co.mruoc;

import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.HttpStatus;
import uk.co.mruoc.controller.dto.CustomerDto;
import uk.co.mruoc.controller.dto.ErrorDto;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomerMaintenance {

    private final TestClient client = new TestClient();

    private CustomerResponse customerResponse;
    private CustomerDto newCustomer;
    private CustomerDto updateCustomer;
    private String deleteId;

    @DataTableType
    public CustomerDto defineCustomer(Map<String, String> entry) {
        CustomerDto customer = new CustomerDto();
        customer.setId(entry.get("id"));
        customer.setFirstName(entry.get("firstName"));
        customer.setSurname(entry.get("surname"));
        customer.setBalance(entry.get("balance"));
        return customer;
    }

    @Given("^the following customers exist$")
    public void the_following_customers_exist(List<CustomerDto> inputCustomers) {
        inputCustomers.forEach(client::createCustomer);
    }

    @Given("^we have a new customer to create with the following data$")
    public void we_have_a_new_customer_to_create_with_the_following_data(List<CustomerDto> inputCustomers) {
        this.newCustomer = inputCustomers.get(0);
    }

    @Given("^the customer data is posted$")
    public void the_customer_data_is_posted() {
        customerResponse = client.createCustomer(newCustomer);
    }

    @Given("^no customer exists with id \"([^\"]*)\"$")
    public void no_customer_exists_with_id(String id) {
        CustomerResponse response = client.getCustomer(id);
        if (response.getStatusCode() == HttpStatus.OK.value())
            client.deleteCustomer(id);
    }

    @Given("^we want to update the customer data to$")
    public void we_want_to_update_the_customer_data_to(List<CustomerDto> inputCustomers) {
        this.updateCustomer = inputCustomers.get(0);
    }

    @Given("^we want to delete the customer with id \"([^\"]*)\"$")
    public void we_want_to_delete_the_customer_with_id(String deleteId) {
        this.deleteId = deleteId;
    }

    @When("^a get request is made for all customers$")
    public void a_get_request_is_made_for_all_customers() {
        customerResponse = client.getCustomers();
    }

    @When("^a get request is made for all customers page (\\d+) with size (\\d+)$")
    public void a_get_request_is_made_for_all_customers_page_with_size(int pageNumber, int pageSize) {
        customerResponse = client.getCustomers(pageSize, pageNumber);
    }

    @When("^a get request is made for customer \"([^\"]*)\"$")
    public void a_get_request_is_made_for_customer(String id) {
        customerResponse = client.getCustomer(id);
    }

    @When("^the customer data is updated")
    public void the_customer_data_is_updated() {
        customerResponse = client.updateCustomer(updateCustomer);
    }

    @When("^the customer data is deleted$")
    public void the_customer_data_is_deleted() {
        customerResponse = client.deleteCustomer(deleteId);
    }

    @Then("^the following customers are returned$")
    public void the_following_customers_are_returned(List<CustomerDto> expectedCustomers) {
        assertMatchesReturnedCustomers(expectedCustomers);
    }

    @Then("^the following customer is returned$")
    public void the_following_customer_is_returned(List<CustomerDto> expectedCustomers) {
        CustomerDto expectedCustomer = expectedCustomers.get(0);
        assertThat(customerResponse.getCustomer()).isEqualToComparingOnlyGivenFields(expectedCustomer);
    }

    @Then("^the service returns a response code (\\d+)$")
    public void the_service_returns_a_response_code(int expectedResponseCode) {
        assertThat(customerResponse.getStatusCode()).isEqualTo(expectedResponseCode);
    }

    @Then("^the service returns error message \"([^\"]*)\"$")
    public void the_service_returns_error_message(String expectedMessage) {
        ErrorDto error = customerResponse.getError();
        assertThat(error.getMessage()).isEqualTo(expectedMessage);
    }

    @Then("^the response header contains \"([^\"]*)\" with value \"([^\"]*)\"$")
    public void the_response_header_contains_with_value(String headerName, String expectedHeaderValue) {
        String headerValue = customerResponse.getHeader(headerName);
        assertThat(headerValue).isEqualTo(expectedHeaderValue);
    }

    private void assertMatchesReturnedCustomers(List<CustomerDto> expectedCustomers) {
        List<CustomerDto> customers = customerResponse.getCustomers();
        assertThat(customers.size()).isEqualTo(expectedCustomers.size());
        for (int i = 0; i < expectedCustomers.size(); i++) {
            CustomerDto customer = customers.get(i);
            CustomerDto expectedCustomer = expectedCustomers.get(i);
            assertThat(customer).isEqualToComparingFieldByField(expectedCustomer);
        }
    }

}
