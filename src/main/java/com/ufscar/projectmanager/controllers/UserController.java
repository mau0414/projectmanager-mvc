package com.ufscar.projectmanager.controllers;

import com.fasterxml.jackson.core.SerializableString;
import com.ufscar.projectmanager.dto.UserLoginRequest;
import com.ufscar.projectmanager.models.User;
import com.ufscar.projectmanager.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String hello(Model model) {
        return "users/hello";
    }

    @PostMapping("/login")
    public ModelAndView login(HttpSession session, @Valid UserLoginRequest userLoginRequest, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("users/hello");
        } else {
            String username;
            Long userId;
            User userFromRequest = userLoginRequest.toModel();
            User user = this.userRepository.findByName(userFromRequest.getName());
            if (user == null) {
                this.userRepository.save(userFromRequest);
                userId = userFromRequest.getId();
                System.out.println("no if" + userId);
            } else {
                userId = user.getId();
                System.out.println("no else" + userId);
            }

            ModelAndView mv = new ModelAndView("redirect:/projects");
//            mv.addObject("name", username);
            session.setAttribute("userId", userId);

            return mv;
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("userId");

        return "redirect:/";
    }
}
