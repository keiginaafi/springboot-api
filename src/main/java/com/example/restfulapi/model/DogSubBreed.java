package com.example.restfulapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class DogSubBreed {
    private String sub_breed;

    // Default constructor
    public DogSubBreed() {}

    // Constructor with parameters
    public DogSubBreed(String sub_breed) {
        this.sub_breed = sub_breed;
    }

    // Getters and Setters
    public String getSubBreed() {
        return sub_breed;
    }

    public void setSubBreed(String sub_breed) {
        this.sub_breed = sub_breed;
    }

    @Override
    public String toString() {
        return "DogSubBreed{" +
                "sub-breed=" + sub_breed +
                '}';
    }
}