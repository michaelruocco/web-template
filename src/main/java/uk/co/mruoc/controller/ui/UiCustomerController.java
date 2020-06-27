package uk.co.mruoc.controller.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.co.mruoc.exception.CustomerException;
import uk.co.mruoc.controller.dto.CustomerDto;
import uk.co.mruoc.controller.dto.RequestConverter;
import uk.co.mruoc.facade.CustomerFacade;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UiCustomerController {

    private static final Logger LOG = LoggerFactory.getLogger(UiCustomerController.class);

    private final CustomerFacade facade;

    public UiCustomerController(CustomerFacade facade) {
        this.facade = facade;
    }

    private final RequestConverter converter = new RequestConverter();

    @GetMapping(value = "/customer-list")
    public String listCustomers(Model m) {
        m.addAttribute("name", "customers");
        m.addAttribute("customers", facade.getCustomers());
        return "customer-list";
    }

    @GetMapping(value = "/delete-customer")
    public String deleteCustomer(@RequestParam(value="id") String id) {
        facade.deleteCustomer(id);
        return "redirect:/customer-list";
    }

    @GetMapping(value = "/update-customer")
    public String showUpdateCustomer(@RequestParam(value="id") String id, Model model) {
        model.addAttribute("name", "customer");
        model.addAttribute("customer", facade.getCustomer(id));
        return "update-customer";
    }

    @PostMapping(value = "/update-customer")
    public String updateCustomer(HttpServletRequest request, Model model) {
        CustomerDto customer = converter.toCustomer(request);
        try {
            facade.updateCustomer(customer);
            return "redirect:/customer-list";
        } catch (CustomerException e) {
            handleError(model, customer, e);
            return "update-customer";
        }
    }

    @GetMapping(value = "/create-customer")
    public String showCreateCustomer(Model model) {
        model.addAttribute("customer", new CustomerDto());
        return "create-customer";
    }

    @PostMapping(value = "/create-customer")
    public String createCustomer(HttpServletRequest request, Model model) {
        CustomerDto customer = converter.toCustomer(request);
        try {
            facade.createCustomer(customer);
            return "redirect:/customer-list";
        } catch (CustomerException e) {
            handleError(model, customer, e);
            return "create-customer";
        }
    }

    private void handleError(Model model, CustomerDto customer, Exception e) {
        logInfo(e);
        model.addAttribute("error", e.getMessage());
        model.addAttribute("customer", customer);
    }

    private void logInfo(Throwable t) {
        LOG.info(t.getMessage(), t);
    }

}