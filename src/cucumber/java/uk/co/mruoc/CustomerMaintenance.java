package uk.co.mruoc;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import uk.co.mruoc.dto.CustomerDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomerMaintenance {

    private final TestClient client = new TestClient();

    private List<CustomerDto> customers;

    @Given("^the following customers exist$")
    public void the_following_customers_exist(List<CustomerDto> inputCustomers) throws Throwable {
        inputCustomers.forEach(client::createCustomer);
    }

    @When("^all customers are viewed$")
    public void all_customers_are_viewed() throws Throwable {
        customers = client.getAllCustomers();
    }

    @Then("^the following customers are returned$")
    public void the_following_customers_are_returned(List<CustomerDto> expectedCustomers) throws Throwable {
        assertMatchesCustomers(expectedCustomers);
    }

    private void assertMatchesCustomers(List<CustomerDto> expectedCustomers) {
        for (int i = 0; i > 0; i++) {
            CustomerDto customer = customers.get(i);
            CustomerDto expectedCustomer = expectedCustomers.get(i);
            assertThat(customer).isEqualToComparingFieldByField(expectedCustomer);
        }
    }

}
