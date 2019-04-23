package edu.svsu.rentit.models;

import android.util.Log;

import java.io.Serializable;

public class Listing implements Serializable {

    private int userId;
    private String username;
    private String title;
    private String description;
    private String address;
    private String contact;
    private String price;
    private double distance;
    private String review;
    private String reviewCount;



    public Listing(int newUserId, String newUsername, String newTitle, String newDescription, String newAddress, double newDistance,
                   String newContact, String newPrice, String newReview, String newReviewCount) {

        userId = newUserId;
        username = newUsername;
        title = newTitle;
        description = newDescription;
        address = newAddress;
        contact = newContact;
        price = newPrice;
        distance = newDistance;
        review = newReview;
        reviewCount = newReviewCount;

    }
    public Listing(int newUserId, String newUsername, String newTitle, String newDescription, String newAddress, double newDistance,
                   String newContact, String newPrice) {

        userId = newUserId;
        username = newUsername;
        title = newTitle;
        description = newDescription;
        address = newAddress;
        contact = newContact;
        price = newPrice;
        distance = newDistance;


    }

    public int getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getTitle()
    {
        return title;
    }
    public String getDescription()
    {
        return description;
    }
    public String getAddress()
    {
        return address;
    }
    public String getContact()
    {
        return contact;
    }
    public String getPrice()
    {
        return price;
    }
    public double getDistance() { return distance; }
    public String getReview(){ return review;}
    public String getReviewCount(){ return reviewCount;}

}
