package uk.co.mruoc.controller.web;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.co.mruoc.dto.CustomerDto;
import uk.co.mruoc.exception.CustomerException;
import uk.co.mruoc.exception.CustomerIdAlreadyUsedException;
import uk.co.mruoc.facade.CustomerFacade;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class CustomerController {

    private static final Logger LOG = Logger.getLogger(CustomerController.class);

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
    public String showUpdateCustomer(@RequestParam(value="id") String id, Model model) {
        model.addAttribute("name", "customer");
        model.addAttribute("customer", customerFacade.getCustomer(id));
        return "updateCustomer";
    }

    @RequestMapping(value = "/updateCustomer", method = POST)
    public String updateCustomer(HttpServletRequest request, Model model) {
        CustomerDto customer = converter.toCustomer(request);
        try {
            customerFacade.updateCustomer(customer);
            return "redirect:/listCustomers";
        } catch (CustomerException e) {
            handleError(model, customer, e);
            return "updateCustomer";
        }
    }

    @RequestMapping(value = "/createCustomer", method = GET)
    public String showCreateCustomer() {
        return "createCustomer";
    }

    @RequestMapping(value = "/createCustomer", method = POST)
    public String createCustomer(HttpServletRequest request, Model model) {
        CustomerDto customer = converter.toCustomer(request);
        try {
            customerFacade.createCustomer(customer);
            return "redirect:/listCustomers";
        } catch (CustomerException e) {
            handleError(model, customer, e);
            return "createCustomer";
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