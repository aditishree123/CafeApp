package com.dbms.cafe.controllers;

import com.dbms.cafe.models.*;
import com.dbms.cafe.repositories.*;
import com.dbms.cafe.repositories.FoodItemRepository;
import com.dbms.cafe.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MenuController {
    MenuService menuService;
    AdminRepository adminRepository;
    SecurityService securityService;
    UserService userService;
    OrderRepository orderRepository;
    OfferRepository offerRepository;
    CartRepository cartRepository;
    FoodItemRepository foodItemRepository;
    @Autowired
    public MenuController(OfferRepository offerRepository,MenuService menuService,OrderRepository orderRepository,AdminRepository adminRepository,SecurityService securityService,UserService userService,
                          CartRepository cartRepository,FoodItemRepository foodItemRepository) {
        this.menuService = menuService;
        this.adminRepository = adminRepository;
        this.securityService =securityService;
        this.userService=userService;
        this.foodItemRepository=foodItemRepository;
        this.orderRepository=orderRepository;
        this.offerRepository=offerRepository;
        this.cartRepository=cartRepository;
    }

    @GetMapping({"/menu"})
    public String menu(Model model){
        String loggedInUSerName = securityService.findLoggedInUsername();
        User user=userService.findByUsername(loggedInUSerName);
        model.addAttribute("user",user);
        if(loggedInUSerName==null){
            model.addAttribute("loginStatus",false);
        }else{
            model.addAttribute("loginStatus",true);
        }
        model.addAttribute("category",menuService.findAll());
        return "menu";
    }



    @GetMapping("/addCategory")
    public String addCategory(Model model){
        String loggedInUSerName = securityService.findLoggedInUsername();
        User user=userService.findByUsername(loggedInUSerName);
        model.addAttribute("user",user);
        if(loggedInUSerName==null){
            model.addAttribute("loginStatus",false);
        }else{
            model.addAttribute("loginStatus",true);
        }
        model.addAttribute("category",new Category());
        return "addCategory";
    }

    @PostMapping({"/addCategory"})
    public String addCategory(@ModelAttribute("category") Category category){
        menuService.saveCategory(category);
        return "redirect:/menu";
    }


    @GetMapping({"/addItem"})
    public String add(Model model){
        String loggedInUSerName = securityService.findLoggedInUsername();
        User user=userService.findByUsername(loggedInUSerName);
        model.addAttribute("user",user);
        if(loggedInUSerName==null){
            model.addAttribute("loginStatus",false);
        }else{
            model.addAttribute("loginStatus",true);
        }
        model.addAttribute("categories", menuService.findAll());
        return "addItem";
    }

    @PostMapping({"/addItem"})
    public String add(@RequestParam("foodItem") String item, @RequestParam("categories") String category,
                      @RequestParam("price") int price, @RequestParam("quantity") int quantity, @RequestParam("description") String des, @RequestParam("discount") int discount){
        System.out.println(category);
        FoodItem foodItem=new FoodItem();
        foodItem.setItem(item);
        Category category1=menuService.findByCategoryName(category);
        foodItem.setCategory(category1);
        foodItem.setPrice(price);
        foodItem.setQuantity(quantity);
        foodItem.setDescription(des);
        foodItem.setDiscount(discount);
        foodItem.setAvg("---");
        menuService.saveItem(foodItem);
        return "redirect:/addItem";
    }

    @GetMapping({"/category_item/{id}"})
    public String CategoryItems(@PathVariable("id") int id, Model model){
        List<FoodItem> items=menuService.findAllItems(id);
        model.addAttribute("items",items);
        model.addAttribute("category",menuService.findByCategoryId(id));
        String loggedInUSerName = securityService.findLoggedInUsername();
        User user=userService.findByUsername(loggedInUSerName);
        if(loggedInUSerName==null){
            model.addAttribute("loginStatus",false);
        }else{
            model.addAttribute("loginStatus",true);
            model.addAttribute("user",user);
            model.addAttribute("role",user.getRole());
            model.addAttribute("userid",user.getId());
            List<Integer> list=new ArrayList<Integer>();
            for(FoodItem c:items){
                Cart cart=cartRepository.findCartById(user.getId(), c.getId());
                if(cart==null)
                    list.add(0);
                else list.add(cart.getQuantity());
            }
            List<Integer> list1=new ArrayList<Integer>();
            for(FoodItem c:items){
               int d=((c.getPrice())-((c.getPrice()*c.getDiscount())/100));
               list1.add(d);
            }
            model.addAttribute("list1",list1);
            model.addAttribute("quant",list);
        }
        return "category_item";
    }

    @RequestMapping("/editCategory")
    public String EditCategory(Model model){
        String loggedInUSerName = securityService.findLoggedInUsername();
        User user=userService.findByUsername(loggedInUSerName);
        model.addAttribute("user",user);
        if(loggedInUSerName==null){
            model.addAttribute("loginStatus",false);
        }else{
            model.addAttribute("loginStatus",true);
        }
        model.addAttribute("categories", menuService.findAll());
        return "editCategory";
    }


    @RequestMapping("/updateCategory/{id}")
    public String UpdateCategory(@PathVariable("id") int id, Model model){
        String loggedInUSerName = securityService.findLoggedInUsername();
        User user=userService.findByUsername(loggedInUSerName);
        model.addAttribute("user",user);
        if(loggedInUSerName==null){
            model.addAttribute("loginStatus",false);
        }else{
            model.addAttribute("loginStatus",true);
        }
        model.addAttribute("category",menuService.findByCategoryId(id));
        return "updateCategory";
    }

    @PostMapping("/updateCategory/{id}")
    public String updateCategory(@PathVariable("id") int id, @RequestParam("name") String name, @RequestParam("description") String description,@RequestParam("image") String image){
        adminRepository.updateCategory(name,description,image,id);
        return "redirect:/menu";
    }

    @GetMapping("/editItems")
    public String EditItems(Model model){
        String loggedInUSerName = securityService.findLoggedInUsername();
        User user=userService.findByUsername(loggedInUSerName);
        model.addAttribute("user",user);
        if(loggedInUSerName==null){
            model.addAttribute("loginStatus",false);
        }else{
            model.addAttribute("loginStatus",true);
        }
        model.addAttribute("items", menuService.findItems());
        return "editItems";
    }


    @RequestMapping("/updateItem/{id}")
    public String UpdateItem(@PathVariable("id") int id, Model model){
        String loggedInUSerName = securityService.findLoggedInUsername();
        User user=userService.findByUsername(loggedInUSerName);
        model.addAttribute("user",user);
        if(loggedInUSerName==null){
            model.addAttribute("loginStatus",false);
        }else{
            model.addAttribute("loginStatus",true);
        }
        model.addAttribute("item",foodItemRepository.findById(id));
        return "updateItem";
    }
    @PostMapping("/updateItem/{id}")
    public String updateItem(@PathVariable("id") int id, @RequestParam("name") String item, @RequestParam("price") int price,@RequestParam("quantity") int quantity,
                             @RequestParam("description") String description, @RequestParam("discount")int discount) {
        adminRepository.updateItem(item, price, quantity, description, discount, id);
        return "redirect:/editItems";
    }

}
