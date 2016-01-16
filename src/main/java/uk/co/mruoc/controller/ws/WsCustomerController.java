package uk.co.mruoc.controller.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import uk.co.mruoc.exception.CustomerNotFoundException;
import uk.co.mruoc.exception.InvalidCustomerIdException;
import uk.co.mruoc.dto.CustomerDto;
import uk.co.mruoc.dto.ErrorDto;
import uk.co.mruoc.exception.CustomerIdAlreadyUsedException;
import uk.co.mruoc.facade.CustomerFacade;
import uk.co.mruoc.facade.DefaultCustomerFacade;

import java.net.URI;
import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import static uk.co.mruoc.controller.ws.RestUrl.BASE_URL;

@RestController
@RequestMapping(BASE_URL)
public class WsCustomerController {

    private static final String MULTIPLE_ENDPOINT = "/customers";
    private static final String SINGLE_ENDPOINT = "/customers/{id}";

    @Autowired
    private CustomerFacade customerFacade;

    public void setCustomerFacade(CustomerFacade customerFacade) {
        this.customerFacade = customerFacade;
    }

    @RequestMapping(value = MULTIPLE_ENDPOINT, method = GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CustomerDto>> getCustomers(
            @RequestParam(value = "limit", required = false, defaultValue = "20") int limit,
            @RequestParam(value = "offset", required = false, defaultValue = "0") int offset) {
        List<CustomerDto> customers = customerFacade.getCustomers(limit, offset);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", Integer.toString(customerFacade.getNumberOfCustomers()));
        return new ResponseEntity<>(customers, headers, OK);
    }

    @RequestMapping(value = SINGLE_ENDPOINT, method = GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCustomer(@PathVariable("id") String id) {
        try {
            CustomerDto customer = customerFacade.getCustomer(id);
            return new ResponseEntity<>(customer, OK);
        } catch (CustomerNotFoundException e) {
            return handleCustomerDoesNotExist(e);
        }
    }

    @RequestMapping(value = MULTIPLE_ENDPOINT, method = POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createCustomer(@RequestBody CustomerDto customer, UriComponentsBuilder uriBuilder) {
        try {
            return handleCreateCustomer(customer, uriBuilder);
        } catch (CustomerIdAlreadyUsedException e) {
            return handleDuplicateCustomerId(e);
        } catch (InvalidCustomerIdException e) {
            return handleInvalidCustomerId(e);
        }
    }

    @RequestMapping(value = MULTIPLE_ENDPOINT, method = PUT, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateCustomer(@RequestBody CustomerDto customer) {
        try {
            customerFacade.updateCustomer(customer);
            return new ResponseEntity<>(customer, OK);
        } catch (CustomerNotFoundException e) {
            return handleCustomerDoesNotExist(e);
        }
    }

    @RequestMapping(value = SINGLE_ENDPOINT, method = DELETE)
    public ResponseEntity<?> deleteCustomer(@PathVariable("id") String id) {
        try {
            customerFacade.deleteCustomer(id);
            return new ResponseEntity<>(NO_CONTENT);
        } catch (CustomerNotFoundException e) {
            return new ResponseEntity<>(NO_CONTENT);
        }
    }

    private ResponseEntity<?> handleCreateCustomer(CustomerDto customer, UriComponentsBuilder uriBuilder) {
        customerFacade.createCustomer(customer);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(getNewCustomerLocationHeader(uriBuilder, customer));
        return new ResponseEntity<>(customer, headers, CREATED);
    }

    private ResponseEntity<?> handleDuplicateCustomerId(Throwable t) {
        ErrorDto error = buildError(t);
        return new ResponseEntity<>(error, CONFLICT);
    }

    private ResponseEntity<?> handleInvalidCustomerId(Throwable t) {
        ErrorDto error = buildError(t);
        return new ResponseEntity<>(error, UNPROCESSABLE_ENTITY);
    }

    private ResponseEntity<?> handleCustomerDoesNotExist(Throwable t) {
        ErrorDto error = buildError(t);
        return new ResponseEntity<>(error, NOT_FOUND);
    }

    private ErrorDto buildError(Throwable t) {
        ErrorDto error = new ErrorDto();
        error.setMessage(t.getMessage());
        return error;
    }

    private URI getNewCustomerLocationHeader(UriComponentsBuilder uriBuilder, CustomerDto dto) {
        UriComponents uriComponents = uriBuilder.path(BASE_URL + SINGLE_ENDPOINT).buildAndExpand(dto.getId());
        return uriComponents.toUri();
    }

}