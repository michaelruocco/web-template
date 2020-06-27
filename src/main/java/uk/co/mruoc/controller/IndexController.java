package uk.co.mruoc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class IndexController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("name", "web-template-index");
        return "index";
    }

}