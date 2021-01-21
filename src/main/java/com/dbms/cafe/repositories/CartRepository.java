package com.dbms.cafe.repositories;

import com.dbms.cafe.models.Cart;
import com.dbms.cafe.models.FoodItem;

import java.util.List;
import java.util.Map;

public interface CartRepository {
    public List<Cart> findCartItems(int id);
    public List<Cart> findCartOffers(int id);
    public void saveCart(Cart cart);
    public Cart findCartById(int userid,int productid);
    public Cart findCartByIdOffer(int userid,int offerid);
    public void updateCart(int userid, int productid,int quantity,int Id);
    public void updateCartOffer(int userid, int offerid,int quantity,int Id);
    public void deleteCart(int Id);
    public boolean cartExists(int userid, int productid);
    public boolean cartExistsOffer(int userid, int offerid);
}
