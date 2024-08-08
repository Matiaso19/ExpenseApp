package com.soyhenry.expenseApp.dto;

public class CategoryRequestDto {
    private Long id;
    private String name;

    public CategoryRequestDto(String categoryName, Long categoryId) {
        this.name = categoryName;
        this.id = categoryId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
