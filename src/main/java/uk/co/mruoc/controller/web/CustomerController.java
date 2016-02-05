package uk.co.mruoc.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.co.mruoc.dto.CustomerDto;
import uk.co.mruoc.facade.CustomerFacade;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class CustomerController {

    @Autowired
    private CustomerFacade customerFacade;
    private final RequestToCustomerDtoConverter converter = new RequestToCustomerDtoConverter();

    @RequestMapping(value = "/listCustomers", method = GET)
    public String listCustomers(Model m) {
        m.addAttribute("name", "customers");
        m.addAttribute("customers", customerFacade.getCustomers());
        return "listCustomers";
    }

    @RequestMapping(value = "/deleteCustomer", method = GET)
    public String deleteCustomer(@RequestParam(value="id") String id) {
        customerFacade.deleteCustomer(id);
        return "redirect:/listCustomers";
    }

    @RequestMapping(value = "/updateCustomer", method = GET)
    public String showUpdateCustomer(@RequestParam(value="id") String id, Model m) {
        m.addAttribute("name", "customer");
        m.addAttribute("customer", customerFacade.getCustomer(id));
        return "updateCustomer";
    }

    @RequestMapping(value = "/updateCustomer", method = POST)
    public String updateCustomer(HttpServletRequest request) {
        CustomerDto customer = converter.toCustomer(request);
        customerFacade.updateCustomer(customer);
        return "redirect:/listCustomers";
    }

    @RequestMapping(value = "/createCustomer", method = GET)
    public String showCreateCustomer() {
        return "createCustomer";
    }

    @RequestMapping(value = "/createCustomer", method = POST)
    public String createCustomer(HttpServletRequest request) {
        CustomerDto customer = converter.toCustomer(request);
        customerFacade.createCustomer(customer);
        return "redirect:/listCustomers";
    }

}