package com.roman.zadanie.db.entity;

import androidx.annotation.Nullable;


public class CategoryPrice {
    public String category;
    public Double price;
    public char type;

    public CategoryPrice(String category, Double price, char type) {
        this.category = category;
        this.price = price;
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }
}
