package com.example.androidassignment2;

//Class to store Customer
public class Customer {

    //private instances
    private String name;
    private String password;
    private String contact;
    private String postcode;
    private String area;

    //default constructor
    public Customer() {
        name = "";
        password="";
        contact = "";
        postcode = "";
        area = "";
    }

    //overloaded constructor
    public Customer(String name, String password, String contact, String postcode, String area) {
        this.name = name;
        this.password = password;
        this.contact = contact;
        this.postcode = postcode;
        this.area = area;
    }

    //getters and setters
    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getArea() {
        return area;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //toString
    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", contact='" + contact + '\'' +
                ", postcode='" + postcode + '\'' +
                ", area='" + area + '\'' +
                '}';
    }
}
