package uk.co.mruoc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class IndexController {

    @RequestMapping("/")
    public String handleIndex(Model m) {
        m.addAttribute("name", "web-template-index");
        return "index";
    }

}