package com.dbms.cafe.controllers;

import com.dbms.cafe.models.*;
import com.dbms.cafe.repositories.*;
import com.dbms.cafe.services.SecurityService;
import com.dbms.cafe.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class FaqController {

    FoodItemRepository foodItemRepository;
    OfferRepository offerRepository;
    CartRepository cartRepository;
    SecurityService securityService;
    UserService userService;
    FaqRepository faqRepository;

    @Autowired
    public FaqController(FoodItemRepository foodItemRepository, OfferRepository offerRepository,FaqRepository faqRepository,
                              SecurityService securityService, UserService userService,   CartRepository cartRepository) {
        this.foodItemRepository=foodItemRepository;
        this.offerRepository=offerRepository;
        this.securityService= securityService;
        this.userService= userService;
        this.cartRepository=cartRepository;
        this.faqRepository=faqRepository;
    }

    @GetMapping("/addQuestion")
    public String Faq(Model model){
        String loggedInUSerName = securityService.findLoggedInUsername();
        User user=userService.findByUsername(loggedInUSerName);
        model.addAttribute("user",user);
        if(loggedInUSerName==null){
            model.addAttribute("loginStatus",false);
        }else{
            model.addAttribute("loginStatus",true);
        }
        return "addQuestion";
    }

    @PostMapping("/addQuestion")
    public String addQuery(@RequestParam("question") String question,Model model){
        String loggedInUSerName = securityService.findLoggedInUsername();
        User user=userService.findByUsername(loggedInUSerName);
        model.addAttribute("user",user);
        if(loggedInUSerName==null){
            model.addAttribute("loginStatus",false);
        }else{
            model.addAttribute("loginStatus",true);
        }
        FAQ faq=new FAQ();
        faq.setQuestion(question);
        faq.setAnswer("---");
        faq.setStatus("Deactivated");
        faqRepository.saveFAQ(faq);
        return "redirect:/homepage";
    }

}
