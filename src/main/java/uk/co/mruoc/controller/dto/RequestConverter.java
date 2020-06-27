package uk.co.mruoc.controller.dto;

import uk.co.mruoc.controller.dto.CustomerDto;

import javax.servlet.http.HttpServletRequest;

public class RequestConverter {

    public CustomerDto toCustomer(HttpServletRequest request) {
        CustomerDto customer = new CustomerDto();
        customer.setId(request.getParameter("id"));
        customer.setFirstName(request.getParameter("firstName"));
        customer.setSurname(request.getParameter("surname"));
        customer.setBalance(request.getParameter("balance"));
        return customer;
    }

}
