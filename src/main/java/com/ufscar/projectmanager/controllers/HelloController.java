package com.ufscar.projectmanager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloController {

    @GetMapping("/")
    public String hello(Model model) {
        return "hello";
    }

    @PostMapping("/login")
    public ModelAndView login(@RequestParam("name") String name) {
        ModelAndView mv = new ModelAndView("redirect:/projects");
        mv.addObject("name", name);
        return mv;
    }

}
