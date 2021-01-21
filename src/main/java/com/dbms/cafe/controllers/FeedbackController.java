package com.dbms.cafe.controllers;

import com.dbms.cafe.models.*;
import com.dbms.cafe.repositories.*;
import com.dbms.cafe.services.SecurityService;
import com.dbms.cafe.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class FeedbackController {

    FoodItemRepository foodItemRepository;
    OfferRepository offerRepository;
    CartRepository cartRepository;
    SecurityService securityService;
    UserService userService;
    FeedbackRepository feedbackRepository;

    @Autowired
    public FeedbackController(FoodItemRepository foodItemRepository, OfferRepository offerRepository,FeedbackRepository feedbackRepository,
                           SecurityService securityService, UserService userService,   CartRepository cartRepository) {
        this.foodItemRepository=foodItemRepository;
        this.offerRepository=offerRepository;
        this.securityService= securityService;
        this.userService= userService;
        this.cartRepository=cartRepository;
        this.feedbackRepository=feedbackRepository;
    }

    @GetMapping("/addFeedback")
    public String Feedback(Model model){
        String loggedInUSerName = securityService.findLoggedInUsername();
        User user=userService.findByUsername(loggedInUSerName);
        model.addAttribute("user",user);
        if(loggedInUSerName==null){
            model.addAttribute("loginStatus",false);
        }else{
            model.addAttribute("loginStatus",true);
        }
        return "addFeedback";
    }

    @GetMapping("/feedback")
    public String feedback(Model model){
        String loggedInUSerName = securityService.findLoggedInUsername();
        User user=userService.findByUsername(loggedInUSerName);
        model.addAttribute("user",user);
        if(loggedInUSerName==null){
            model.addAttribute("loginStatus",false);
        }else{
            model.addAttribute("loginStatus",true);
        }
        model.addAttribute("feedbacks",feedbackRepository.findAll());
        return "feedback";
    }

    @PostMapping("/addFeedback")
    public String addFeedback(@RequestParam("description") String description,Model model){
        String loggedInUSerName = securityService.findLoggedInUsername();
        User user=userService.findByUsername(loggedInUSerName);
        model.addAttribute("user",user);
        if(loggedInUSerName==null){
            model.addAttribute("loginStatus",false);
        }else{
            model.addAttribute("loginStatus",true);
        }
        Feedback feedback=new Feedback();
        feedback.setUser(user);
        feedback.setDescription(description);
        feedback.setStatus("Deactivated");
        feedbackRepository.saveFeedback(feedback);
        return "homepage";
    }

    @PostMapping("/feedback/{id}")
    public String addFeedback(@PathVariable("id")int Id,@RequestParam("status") String status){

        return "homepage";
    }

}
