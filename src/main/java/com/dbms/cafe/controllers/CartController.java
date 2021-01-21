package com.dbms.cafe.controllers;

import com.dbms.cafe.models.*;
import com.dbms.cafe.repositories.*;
import com.dbms.cafe.repositories.FoodItemRepository;
import com.dbms.cafe.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.*;

@Controller
public class CartController {
    CartRepository cartRepository;
    SecurityService securityService;
    FoodItemRepository foodItemRepository;
    UserService userService;
    OrderRepository orderRepository;
    OfferRepository offerRepository;
    @Autowired
    public CartController(CartRepository cartRepository,SecurityService securityService,UserService userService,
                          FoodItemRepository foodItemRepository,OrderRepository orderRepository, OfferRepository offerRepository) {
        this.securityService=securityService;
        this.cartRepository = cartRepository;
        this.userService=userService;
        this.foodItemRepository=foodItemRepository;
        this.orderRepository=orderRepository;
        this.offerRepository=offerRepository;
    }

    @GetMapping({"/cart"})
    public String MyCart(Model model){
        String loggedInUSerName = securityService.findLoggedInUsername();
        User user=userService.findByUsername(loggedInUSerName);
        model.addAttribute("user",user);
        if(loggedInUSerName==null){
            model.addAttribute("loginStatus",false);
        }else{
            model.addAttribute("loginStatus",true);
        }
        List<Cart> carts=cartRepository.findCartOffers(user.getId());
        System.out.println("ok");
        for (Cart c: carts)
        {
            System.out.println(c.getOffer().getDescription());
        }
        List<Cart> cartItems= cartRepository.findCartItems(user.getId());
        if(cartItems.size()>0){
            model.addAttribute("empty",false);
        }else{
            model.addAttribute("empty",true);
        }
        List<Integer> dis=new ArrayList<Integer>();
        for(Cart c:cartItems){
            int d=((c.getProduct().getPrice())-((c.getProduct().getPrice()*c.getProduct().getDiscount())/100));
            dis.add(d);
        }
        model.addAttribute("discountList",dis);
        model.addAttribute("cartItems",cartItems);
        model.addAttribute("offers", cartRepository.findCartOffers(user.getId()));
        return "cart";
    }


    @PostMapping({"/cart/{id}"})
    public String CartList(@PathVariable("id") int Id,Model model,@RequestParam("quantity") int quantity
    ,@RequestParam("product") int product){
        Cart cart=new Cart();
        cart.setUser(userService.findByUserId(Id));
        cart.setProduct(foodItemRepository.findById(product));
        cart.setQuantity(quantity);
        if(!cartRepository.cartExists(Id,product)) {
            cartRepository.saveCart(cart);
        }
        else{
            cartRepository.updateCart(Id,product,quantity,cartRepository.findCartById(Id,product).getId());
        }
        model.addAttribute("category",foodItemRepository.findById(product).getCategory());
        model.addAttribute("user",Id);
        List<Cart> cartItems= cartRepository.findCartItems(Id);
        model.addAttribute("cartItems",cartItems);
        model.addAttribute("offers",cartRepository.findCartOffers(Id));
        return "redirect:/cart";
    }


    @GetMapping("/deleteCart/{id}")
    public String deleteCartItem(@PathVariable("id") int Id){
        cartRepository.deleteCart(Id);
        return "redirect:/cart";
    }

    @GetMapping("/order/{id}")
    public String showOrder(@PathVariable("id")int Id,Model model){
        String loggedInUSerName = securityService.findLoggedInUsername();
        model.addAttribute("user",userService.findByUsername(loggedInUSerName));
        if(loggedInUSerName==null){
            model.addAttribute("loginStatus",false);
        }else{
            model.addAttribute("loginStatus",true);
        }
        model.addAttribute("orderList",cartRepository.findCartItems(Id));
        model.addAttribute("orderlist1", cartRepository.findCartOffers(Id));
        List<Cart> list=cartRepository.findCartItems(Id);
        List<Cart> list1=cartRepository.findCartOffers(Id);
        int s=0;
        for (Cart c : list) {
            s=s+((c.getProduct().getPrice())*(c.getQuantity()));
        }
        for (Cart c : list1) {
            s=s+((c.getOffer().getPrice())*(c.getQuantity()));
        }
        model.addAttribute("ActualAmount",s);
        int x=0;
        List<Integer> dis=new ArrayList<Integer>();
        for(Cart c:list){
            int d=((c.getProduct().getPrice())-((c.getProduct().getPrice()*c.getProduct().getDiscount())/100));
            dis.add(d);
        }
        model.addAttribute("discountList",dis);
        for(Cart c:list){
            int d=(((c.getProduct().getPrice()*c.getProduct().getDiscount())/100));
            System.out.println(d);
            x+=d;
            s=s-d;
        }
        model.addAttribute("netDiscount",x);
        model.addAttribute("netAmount",s);
        return "order";
    }

    @GetMapping("/ConfirmOrder")
    public String ConfirmOrder(Model model){
        Order order=new Order();
        String loggedInUSerName = securityService.findLoggedInUsername();
        model.addAttribute("user",userService.findByUsername(loggedInUSerName));
        if(loggedInUSerName==null){
            model.addAttribute("loginStatus",false);
        }else{
            model.addAttribute("loginStatus",true);
        }
        int Id= userService.findByUsername(loggedInUSerName).getId();
        List<Cart> list=cartRepository.findCartItems(Id);
        List<Cart> list1=cartRepository.findCartOffers(Id);
        int s=0;
        for (Cart c : list) {
            s=s+((c.getProduct().getPrice())*(c.getQuantity()));
        }
        for (Cart c : list1) {
            s=s+((c.getOffer().getPrice())*(c.getQuantity()));
        }
        order.setUser(userService.findByUserId(Id));
        order.setStatus("Pending");
        order.setTime(LocalDateTime.now());
        order.setAmount(s);
        if(s>=180){
        orderRepository.saveOrder(order);
        model.addAttribute("minAmount",true);}
        else
            model.addAttribute("minAmount",false);
        Order last=orderRepository.findLast(Id);
        for (Cart c : list) {
            Product product=new Product();
            product.setOrderid(last);
            product.setItem(c.getProduct());
            product.setQuantity(c.getQuantity());
            product.setRating(0);
            orderRepository.saveProduct(product);
        }
        for (Cart c : list1) {
            Product product=new Product();
            product.setOrderid(last);
            product.setOffer(c.getOffer());
            product.setQuantity(c.getQuantity());
            product.setRating(0);
            orderRepository.saveProduct(product);
        }
        model.addAttribute("last",last);
        model.addAttribute("order",order);
        model.addAttribute("list",cartRepository.findCartItems(Id));
        model.addAttribute("list1",list1);
        model.addAttribute("total_amount",s);
        return "ConfirmOrder";
    }

    @GetMapping("/orderHistory")
    public String OrderHistory(Model model){
        String loggedInUSerName = securityService.findLoggedInUsername();
        User user=userService.findByUsername(loggedInUSerName);
        model.addAttribute("user",user);
        if(loggedInUSerName==null){
            model.addAttribute("loginStatus",false);
        }else{
            model.addAttribute("loginStatus",true);
        }

        int user_id= user.getId();
        model.addAttribute("orders",orderRepository.ByUserId(user_id));
        return "orderHistory";
    }

    @GetMapping("/orderDetails/{id}")
    public String OrderDetails(@PathVariable("id")int Id,Model model){
        String loggedInUSerName = securityService.findLoggedInUsername();
        User user=userService.findByUsername(loggedInUSerName);
        model.addAttribute("user",user);
        if(loggedInUSerName==null){
            model.addAttribute("loginStatus",false);
        }else{
            model.addAttribute("loginStatus",true);
        }
        List<Product> productItems=orderRepository.ByOrderId(Id);
        List<Product> productOffers=orderRepository.ByOrderIdOffer(Id);
        model.addAttribute("order", orderRepository.findByOrderId(Id));
        model.addAttribute("products",productItems);
        model.addAttribute("producto",productOffers);
        int num=0;
        for(Product c:productItems)
            if(c.getRating()==0)
                num++;
        for(Product c:productOffers)
            if(c.getRating()==0)
                num++;
            model.addAttribute("num",num);
        return "orderDetails";
    }

    @GetMapping("/rate/{id}")
    public String RateOrder(@PathVariable("id") int Id, Model model){
        String loggedInUSerName = securityService.findLoggedInUsername();
        User user=userService.findByUsername(loggedInUSerName);
        model.addAttribute("user",user);
        if(loggedInUSerName==null){
            model.addAttribute("loginStatus",false);
        }else{
            model.addAttribute("loginStatus",true);
        }
        return "rate";
    }

    @PostMapping("/rate/{id}")
    public String rateOrder(@PathVariable("id") int Id,@RequestParam("rating") int rating,Model model){
        orderRepository.updateRating(rating,Id);
        Product product=null;
        product =orderRepository.findProductById(Id);
      Float r1;
        r1=orderRepository.AvgRating(product.getItem().getId());
        Double r=(double)r1;
        DecimalFormat df = new DecimalFormat("#.#");
        String rate= df.format(r);
        System.out.println(r);
           rate+="/10";
           foodItemRepository.updateRating(rate, product.getItem().getId());
           model.addAttribute("category",product.getItem().getCategory());

        return "rate";
    }

    @PostMapping("/rateO/{id}")
    public String rateorder(@PathVariable("id") int Id,@RequestParam("rating") int rating,Model model){
        orderRepository.updateRating(rating,Id);
        Product product=null;
        product =orderRepository.findProductByIdO(Id);
        Float r;
        r=orderRepository.AvgOfferRating(product.getOffer().getId());
        String rate=Float.toString(r);
        rate+="/10";
        offerRepository.updateRating(rate, product.getOffer().getId());

        return "rate";
    }

    @GetMapping("/allOrders")
    public String allOrders(Model model){
        String loggedInUSerName = securityService.findLoggedInUsername();
        User user=userService.findByUsername(loggedInUSerName);
        model.addAttribute("user",user);
        if(loggedInUSerName==null){
            model.addAttribute("loginStatus",false);
        }else{
            model.addAttribute("loginStatus",true);
        }
        List<Order> orders=orderRepository.AllOrders();
        model.addAttribute("orders",orders);
        return "allOrders";
    }
    @GetMapping("/updateStatus/{id}")
    public String updateStatus(@PathVariable("id") int Id,Model model){
        String loggedInUSerName = securityService.findLoggedInUsername();
        User user=userService.findByUsername(loggedInUSerName);
        model.addAttribute("user",user);
        if(loggedInUSerName==null){
            model.addAttribute("loginStatus",false);
        }else{
            model.addAttribute("loginStatus",true);
        }
        model.addAttribute("Id",Id);
      return"updateStatus";
    }
    @PostMapping("/updateStatus/{id}")
    public String updateStatus(@PathVariable("id")int Id,@RequestParam("status") String status){
        orderRepository.setStatus(status,Id);
        return "redirect:/allOrders";
    }

}
