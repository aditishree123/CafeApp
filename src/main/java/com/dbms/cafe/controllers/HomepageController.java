package com.dbms.cafe.controllers;
import com.dbms.cafe.models.User;
import com.dbms.cafe.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import com.dbms.cafe.services.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomepageController {
    SecurityService securityService;
    UserRepository userRepository;
    UserService userService;
    @Autowired
    public HomepageController(SecurityService securityService, UserService userService, UserRepository userRepository) {
        this.securityService = securityService;
        this.userService=userService;
        this.userRepository=userRepository;
    }

    @GetMapping({"/","","/homepage"})
    public String homepage(Model model){
        String loggedInUSerName = securityService.findLoggedInUsername();
        model.addAttribute("user",userService.findByUsername(loggedInUSerName));
        if(loggedInUSerName==null){
            model.addAttribute("loginStatus",false);
        }else{
            model.addAttribute("loginStatus",true);
        }
        return "homepage";
    }

    @GetMapping({"/editProfile"})
    public String EditProfile(Model model){
        String loggedInUSerName = securityService.findLoggedInUsername();
        model.addAttribute("user",userService.findByUsername(loggedInUSerName));
        if(loggedInUSerName==null){
            model.addAttribute("loginStatus",false);
        }else{
            model.addAttribute("loginStatus",true);
        }
        return "editProfile";
    }

    @PostMapping({"/editProfile"})
    public String AfterEditProfile(Model model,@RequestParam("email")String email,@RequestParam("firstname") String firstname,
                                   @RequestParam("middlename") String middlename,@RequestParam("lastname") String lastname,
                                   @RequestParam("flat") String flat,@RequestParam("landmark") String landmark,
                                   @RequestParam("locality") String locality,@RequestParam("contact") String contact){
        String loggedInUSerName = securityService.findLoggedInUsername();
        model.addAttribute("user",userService.findByUsername(loggedInUSerName));
        userRepository.updateUser(email,firstname,middlename,lastname,flat,landmark,locality,contact);
        if(loggedInUSerName==null){
            model.addAttribute("loginStatus",false);
        }else{
            model.addAttribute("loginStatus",true);
        }
        return "editProfile";
    }

}