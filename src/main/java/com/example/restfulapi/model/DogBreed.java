package com.example.restfulapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class DogBreed {

    @JsonProperty("message")
    private Map<String, List<DogSubBreed>> breeds;

    @JsonProperty("status")
    private String status;

    // Default constructor
    public DogBreed() {}

    // Constructor with parameters
    public DogBreed(Map<String, List<DogSubBreed>> breeds, String status) {
        this.breeds = breeds;
        this.status = status;
    }

    public DogBreed(String breed, List<String> sub_breed, String status) {
        this.breeds = Map.of(breed, sub_breed.stream().map(DogSubBreed::new).toList());
        this.status = status;
    }

    // Getters and Setters
    public Map<String, List<DogSubBreed>> getBreeds() {
        return breeds;
    }

    public void setBreeds(Map<String, List<DogSubBreed>> breeds) {
        this.breeds = breeds;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "DogBreed{" +
                "breeds=" + breeds +
                ", status='" + status + '\'' +
                '}';
    }
}