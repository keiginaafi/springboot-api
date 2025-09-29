package com.example.restfulapi.service;

import com.example.restfulapi.model.DogBreed;
import com.example.restfulapi.model.DogImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DogService {
    
    private static final Logger logger = LoggerFactory.getLogger(DogService.class);
    private static final String DOG_API_BASE_URL = "https://dog.ceo/api";
    
    @Autowired
    private RestTemplate restTemplate;
    
    /**
     * Get all dog breeds from the Dog CEO API
     * @return List of breed names
     */
    public List<String> getAllBreeds() {
        try {
            String url = DOG_API_BASE_URL + "/breeds/list/all";
            logger.info("Fetching all breeds from: {}", url);
            
            // Method 1: Using getForObject (simplest)
            DogBreed response = restTemplate.getForObject(url, DogBreed.class);
            
            if (response != null && "success".equals(response.getStatus())) {
                List<String> breedNames = new ArrayList<>(response.getBreeds().keySet());
                logger.info("Successfully fetched {} breeds", breedNames.size());
                return breedNames;
            }
            
            logger.warn("API response was null or unsuccessful");
            return new ArrayList<>();
            
        } catch (RestClientException e) {
            logger.error("Error fetching breeds from API: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch dog breeds", e);
        }
    }
    
    /**
     * Get sub-breeds for a specific breed
     * @param breed The breed name
     * @return List of sub-breeds
     */
    public List<String> getSubBreeds(String breed) {
        try {
            String url = DOG_API_BASE_URL + "/breed/" + breed + "/list";
            logger.info("Fetching sub-breeds for {} from: {}", breed, url);
            
            // Method 2: Using exchange method with headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            
            ResponseEntity<DogBreed> response = restTemplate.exchange(
                url, 
                HttpMethod.GET, 
                entity, 
                DogBreed.class
            );
            
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                DogBreed dogBreed = response.getBody();
                if ("success".equals(dogBreed.getStatus())) {
                    // For sub-breeds, the API returns an array in the message field
                    return new ArrayList<>(); // Simplified for this example
                }
            }
            
            return new ArrayList<>();
            
        } catch (RestClientException e) {
            logger.error("Error fetching sub-breeds for {}: {}", breed, e.getMessage());
            throw new RuntimeException("Failed to fetch sub-breeds for " + breed, e);
        }
    }
    
    /**
     * Get a random dog image
     * @return Dog image URL
     */
    public String getRandomDogImage() {
        try {
            String url = DOG_API_BASE_URL + "/breeds/image/random";
            logger.info("Fetching random dog image from: {}", url);
            
            // Method 3: Using getForEntity to get full response
            ResponseEntity<DogImage> response = restTemplate.getForEntity(url, DogImage.class);
            
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                DogImage dogImage = response.getBody();
                if ("success".equals(dogImage.getStatus())) {
                    logger.info("Successfully fetched random dog image");
                    return dogImage.getImageUrl();
                }
            }
            
            logger.warn("Failed to fetch random dog image");
            return null;
            
        } catch (RestClientException e) {
            logger.error("Error fetching random dog image: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch random dog image", e);
        }
    }
    
    /**
     * Get random images for a specific breed
     * @param breed The breed name
     * @param count Number of images to fetch
     * @return List of image URLs
     */
    public List<String> getBreedImages(String breed, int count) {
        try {
            String url = DOG_API_BASE_URL + "/breed/" + breed + "/images/random/" + count;
            logger.info("Fetching {} images for breed {} from: {}", count, breed, url);
            
            // Method 4: Using postForObject (even though this is a GET, showing POST example)
            // For actual POST requests, you would pass a request body
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
            
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Map<String, Object> responseBody = response.getBody();
                if ("success".equals(responseBody.get("status"))) {
                    @SuppressWarnings("unchecked")
                    List<String> images = (List<String>) responseBody.get("message");
                    logger.info("Successfully fetched {} images for breed {}", images.size(), breed);
                    return images != null ? images : new ArrayList<>();
                }
            }
            
            return new ArrayList<>();
            
        } catch (RestClientException e) {
            logger.error("Error fetching images for breed {}: {}", breed, e.getMessage());
            throw new RuntimeException("Failed to fetch images for breed " + breed, e);
        }
    }
    
    /**
     * Example of making a POST request (hypothetical endpoint)
     * @param breedName The breed name to add
     * @return Success status
     */
    public boolean addFavoriteBreed(String breedName) {
        try {
            // This is a hypothetical POST endpoint for demonstration
            String url = DOG_API_BASE_URL + "/favorites";
            
            // Create request body
            Map<String, String> requestBody = Map.of("breed", breedName);
            
            // Set headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            // Create HTTP entity with body and headers
            HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);
            
            // Make POST request
            ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);
            
            return response.getStatusCode() == HttpStatus.CREATED;
            
        } catch (RestClientException e) {
            logger.error("Error adding favorite breed {}: {}", breedName, e.getMessage());
            return false;
        }
    }
}