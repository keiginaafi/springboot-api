package com.example.restfulapi.controller;

// import com.example.restfulapi.service.DogService;
import com.example.restfulapi.service.DogWebClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dogs")
@CrossOrigin(origins = "*")
public class DogController {

    @Autowired
    // private DogService dogService;
    private DogWebClientService DogWebClientService;

    // GET /api/dogs/breeds - Get all dog breeds
    @GetMapping("/breeds")
    public ResponseEntity<List<String>> getAllBreeds() {
        try {
            List<String> breeds = DogWebClientService.getAllBreeds();
            if (breeds.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(breeds, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET /api/dogs/{breed}/sub-breeds - Get sub-breeds for a specific breed
    @GetMapping("/{breed}/sub-breeds")
    public ResponseEntity<List<String>> getSubBreeds(@PathVariable String breed) {
        try {
            List<String> subBreeds = DogWebClientService.getAllSubBreeds(breed);
            return new ResponseEntity<>(subBreeds, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET /api/dogs/random-image - Get a random dog image
    // can use query params count={count} to get multiple images
    @GetMapping("/random-image")
    public ResponseEntity<List<String>> getRandomDogImage(@RequestParam(defaultValue = "0") int count) {
        try {
            if (count > 50) {
                throw new IllegalArgumentException("Count must be less than or equal to 50");
            }
            List<String> imageUrl = DogWebClientService.getRandomDogImages(count);
            if (imageUrl.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(imageUrl, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET /api/dogs/{breed}/images - Get all images for a specific breed
    @GetMapping("/{breed}/images")
    public ResponseEntity<List<String>> getAllBreedImages(@PathVariable String breed) {
        try {
            List<String> images = DogWebClientService.getAllBreedImages(breed);
            if (images.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(images, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET /api/dogs/{breed}/images/random?count={count} - Get all images for a specific breed
    @GetMapping("/{breed}/images/random")
    public ResponseEntity<List<String>> getRandomBreedImages(
            @PathVariable String breed,
            @RequestParam(defaultValue = "0") int count) {
        try {
            if (count > 50) {
                throw new IllegalArgumentException("Count must be less than or equal to 50");
            }
            List<String> images = DogWebClientService.getRandomBreedImages(breed, count);
            if (images.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(images, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}