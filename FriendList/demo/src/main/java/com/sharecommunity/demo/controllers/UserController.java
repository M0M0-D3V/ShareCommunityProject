package com.sharecommunity.demo.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.sharecommunity.demo.models.User;
import com.sharecommunity.demo.services.UserService;
import com.sharecommunity.demo.validators.UserValidator;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {
    private final UserService userService;
    private final UserValidator userValidator;

    public UserController(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    // index at / is the registration and login page
    @RequestMapping("/")
    public String index(@ModelAttribute("registration") User user, HttpSession session, Model model) {
        if (session.getAttribute("uuid") != null) {
            return "redirect:/events";
        } else {
            return "index.jsp";
        }
    }

    @PostMapping("/registration")
    public String registerUser(@Valid @ModelAttribute("registration") User user, BindingResult result,
            HttpSession session, Model model) {
        // [x] validate
        // [x] else, save the user in the database, save the user id in session, and
        // [x] redirect them to the /home route
        System.out.println("hit the registration button and running validations now");
        userValidator.validate(user, result);
        System.out.println("ran validations and now evaluating if errors...");
        if (result.hasErrors()) {
            System.out.println("evaulated that there are errors... should render index.jsp");
            return "index.jsp";
        } else {
            System.out.println("Yup, going for it");
            this.userService.registerUser(user);
            System.out.println("User created successfully");
            session.setAttribute("uuid", user.getId());
            System.out.println("User ID saved to session as: " + user.getId());
            return "redirect:/events";
        }
    }

    @PostMapping("/login")
    public String loginUser(@Valid @ModelAttribute("registration") User user, BindingResult result,
            @RequestParam("email") String email, @RequestParam("password") String password, Model model,
            HttpSession session, RedirectAttributes redirectAttributes) {
        System.out.println("entered post request for login...");
        // [x] if the user is authenticated, save their user id in session
        if (userService.authenticateUser(email, password)) {
            User thisUser = userService.findByEmail(email);
            session.setAttribute("uuid", thisUser.getId());
            System.out.println("User ID saved to session as: " + thisUser.getId());
            return "redirect:/events";
        }
        // [x] else, add error messages and return the login page
        else {
            String error = "Email or Password incorrect";
            model.addAttribute("error", error);
            return "index.jsp";
        }
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        // [x] invalidate session
        System.out.println("Invalidating Session...");
        session.invalidate();
        // [x] redirect to login page
        return "redirect:/";
    }

}