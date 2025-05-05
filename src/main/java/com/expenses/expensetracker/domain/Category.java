package com.expenses.expensetracker.domain;

public class Category
{
    private Integer categoryID;
    private Integer userId;
    private String title;
    private String description;
    private Double totalExpense;

    public Category(Integer categoryID, Integer userID, String title, String description, Double totalExpense) {
        this.categoryID = categoryID;
        this.userId = userID;
        this.title = title;
        this.description = description;
        this.totalExpense = totalExpense;
    }

    public Integer getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Integer categoryID) {
        this.categoryID = categoryID;
    }

    public Integer getUserID() {
        return userId;
    }

    public void setUserID(Integer userID) {
        this.userId = userID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(Double totalExpense) {
        this.totalExpense = totalExpense;
    }
}
