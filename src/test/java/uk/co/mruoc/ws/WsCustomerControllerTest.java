package uk.co.mruoc.ws;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uk.co.mruoc.controller.ws.WsCustomerController;
import uk.co.mruoc.dto.CustomerDto;
import uk.co.mruoc.facade.CustomerFacade;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class WsCustomerControllerTest {

    private final CustomerFacade customerFacade = new MockCustomerFacade();
    private final WsCustomerController controller = new WsCustomerController();

    @Before
    public void setUp() {
        controller.setCustomerFacade(customerFacade);
    }

    @Test
    public void shouldReturnCustomers() {
        int limit = 20;
        int offset = 0;
        ResponseEntity<List<CustomerDto>> response = controller.getCustomers(limit, offset);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getTotalCount(response)).isEqualTo(customerFacade.getNumberOfCustomers());
        assertMatches(response.getBody(), customerFacade.getCustomers());
    }

    @Test
    public void shouldReturnPagedCustomers() {
        int limit = 3;
        int offset = 3;
        ResponseEntity<List<CustomerDto>> response = controller.getCustomers(limit, offset);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getTotalCount(response)).isEqualTo(customerFacade.getNumberOfCustomers());
        assertMatches(response.getBody(), customerFacade.getCustomers(limit, offset));
    }

    private void assertMatches(List<CustomerDto> customers, List<CustomerDto> expectedCustomers) {
        assertThat(customers.size()).isEqualTo(expectedCustomers.size());
        for (int i = 0; i < expectedCustomers.size(); i++) {
            CustomerDto customer = customers.get(i);
            CustomerDto expectedCustomer = expectedCustomers.get(i);
            assertThat(customer).isEqualToComparingFieldByField(expectedCustomer);
        }
    }

    private int getTotalCount(ResponseEntity<List<CustomerDto>> response) {
        HttpHeaders headers = response.getHeaders();
        String value = headers.get("X-Total-Count").get(0);
        return Integer.parseInt(value);
    }

}
