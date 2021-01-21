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
public class OfferController {

    FoodItemRepository foodItemRepository;
    OfferRepository offerRepository;
    CartRepository cartRepository;
    SecurityService securityService;
    UserService userService;

    @Autowired
    public OfferController(FoodItemRepository foodItemRepository, OfferRepository offerRepository,
                           SecurityService securityService, UserService userService,   CartRepository cartRepository) {
        this.foodItemRepository=foodItemRepository;
        this.offerRepository=offerRepository;
        this.securityService= securityService;
        this.userService= userService;
        this.cartRepository=cartRepository;
    }

    @GetMapping("/setOffers")
    public String setOffers(Model model){
        String loggedInUSerName = securityService.findLoggedInUsername();
        User user=userService.findByUsername(loggedInUSerName);
        model.addAttribute("user",user);
        if(loggedInUSerName==null){
            model.addAttribute("loginStatus",false);
        }else{
            model.addAttribute("loginStatus",true);
        }
        model.addAttribute("items",foodItemRepository.findItems());
        return "setOffers";
    }

    @GetMapping("/Offers")
    public String Offers(Model model){
        List<Offer> offers=offerRepository.allOffers();
        if(offers.size()>0){
            model.addAttribute("empty",false);
        }else{
            model.addAttribute("empty",true);
        }
        model.addAttribute("offers",offers);
        String loggedInUSerName = securityService.findLoggedInUsername();
        User user=userService.findByUsername(loggedInUSerName);
        model.addAttribute("user",user);
        if(loggedInUSerName==null){
            model.addAttribute("loginStatus",false);
        }else{
            model.addAttribute("loginStatus",true);
        }

        return "Offers";
    }

    @PostMapping("/setOffers")
    public String saveOffers(@RequestParam("items") List<String> items, @RequestParam("price") int price,
                             @RequestParam("description") String description,Model model){
        String loggedInUSerName = securityService.findLoggedInUsername();
        User user=userService.findByUsername(loggedInUSerName);
        model.addAttribute("user",user);
        if(loggedInUSerName==null){
            model.addAttribute("loginStatus",false);
        }else{
            model.addAttribute("loginStatus",true);
        }
        model.addAttribute("items",foodItemRepository.findItems());
        Offer offer=new Offer();
        offer.setPrice(price);
        offer.setStatus("Deactivated");
        offer.setDescription(description);
        offer.setRating("---");
        offerRepository.saveOffer(offer);
        Offer offer1=offerRepository.findLast();
        for (String c : items) {
           Offer_product offerProduct =new Offer_product();
           offerProduct.setOfferid(offer1);
           FoodItem foodItem = foodItemRepository.findByName(c);
           offerProduct.setItemid(foodItem);
           offerRepository.saveCombo(offerProduct);
        }
        return "setOffers";
    }

    @GetMapping("/offer_items/{id}")
    public String OfferItems(@PathVariable("id") int Id, Model model){
        model.addAttribute("items",offerRepository.findComboProducts(Id));
        model.addAttribute("offer",offerRepository.findOfferById(Id));
        String loggedInUSerName = securityService.findLoggedInUsername();
        User user=userService.findByUsername(loggedInUSerName);
        model.addAttribute("user",user);
        if(loggedInUSerName==null){
            model.addAttribute("loginStatus",false);
        }else{
            model.addAttribute("loginStatus",true);
            Cart cart=cartRepository.findCartByIdOffer(user.getId(),Id);
            if(cart!=null)
            model.addAttribute("num",cartRepository.findCartByIdOffer(user.getId(),Id));
            else
                model.addAttribute("num",0);
            model.addAttribute("role",user.getRole());
            model.addAttribute("userid",user.getId());
        }
        return "offer_items";
    }

    @PostMapping("/offer_items/{id}")
    public String setStatus(@PathVariable("id") int Id,@RequestParam("status") String status){
       offerRepository.setStatus(status,Id);
        return "redirect:/homepage";
    }

    @PostMapping({"/carto/{id}"})
    public String CartListO(@PathVariable("id") int Id,Model model,@RequestParam("quantity") int quantity
            ,@RequestParam("offer") int offer){
        Cart cart=new Cart();
        cart.setUser(userService.findByUserId(Id));
        cart.setOffer(offerRepository.findOfferById(offer));
        cart.setQuantity(quantity);
        if(!cartRepository.cartExistsOffer(Id,offer)) {
            cartRepository.saveCart(cart);
        }
        else{
            cartRepository.updateCartOffer(Id,offer,quantity,cartRepository.findCartByIdOffer(Id,offer).getId());
        }
        model.addAttribute("user",Id);
        model.addAttribute("cartItems",cartRepository.findCartItems(Id));
        model.addAttribute("offers",cartRepository.findCartOffers(Id));
        return "redirect:/cart";
    }

    @GetMapping({"/deleteOffer/{id}"})
    public String deleteCartFromOffer(@PathVariable("id") int Id){
        cartRepository.deleteCart(Id);
        return "redirect:/cart";
    }
}
