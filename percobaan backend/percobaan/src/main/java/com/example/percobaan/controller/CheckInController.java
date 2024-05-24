package com.example.percobaan.controller;

import com.example.percobaan.model.CheckIn;
import com.example.percobaan.model.User;
import com.example.percobaan.repository.CheckInRepository;
import com.example.percobaan.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/checkin")
@CrossOrigin(origins = "http://localhost:5174")
public class CheckInController {


    @Autowired
    private CheckInRepository checkInRepository;

    @Autowired
    private UserRepository userRepository;
    @CrossOrigin(origins = "http://localhost:5173")
    // Endpoint untuk melakukan check-in
    @PostMapping("/{userId}")
    public ResponseEntity<String> checkIn(@PathVariable Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        User user = userOptional.get();
        CheckIn checkIn = new CheckIn();
        checkIn.setUser(user);
        checkIn.setCheckinTime(LocalDateTime.now());

        checkInRepository.save(checkIn);
        return ResponseEntity.status(HttpStatus.CREATED).body("Check-in success for user: " + user.getUsername());
    }

    // Endpoint untuk mendapatkan semua data check-in

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/all")
    public ResponseEntity<List<CheckIn>> getAllCheckIns() {
        List<CheckIn> checkIns = checkInRepository.findAll();
        return ResponseEntity.ok(checkIns);
    }
}
