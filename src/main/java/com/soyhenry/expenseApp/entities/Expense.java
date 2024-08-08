package com.soyhenry.expenseApp.entities;

import java.util.Date;

public class Expense {
    private Long id;
    private Double amount;
    private Date date;
    private String categoryName;
    private Integer categoryId;





    public Expense() {
    }

    public Expense(Double amount, Date date, String categoryName, Integer categoryId) {
        this.amount = amount;
        this.date = date;
        this.categoryName = categoryName;
        this.categoryId = categoryId;
    }
    public String getCategoryName() {
        return categoryName;
    }
    public Integer getCategoryId() {
        return categoryId;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }



    public void setCategoryId(Integer category) {
        this.categoryId = category;
    }



    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "id=" + id +
                ", amount=" + amount +
                ", date=" + date +
                ", categoryName='" + categoryName + '\'' +
                ", categoryId=" + categoryId +
                '}';
    }
}
