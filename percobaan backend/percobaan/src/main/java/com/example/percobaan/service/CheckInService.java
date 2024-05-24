package com.example.percobaan.service;

import com.example.percobaan.model.CheckIn;
import com.example.percobaan.model.User;
import com.example.percobaan.repository.CheckInRepository;
import com.example.percobaan.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckInService {

    @Autowired
    private CheckInRepository checkInRepository;

    @Autowired
    private UserRepository userRepository;

    public CheckIn performCheckIn(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        CheckIn checkIn = new CheckIn();
        checkIn.setUser(user);

        return checkInRepository.save(checkIn);
    }

    public List<CheckIn> getAllCheckIns() {
        return checkInRepository.findAll();
    }
}
