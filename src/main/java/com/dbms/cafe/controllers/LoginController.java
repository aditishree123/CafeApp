package com.dbms.cafe.controllers;

import com.dbms.cafe.events.OnRegistrationSuccessEvent;
import com.dbms.cafe.models.User;
import com.dbms.cafe.services.SecurityService;
import com.dbms.cafe.services.SecurityServiceImpl;
import com.dbms.cafe.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class LoginController {
    UserService userService;
    SecurityService securityService;

    @Autowired
    public LoginController(UserService userService, SecurityService securityService) {
        this.userService = userService;
        this.securityService = securityService;

    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("user",new User());
        return "login";
    }
    @RequestMapping("/welcome")
    public String welcome(Principal principal, Model model){
        User user = userService.findByUsername(principal.getName());
        if(user.getStatus().equals("not-verified")) {
            model.addAttribute("verification",false);
            securityService.notVerified();
            return "redirect:/AccessDenied";
        }
        model.addAttribute("username",securityService.findLoggedInUsername());
        return "redirect:/homepage";
    }


}