package uk.co.mruoc.controller.web;

import uk.co.mruoc.dto.CustomerDto;

import javax.servlet.http.HttpServletRequest;

public class RequestToCustomerDtoConverter {

    public CustomerDto toCustomer(HttpServletRequest request) {
        CustomerDto customer = new CustomerDto();
        customer.setId(request.getParameter("id"));
        customer.setFirstName(request.getParameter("firstName"));
        customer.setSurname(request.getParameter("surname"));
        customer.setBalance(request.getParameter("balance"));
        return customer;
    }

}
