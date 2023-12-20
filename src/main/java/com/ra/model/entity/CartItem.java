package com.ra.model.entity;

import com.ra.model.entity.Product;

public class CartItem {
    private Product product;

    private int quantity;
    public CartItem() {};

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
