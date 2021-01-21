package com.dbms.cafe.repositories;

import com.dbms.cafe.models.FoodItem;
import com.dbms.cafe.models.Offer;
import com.dbms.cafe.models.Offer_product;

import java.util.List;

public interface OfferRepository {
    public void saveOffer(Offer offer);

    public void saveCombo(Offer_product combo);

    public List<Offer_product> findComboProducts(int Id);

    public List<Offer> allOffers();

    public Offer findOfferById(int Id);

    public Offer findLast();

    public void setStatus(String status, int Id);

    public List<Offer> activatedOffers();

    public void updateRating(String rate, int Id);
}