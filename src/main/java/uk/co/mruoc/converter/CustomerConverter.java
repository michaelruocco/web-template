package uk.co.mruoc.converter;

import uk.co.mruoc.dto.CustomerDto;
import uk.co.mruoc.exception.InvalidCustomerBalanceException;
import uk.co.mruoc.model.Customer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static uk.co.mruoc.model.Customer.CustomerBuilder;

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
        dto.setBalance(balanceAsString(customer));
        return dto;
    }

    public Customer convert(CustomerDto dto) {
        return new CustomerBuilder()
                .setId(dto.getId())
                .setFirstName(dto.getFirstName())
                .setSurname(dto.getSurname())
                .setBalance(balanceAsBigDecimal(dto))
                .build();
    }

    private BigDecimal balanceAsBigDecimal(CustomerDto dto) {
        String balance = dto.getBalance();
        try {
            return new BigDecimal(balance);
        } catch (NumberFormatException e) {
            throw new InvalidCustomerBalanceException(balance, e);
        }
    }

    private String balanceAsString(Customer customer) {
        BigDecimal balance = customer.getBalance();
        return balance.toString();
    }

}
