package com.soyhenry.expenseApp.dto;

import java.util.Date;

public class ExpenseRequestDto {

    private Double amount;
    private CategoryRequestDto categoryRequestDto;
    private Date date;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public CategoryRequestDto getCategoryRequestDto() {
        return categoryRequestDto;
    }

    public void setCategoryRequestDto(CategoryRequestDto categoryRequestDto) {
        this.categoryRequestDto = categoryRequestDto;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
