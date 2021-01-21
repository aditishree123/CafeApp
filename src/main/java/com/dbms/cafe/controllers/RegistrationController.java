package com.dbms.cafe.controllers;

import com.dbms.cafe.events.OnRegistrationSuccessEvent;
import com.dbms.cafe.models.User;
import com.dbms.cafe.models.VerificationToken;
import com.dbms.cafe.services.SecurityService;
import com.dbms.cafe.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.security.Principal;
import java.util.Calendar;

@Controller
public class RegistrationController {

    private UserService userService;
    private SecurityService securityService;
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    public RegistrationController(UserService userService, SecurityService securityService, ApplicationEventPublisher eventPublisher) {
        this.userService = userService;
        this.securityService = securityService;
        this.eventPublisher = eventPublisher;
    }

    @GetMapping("/register")
    public String register(Model model){
        String loggedInUSerName = securityService.findLoggedInUsername();
        User user=userService.findByUsername(loggedInUSerName);
        model.addAttribute("user",user);
        if(loggedInUSerName==null){
            model.addAttribute("loginStatus",false);
        }else{
            model.addAttribute("loginStatus",true);
        }
        model.addAttribute("user",new User());
        return "register";
    }
    @GetMapping("/registerError")
    public String registerError(Model model){
        model.addAttribute("user",new User());
        model.addAttribute("emailError",true);
        return "register";
    }
    @GetMapping("/AccessDenied")
    public String AccessDenied(Model model){
        return "AccessDenied";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") User user, BindingResult result, WebRequest request,Model model){
        if(userService.userExists(user.getEmail())){
            return "redirect:/registerError";
        }
        userService.save(user);
        User user1 = userService.findByUsername(user.getEmail());
        try {
            String appUrl = request.getContextPath();
            eventPublisher.publishEvent(new OnRegistrationSuccessEvent(appUrl,user1));
        }catch (Exception re) {
            re.printStackTrace();
        }
        return "verify";
    }

    @GetMapping("/confirmRegistration")
    public String confirmRegistration(WebRequest request, Model model, @RequestParam("token") String token){
        VerificationToken verificationToken = userService.getVerificationToken(token);
        if(verificationToken == null) {
            return "redirect:/AccessDenied";
        }
        User user = verificationToken.getUser();
        Calendar calendar = Calendar.getInstance();
        if(verificationToken.getExpiryDate().getTime()-calendar.getTime().getTime()<=0){
            return "redirect:/AccessDenied";
        }
        userService.enableRegisteredUser(user);
        return "redirect:/login";
    }

}