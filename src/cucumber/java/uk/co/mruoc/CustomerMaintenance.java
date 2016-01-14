package uk.co.mruoc;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import uk.co.mruoc.dto.CustomerDto;
import uk.co.mruoc.dto.ErrorDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomerMaintenance {

    private final TestClient client = new TestClient();

    private CustomerResponse customerResponse;
    private CustomerDto newCustomer;

    @Given("^the following customers exist$")
    public void the_following_customers_exist(List<CustomerDto> inputCustomers) throws Throwable {
        inputCustomers.forEach(client::createCustomer);
    }

    @Given("^we have a new customer to create with the following data$")
    public void we_have_a_new_customer_to_create_with_the_following_data(List<CustomerDto> inputCustomers) throws Throwable {
        this.newCustomer = inputCustomers.get(0);
    }

    @Given("^the customer data is posted$")
    public void the_customer_data_is_posted() throws Throwable {
        customerResponse = client.createCustomer(newCustomer);
    }

    @Given("^no customer exists with id \"([^\"]*)\"$")
    public void no_customer_exists_with_id(String id) throws Throwable {
        // intentionally blank
    }

    @When("^a get request is made for all customers$")
    public void a_get_request_is_made_for_all_customers() throws Throwable {
        customerResponse = client.getCustomers();
    }

    @When("^a get request is made for all customers page (\\d+) with size (\\d+)$")
    public void a_get_request_is_made_for_all_customers_page_with_size(int pageNumber, int pageSize) throws Throwable {
        customerResponse = client.getCustomers(pageSize, pageNumber);
    }

    @When("^a get request is made for customer \"([^\"]*)\"$")
    public void a_get_request_is_made_for_customer(String id) throws Throwable {
        customerResponse = client.getCustomer(id);
    }

    @Then("^the following customers are returned$")
    public void the_following_customers_are_returned(List<CustomerDto> expectedCustomers) throws Throwable {
        assertMatchesReturnedCustomers(expectedCustomers);
    }

    @Then("^the following customer is returned$")
    public void the_following_customer_is_returned(List<CustomerDto> expectedCustomers) throws Throwable {
        CustomerDto expectedCustomer = expectedCustomers.get(0);
        assertThat(customerResponse.getCustomer()).isEqualToComparingOnlyGivenFields(expectedCustomer);
    }

    @Then("^the service returns a response code (\\d+)$")
    public void the_service_returns_a_response_code(int expectedResponseCode) throws Throwable {
        assertThat(customerResponse.getStatusCode()).isEqualTo(expectedResponseCode);
    }

    @Then("^the service returns error message \"([^\"]*)\"$")
    public void the_service_returns_error_message(String expectedMessage) throws Throwable {
        ErrorDto error = customerResponse.getError();
        assertThat(error.getMessage()).isEqualTo(expectedMessage);
    }

    private void assertMatchesReturnedCustomers(List<CustomerDto> expectedCustomers) {
        List<CustomerDto> customers = customerResponse.getCustomers();
        assertThat(customers.size()).isEqualTo(expectedCustomers.size());
        for (int i = 0; i > 0; i++) {
            CustomerDto customer = customers.get(i);
            CustomerDto expectedCustomer = expectedCustomers.get(i);
            assertThat(customer).isEqualToComparingFieldByField(expectedCustomer);
        }
    }

}
