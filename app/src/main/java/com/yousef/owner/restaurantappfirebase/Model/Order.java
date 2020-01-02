package com.yousef.owner.restaurantappfirebase.Model;

public class Order {
    private String prodectID;
    private String prodectname;
    private String quantity;
    private String price;
    private String discount;

    public Order() {
    }

    public Order(String prodectID, String prodectname, String quantity, String price, String discount) {
        this.prodectID = prodectID;
        this.prodectname = prodectname;
        this.quantity = quantity;
        this.price = price;
        this.discount = discount;
    }

    public String getProdectID() {
        return prodectID;
    }

    public void setProdectID(String prodectID) {
        this.prodectID = prodectID;
    }

    public String getProdectname() {
        return prodectname;
    }

    public void setProdectname(String prodectname) {
        this.prodectname = prodectname;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
}
