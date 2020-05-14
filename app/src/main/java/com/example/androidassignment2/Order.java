package com.example.androidassignment2;

import java.util.ArrayList;
//Order class to store Customer and Food classes
public class Order {
    private Customer cust;
    private ArrayList<Food> foods = new ArrayList<>();
    private double price = 0.0;
    private String review;

    //default constructor
    public Order(){
        cust = new Customer();
        price = 0.0;
        review = "";
    }

    //overloaded constructor
    public Order(Customer c, Food f, double p, String r){
        cust = c;
        foods.add(f);
        price = p + price;
        review = r;
    }

    //getters and setters
    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Customer getCust() {
        return cust;
    }

    public void setCust(Customer cust) {
        this.cust = cust;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ArrayList<Food> getFoods() {
        return foods;
    }

    public void setFoods(ArrayList<Food> foods) {
        this.foods = foods;
    }

    @Override
    public String toString() {
        return "Order{" +
                "cust=" + cust +
                ", price=" + price +
                ", review='" + review + '\'' +
                '}';
    }

    /**
     * method to add selected Food to Order
     * @param food selected food or drink
     */
    public void addToOrder(Food food){
        foods.add(food);
        price = price + food.getPrice();
    }

    /**
     * method to clear the ArrayList of Food and prices containing the previously selected foods or drinks
     */
    public void removeOrder(){
        foods.clear();
        price = 0;
    }
}
