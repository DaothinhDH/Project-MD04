package com.ra.model.entity;




public class Product {
    private int productId;
    private String productName;
    private String image;
    private String description;
    private float price;
    private int stock;
    private boolean status;
    private Category category;

    public Product() {
    }

    public Product(int productId, String productName, String image, String description, float price, int stock, boolean status, Category category) {
        this.productId = productId;
        this.productName = productName;
        this.image = image;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.status = status;
        this.category = category;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
