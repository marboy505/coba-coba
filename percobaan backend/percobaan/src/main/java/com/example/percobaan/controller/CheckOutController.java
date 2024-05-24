package com.example.percobaan.controller;

import com.example.percobaan.model.CheckOut;
import com.example.percobaan.model.User;
import com.example.percobaan.repository.CheckOutRepository;
import com.example.percobaan.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/checkout")
@CrossOrigin(origins = "http://localhost:5174")
public class CheckOutController {

    @Autowired
    private CheckOutRepository checkOutRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/{userId}")
    public ResponseEntity<String> checkOut(@PathVariable Long userId) {
        try {
            Optional<User> userOptional = userRepository.findById(userId);
            if (!userOptional.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            User user = userOptional.get();
            CheckOut checkOut = new CheckOut();
            checkOut.setUser(user);
            checkOut.setCheckoutTime(LocalDateTime.now());

            checkOutRepository.save(checkOut);
            return ResponseEntity.status(HttpStatus.CREATED).body("Check-out success for user: " + user.getUsername());
        } catch (Exception e) {
            // Log exception
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during checkout.");
        }
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/all")
    public ResponseEntity<String> getAllCheckOuts() {
        try {
            List<CheckOut> checkOuts = checkOutRepository.findAll();
            return ResponseEntity.ok(checkOuts.toString());
        } catch (Exception e) {
            // Log exception
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching check-outs.");
        }
    }
}
