package uk.co.mruoc.ws;

import uk.co.mruoc.dto.CustomerDto;
import uk.co.mruoc.facade.CustomerFacade;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MockCustomerFacade implements CustomerFacade {

    private final List<CustomerDto> customers;

    public MockCustomerFacade() {
        customers = new ArrayList<>();
        customers.add(createCustomer("000001", "Customer", "1", 1));
        customers.add(createCustomer("000002", "Customer", "2", 2));
        customers.add(createCustomer("000003", "Customer", "3", 3));
        customers.add(createCustomer("000004", "Customer", "4", 4));
        customers.add(createCustomer("000005", "Customer", "5", 5));
        customers.add(createCustomer("000006", "Customer", "6", 6));
    }

    @Override
    public List<CustomerDto> getCustomers() {
        return customers;
    }

    @Override
    public List<CustomerDto> getCustomers(int limit, int offset) {
        if (limit >= customers.size())
            return customers;
        return customers.subList(offset, offset + limit);
    }

    @Override
    public int getNumberOfCustomers() {
        return customers.size();
    }

    @Override
    public CustomerDto getCustomer(String id) {
        return null;
    }

    @Override
    public void createCustomer(CustomerDto customer) {
        customers.add(customer);
    }

    @Override
    public void updateCustomer(CustomerDto dto) {

    }

    @Override
    public void deleteCustomer(String id) {

    }

    private CustomerDto createCustomer(String id, String firstName, String surname, int balance) {
        CustomerDto customer = new CustomerDto();
        customer.setId(id);
        customer.setFirstName(firstName);
        customer.setSurname(surname);
        customer.setBalance(new BigDecimal(balance));
        return customer;
    }

}
