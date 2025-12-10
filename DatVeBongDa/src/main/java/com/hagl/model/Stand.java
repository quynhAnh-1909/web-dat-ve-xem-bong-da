package com.hagl.model;

public class Stand {
    private String standName;
    private double fixedPrice; // GiaVeCoDinh

    public Stand() {}

    public Stand(String standName, double fixedPrice) {
        this.standName = standName;
        this.fixedPrice = fixedPrice;
    }
    
    // Getters and Setters
    public String getStandName() { return standName; }
    public double getFixedPrice() { return fixedPrice; }
    // ...
}