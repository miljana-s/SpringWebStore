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
        return "register_page";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model){
        model.addAttribute("loginRequest", new UserModel());
        return "login_page";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute UserModel userModel){
        System.out.println("register request: " + userModel);
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

        return registeredUser == null ? "error_page" : "redirect:/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute UserModel userModel, Model model ){
        System.out.println("login request: " + userModel);
        UserModel authenticated = userService.authenticate(userModel.getUsername(), userModel.getPassword());
        if (authenticated != null ){
            model.addAttribute("userLogin", authenticated.getUsername());

            // Determine the user's role and redirect accordingly
            if (authenticated.getRoles().stream().anyMatch(role -> role.getName().equals("SELLER"))) {
                return "seller";
            } else if (authenticated.getRoles().stream().anyMatch(role -> role.getName().equals("CUSTOMER"))) {
                return "customer";
            } else {
                // Handle other roles or scenarios here
                return "error_page";
            }
            // TODO: check role & decide where to redirect!

        } else {
            return "error_page";
        }
    }

    @GetMapping("/seller")
    public String sellerDashboard(Model model) {
        // Add seller-specific logic here
        return "seller_page"; // Return the seller dashboard view
    }

    @GetMapping("/customer")
    public String buyerDashboard(Model model) {
        // Add buyer-specific logic here
        return "customer"; // Return the buyer dashboard view
    }
}
