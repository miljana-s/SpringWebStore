package com.example.springproject.controller;

import com.example.springproject.model.CartModel;
import com.example.springproject.model.UserModel;
import com.example.springproject.model.UserModelDTO;
import com.example.springproject.model.UserModelMapper;
import com.example.springproject.service.StoreService;
import com.example.springproject.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

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

    @PostMapping("/login-process")
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
            if (authenticated.getRoles().stream().anyMatch(role -> role.getName().equals("SELLER"))) {
                return "products";
            } else if (authenticated.getRoles().stream().anyMatch(role -> role.getName().equals("CUSTOMER"))) {
                return "products";
            }
        }
        else {
            model.addAttribute("displayLoginError", true);
        }
        return "login";
    }

    @GetMapping("/profile")
    public String getProfilePage(Model model, HttpSession session){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserModel existingUser = (UserModel)authentication.getPrincipal();
        UserModelDTO userDTO = UserModelMapper.toDTO(userService.findById(existingUser.getId()));
        model.addAttribute("userData", userDTO);
        StoreService.addSessionCartToModel(model, session);
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@ModelAttribute UserModelDTO updatedUserModel, Model model, HttpSession session){
        // 1. Get existing user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserModel existingUser = (UserModel) authentication.getPrincipal();

        // 2. Try ti update user
        boolean is_user_updated = userService.updateUser(updatedUserModel);

        // 3. Display related message
        if (is_user_updated) {
            UserModel user = userService.findById(existingUser.getId());
            model.addAttribute("showSuccMsg", true);
            model.addAttribute("userData", user);
        }
        else {
            model.addAttribute("showWarnMsg", true);
            model.addAttribute("userData", existingUser);
        }

        StoreService.addSessionCartToModel(model, session);
        return "profile";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/";
    }
}
