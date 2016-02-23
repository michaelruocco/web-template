package uk.co.mruoc.facade;

import uk.co.mruoc.service.CustomerService;
import uk.co.mruoc.controller.CustomerDto;
import uk.co.mruoc.model.Customer;

import java.util.List;

public class DefaultCustomerFacade implements CustomerFacade {

    private final CustomerService customerService;
    private final CustomerConverter customerConverter = new CustomerConverter();

    public DefaultCustomerFacade(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public List<CustomerDto> getCustomers() {
        List<Customer> customers = customerService.getCustomers();
        return customerConverter.convert(customers);
    }

    @Override
    public List<CustomerDto> getCustomers(int limit, int offset) {
        List<Customer> customers = customerService.getCustomers(limit, offset);
        return customerConverter.convert(customers);
    }

    @Override
    public int getNumberOfCustomers() {
        return customerService.getNumberOfCustomers();
    }

    @Override
    public CustomerDto getCustomer(String id) {
        Customer customer = customerService.getCustomer(id);
        return customerConverter.convert(customer);
    }

    @Override
    public void createCustomer(CustomerDto dto) {
        Customer customer = customerConverter.convert(dto);
        customerService.create(customer);
    }

    @Override
    public void updateCustomer(CustomerDto dto) {
        Customer customer = customerConverter.convert(dto);
        customerService.update(customer);
    }

    @Override
    public void deleteCustomer(String id) {
        customerService.delete(id);
    }

}
