package com.example.User.Service.DTO;

public class OrderDTO {
    private Integer userID;
    private String itemName;
    private Double totalAmount;

    public OrderDTO() {}
    public OrderDTO(Integer userID, String itemName, Double totalAmount) {
        this.userID = userID;
        this.itemName = itemName;
        this.totalAmount = totalAmount;
    }


    public Integer getUserID() { return userID; }
    public void setUserID(Integer userID) { this.userID = userID; }

    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public Double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }
}
