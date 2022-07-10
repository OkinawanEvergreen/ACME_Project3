package com.example.acme_project3;

public class Item {
    private String ID;
    private int listID;
    private String productName;
    private String details;
    private String department;
    private String quantity;
    private Double cost;

    /*Functions below are what will retrieve user credentials*/

    public Item(int listID, String ID, String productName, String details, String department, String quantity, Double cost) {
        this.ID = ID;
        this.listID = listID;
        this.productName = productName;
        this.details = details;
        this.department = department;
        this.quantity = quantity;
        this.cost = cost;
    }

    public int getListID() {
        return listID;
    }

    public void setListID(int listID) {
        this.listID = listID;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String itemName) {
        this.productName = itemName;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
