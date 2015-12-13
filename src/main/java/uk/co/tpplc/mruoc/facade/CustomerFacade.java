package uk.co.tpplc.mruoc.facade;

import uk.co.tpplc.mruoc.converter.CustomerConverter;
import uk.co.tpplc.mruoc.dto.CustomerDto;
import uk.co.tpplc.mruoc.model.Customer;
import uk.co.tpplc.mruoc.service.CustomerService;
import uk.co.tpplc.mruoc.service.MysqlCustomerService;

import java.util.List;

public class CustomerFacade {

    private final CustomerService customerService;
    private final CustomerConverter customerConverter = new CustomerConverter();

    public CustomerFacade(CustomerService customerService) {
        this.customerService = customerService;
    }

    public List<CustomerDto> getCustomers() {
        List<Customer> customers = customerService.getCustomers();
        return customerConverter.convert(customers);
    }

    public List<CustomerDto> getCustomers(int limit, int offset) {
        List<Customer> customers = customerService.getCustomers(limit, offset);
        return customerConverter.convert(customers);
    }

    public int getNumberOfCustomers() {
        return customerService.getNumberOfCustomers();
    }

    public CustomerDto getCustomer(String id) {
        Customer customer = customerService.getCustomer(id);
        return customerConverter.convert(customer);
    }

    public void createCustomer(CustomerDto dto) {
        Customer customer = customerConverter.convert(dto);
        customerService.create(customer);
    }

    public void updateCustomer(CustomerDto dto) {
        Customer customer = customerConverter.convert(dto);
        customerService.update(customer);
    }

    public void deleteCustomer(String id) {
        customerService.delete(id);
    }

}
