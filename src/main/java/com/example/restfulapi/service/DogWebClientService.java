package com.example.restfulapi.service;

import com.example.restfulapi.model.DogBreed;
import com.example.restfulapi.model.DogImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import com.fasterxml.jackson.databind.JsonNode;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * Alternative service using WebClient for reactive API consumption
 * This demonstrates the modern, reactive approach to consuming REST APIs
 */
@Service
public class DogWebClientService {

    private static final Logger logger = LoggerFactory.getLogger(DogWebClientService.class);

    @Autowired
    private WebClient webClient;

    /**
     * Get all dog breeds using WebClient (reactive approach)
     * @return List of breed names
     */
    public List<String> getAllBreeds() {
        try {
            logger.info("Fetching all breeds using WebClient");

            Mono<DogBreed> response = webClient
                    .get()
                    .uri("/breeds/list/all")
                    .retrieve()
                    .bodyToMono(DogBreed.class)
                    .timeout(Duration.ofSeconds(10));

            DogBreed dogBreed = response.block();

            if (dogBreed != null && "success".equals(dogBreed.getStatus())) {
                List<String> breedNames = new ArrayList<>(dogBreed.getBreeds().keySet());
                logger.info("Successfully fetched {} breeds using WebClient", breedNames.size());
                return breedNames;
            }

            return new ArrayList<>();

        } catch (Exception e) {
            logger.error("Error fetching breeds using WebClient: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch dog breeds", e);
        }
    }

    /**
     * Get all dog sub-breeds by breed using WebClient (reactive approach)
     * @return List of sub-breed names
     */
    public List<String> getAllSubBreeds(String breed) {
        try {
            logger.info("Fetching all sub-breeds for breed '{}' using WebClient", breed);

            Mono<JsonNode> response = webClient
                    .get()
                    .uri("/breed/{breed}/list", breed)
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .timeout(Duration.ofSeconds(10));

            JsonNode jsonNode = response.block();

            if (jsonNode != null && jsonNode.has("message")) {
                List<String> subBreedNames = new ArrayList<>();
                jsonNode.get("message").forEach(node -> subBreedNames.add(node.asText()));
                logger.info("Successfully fetched {} sub-breeds using WebClient", subBreedNames.size());
                return subBreedNames;
            }

            return new ArrayList<>();

        } catch (Exception e) {
            logger.error("Error fetching breeds using WebClient: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch dog breeds", e);
        }
    }

    /**
     * Get a random dog image using WebClient
     * @return Dog image URL
     */
    public List<String> getRandomDogImages(int count) {
        try {
            logger.info("Fetching random dog image using WebClient");

            String uri = count > 0 ? "/breeds/image/random/{count}" : "/breeds/image/random";
            Mono<JsonNode> response = webClient
                    .get()
                    .uri(uri, count)
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .timeout(Duration.ofSeconds(10));

            JsonNode dogImage = response.block();

            if (dogImage != null && dogImage.has("message")) {
                List<String> imageUrls = new ArrayList<>();
                if (count == 0) {
                    imageUrls.add(dogImage.get("message").asText());
                } else {
                    dogImage.get("message").forEach(node -> imageUrls.add(node.asText()));
                }
                logger.info("Successfully fetched {} random dog image(s) using WebClient", imageUrls.size());
                return imageUrls;
            }

            return null;

        } catch (Exception e) {
            logger.error("Error fetching random dog image(s) using WebClient: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch random dog image", e);
        }
    }

    /**
     * Get all dog images based on breed using WebClient
     * @return Dog image URL
     */
    public List<String> getAllBreedImages(String breed) {
        try {
            logger.info("Fetching random dog image using WebClient");

            Mono<JsonNode> response = webClient
                    .get()
                    .uri("/breed/{breed}/images", breed)
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .timeout(Duration.ofSeconds(10));

            JsonNode dogImage = response.block();

            if (dogImage != null && dogImage.has("message")) {
                List<String> imageUrls = new ArrayList<>();
                dogImage.get("message").forEach(node -> imageUrls.add(node.asText()));
                logger.info("Successfully fetched all dog breed images using WebClient");
                return imageUrls;
            }

            return null;

        } catch (Exception e) {
            logger.error("Error fetching random dog image using WebClient: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch random dog image", e);
        }
    }

    /**
     * Get all dog images based on breed using WebClient
     * @return Dog image URL
     */
    public List<String> getRandomBreedImages(String breed, int count) {
        try {
            logger.info("Fetching {} random dog images for breed '{}' using WebClient", count, breed);

            String uri = count > 0 ? "/breed/{breed}/images/random/{count}" : "/breed/{breed}/images/random";
            Mono<JsonNode> response = webClient
                    .get()
                    .uri(uri, breed, count)
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .timeout(Duration.ofSeconds(10));

            JsonNode dogImage = response.block();

            if (dogImage != null && dogImage.has("message")) {
                List<String> imageUrls = new ArrayList<>();
                if (count == 0) {
                    imageUrls.add(dogImage.get("message").asText());
                } else {
                    dogImage.get("message").forEach(node -> imageUrls.add(node.asText()));
                }
                logger.info("Successfully fetched {} random dog image(s) using WebClient", imageUrls.size());
                return imageUrls;
            }

            return null;

        } catch (Exception e) {
            logger.error("Error fetching random dog image using WebClient: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch random dog image", e);
        }
    }

    /**
     * Example of error handling with WebClient
     * @param breed The breed name
     * @return List of image URLs
     */
    public List<String> getBreedImagesWithErrorHandling(String breed, int count) {
        try {
            logger.info("Fetching {} images for breed {} using WebClient with error handling", count, breed);

            Mono<List> response = webClient
                    .get()
                    .uri("/breed/{breed}/images/random/{count}", breed, count)
                    .retrieve()
                    .onStatus(
                        status -> status.is4xxClientError(),
                        clientResponse -> {
                            logger.warn("Client error: {}", clientResponse.statusCode());
                            return Mono.error(new RuntimeException("Client error: " + clientResponse.statusCode()));
                        }
                    )
                    .onStatus(
                        status -> status.is5xxServerError(),
                        clientResponse -> {
                            logger.error("Server error: {}", clientResponse.statusCode());
                            return Mono.error(new RuntimeException("Server error: " + clientResponse.statusCode()));
                        }
                    )
                    .bodyToMono(List.class)
                    .timeout(Duration.ofSeconds(10))
                    .doOnError(error -> logger.error("WebClient error: {}", error.getMessage()));

            @SuppressWarnings("unchecked")
            List<String> images = response.block();

            return images != null ? images : new ArrayList<>();

        } catch (Exception e) {
            logger.error("Error fetching images for breed {} using WebClient: {}", breed, e.getMessage());
            throw new RuntimeException("Failed to fetch images for breed " + breed, e);
        }
    }
}