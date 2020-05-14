package com.example.androidassignment2;

//Food class for storing foods and drinks for FoodAdapter usage
public class Food {
    private String name;
    private String code;
    private double price;
    private int imageRes;

    //default constructor
    public Food() {
        name="";
        code="";
        price=0.0;
        imageRes=0;
    }

    //overloaded constructor
    public Food(String name, String code, double price,int imageRes) {
        this.name = name;
        this.code = code;
        this.price = price;
        this.imageRes = imageRes;
    }

    //getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode(){return code;}

    public void setCode(String code){ this.code = code; }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getImageRes(){
        return imageRes;
    }

    public void setImageRes(int imageRes){
        this.imageRes = imageRes;
    }

    //toString method
    @Override
    public String toString() {
        return "Food{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", price=" + price +
                ", imageRes=" + imageRes +
                '}';
    }
}
