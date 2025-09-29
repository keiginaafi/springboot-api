package com.example.restfulapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DogImage {
    
    @JsonProperty("message")
    private String imageUrl;
    
    @JsonProperty("status")
    private String status;
    
    // Default constructor
    public DogImage() {}
    
    // Constructor with parameters
    public DogImage(String imageUrl, String status) {
        this.imageUrl = imageUrl;
        this.status = status;
    }
    
    // Getters and Setters
    public String getImageUrl() {
        return imageUrl;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return "DogImage{" +
                "imageUrl='" + imageUrl + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}