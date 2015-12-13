package uk.co.tpplc.mruoc.converter;

import uk.co.tpplc.mruoc.dto.CustomerDto;
import uk.co.tpplc.mruoc.model.Customer;

import java.util.ArrayList;
import java.util.List;

import static uk.co.tpplc.mruoc.model.Customer.CustomerBuilder;

public class CustomerConverter {

    public List<CustomerDto> convert(List<Customer> customers) {
        List<CustomerDto> dtos = new ArrayList<>();
        for(Customer customer : customers)
            dtos.add(convert(customer));
        return dtos;
    }

    public CustomerDto convert(Customer customer) {
        CustomerDto dto = new CustomerDto();
        dto.setId(customer.getId());
        dto.setFirstName(customer.getFirstName());
        dto.setSurname(customer.getSurname());
        dto.setBalance(customer.getBalance());
        return dto;
    }

    public Customer convert(CustomerDto dto) {
        return new CustomerBuilder()
                .setId(dto.getId())
                .setFirstName(dto.getFirstName())
                .setSurname(dto.getSurname())
                .setBalance(dto.getBalance())
                .build();
    }

}
