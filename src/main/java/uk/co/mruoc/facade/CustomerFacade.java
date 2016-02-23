package uk.co.mruoc.facade;

import uk.co.mruoc.controller.CustomerDto;

import java.util.List;

public interface CustomerFacade {

    List<CustomerDto> getCustomers();

    List<CustomerDto> getCustomers(int limit, int offset);

    int getNumberOfCustomers();

    CustomerDto getCustomer(String id);

    void createCustomer(CustomerDto dto);

    void updateCustomer(CustomerDto dto);

    void deleteCustomer(String id);

}
