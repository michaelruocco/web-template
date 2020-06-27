package uk.co.mruoc.service;

import uk.co.mruoc.exception.CustomerIdAlreadyUsedException;
import uk.co.mruoc.exception.CustomerNotFoundException;
import uk.co.mruoc.model.Customer;
import uk.co.mruoc.repository.CustomerRepository;

import java.util.List;
import java.util.Optional;

public class CustomerService {

    private final CustomerRepository repository;
    private final CustomerValidator validator;

    public CustomerService(CustomerRepository repository) {
        this(repository, new CustomerValidator());
    }

    public CustomerService(CustomerRepository repository, CustomerValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    public List<Customer> getCustomers() {
        return getCustomers(Integer.MAX_VALUE, 0);
    }

    public List<Customer> getCustomers(int limit, int offset) {
        return repository.getCustomers(limit, offset);
    }

    public Customer getCustomer(String id) {
        Optional<Customer> customer = repository.getCustomer(id);
        return customer.orElseThrow(() -> new CustomerNotFoundException(id));
    }

    public int getNumberOfCustomers() {
        return repository.getNumberOfCustomers();
    }

    public void create(Customer customer) {
        if (exists(customer.getId()))
            throw new CustomerIdAlreadyUsedException(customer.getId());

        validator.validate(customer);
        repository.create(customer);
    }

    public void update(Customer customer) {
        if (!exists(customer.getId()))
            throw new CustomerNotFoundException(customer.getId());

        validator.validate(customer);
        repository.update(customer);
    }

    public void delete(String id) {
        repository.delete(id);
    }

    private boolean exists(String id) {
        return repository.getCustomer(id).isPresent();
    }

}