package uk.co.tpplc.mruoc.service;

import uk.co.tpplc.mruoc.model.Customer;

import java.util.List;

public interface CustomerService {

    List<Customer> getCustomers();

    List<Customer> getCustomers(int limit, int offset);

    Customer getCustomer(String id);

    int getNumberOfCustomers();

    void create(Customer customer);

    void update(Customer customer);

    void delete(String id);

}
