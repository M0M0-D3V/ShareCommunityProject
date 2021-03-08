package com.sharecommunity.demo.controllers;

import java.util.Set;

import javax.servlet.http.HttpSession;

import com.sharecommunity.demo.models.User;
import com.sharecommunity.demo.services.UserService;
import com.sharecommunity.demo.validators.UserValidator;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FriendlistController {
    private final UserService userService;
    private final UserValidator userValidator;

    public FriendlistController(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    private Long isLoggedIn(HttpSession session) {
        if (session.getAttribute("uuid") == null) {
            return null;
        }
        return (Long) session.getAttribute("uuid");
    }

    @RequestMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        if (isLoggedIn(session) == null) {
            return "redirect:/";
        }
        User me = userService.findUserById(isLoggedIn(session));
        Set<User> myRequestsPending = me.getRequestsSentTo();
        model.addAttribute("me", me);
        model.addAttribute("myRequestsPending", myRequestsPending);
        model.addAttribute("allOtherUsers", userService.findAllOtherUsers(isLoggedIn(session)));

        return "friendlist/dashboard.jsp";
    }

    @PostMapping("/friendlist/{userId}/request")
    public String requestFriend(HttpSession session, Model model, @PathVariable(value = "userId") Long userId) {
        if (isLoggedIn(session) == null) {
            return "redirect:/";
        }
        // logic for 'me' to send 'user' request
        User me = userService.findUserById(isLoggedIn(session));
        User user = userService.findUserById(userId);
        System.out.println("made to friend request between " + me.getFirstName() + " and " + user.getFirstName());
        userService.friendRequest(me, user);
        return "redirect:/dashboard";
    }

}
