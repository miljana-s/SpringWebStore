package com.example.springproject.controller;

import com.example.springproject.model.UserModel;
import com.example.springproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model){
        model.addAttribute("registerRequest", new UserModel());
        return "registration";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model){
        model.addAttribute("loginRequest", new UserModel());
        return "login";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute UserModel userModel, Model model){
        UserModel registeredUser =
                userService.registerUser(
                        userModel.getFirstName(),
                        userModel.getLastName(),
                        userModel.getUsername(),
                        userModel.getPassword(),
                        userModel.getEmail(),
                        userModel.getPhone(),
                        userModel.getAddress(),
                        userModel.getCity()
                );

        if (registeredUser != null) {
            return "login";
        }
        else {
            model.addAttribute("displayRegisterError", true);
            return "registration";
        }
    }

    @PostMapping("/login")
    public String login(@ModelAttribute UserModel userModel, Model model){
        UserModel authenticated = userService.authenticate(userModel.getUsername(), userModel.getPassword());
        if (authenticated != null ){
            // model.addAttribute("userLogin", authenticated.getUsername()); --> <span th:text="${userLogin}"></span>
            // Determine the user's role and redirect accordingly
            if (authenticated.getRoles().stream().anyMatch(role -> role.getName().equals("SELLER"))) {
                return "products";
            } else if (authenticated.getRoles().stream().anyMatch(role -> role.getName().equals("CUSTOMER"))) {
                return "products";
            }
//            model.addAttribute("user", authenticated);
//            return "redirect:/products";
        }
        else {
            model.addAttribute("displayLoginError", true);
        }
        return "login";
    }
}
