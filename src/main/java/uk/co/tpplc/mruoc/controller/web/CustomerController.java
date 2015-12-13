package uk.co.tpplc.mruoc.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.co.tpplc.mruoc.facade.CustomerFacade;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class CustomerController {

    @Autowired
    private CustomerFacade customerFacade;

    @RequestMapping(value = "/customers", method = GET)
    public String getCustomers(Model m) {
        m.addAttribute("name", "customers");
        m.addAttribute("customers", customerFacade.getCustomers());
        return "customers";
    }

    @RequestMapping(value = "/customer", method = GET)
    public String getCustomer(@RequestParam(value="id") String id, Model m) {
        m.addAttribute("name", "customer");
        m.addAttribute("customer", customerFacade.getCustomer(id));
        return "customer";
    }

}