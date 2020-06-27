package uk.co.mruoc.repository;

import uk.co.mruoc.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {

    List<Customer> getCustomers(int limit, int offset);

    Optional<Customer> getCustomer(String id);

    int getNumberOfCustomers();

    void create(Customer customer);

    void update(Customer customer);

    void delete(String id);

}
