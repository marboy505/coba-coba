package com.example.percobaan.service;

import com.example.percobaan.model.CheckOut;
import com.example.percobaan.model.User;
import com.example.percobaan.repository.CheckOutRepository;
import com.example.percobaan.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckOutService {

    @Autowired
    private CheckOutRepository checkOutRepository;

    @Autowired
    private UserRepository userRepository;

    public CheckOut performCheckOut(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        CheckOut checkOut = new CheckOut();
        checkOut.setUser(user);

        return checkOutRepository.save(checkOut);
    }

    public List<CheckOut> getAllCheckOuts() {
        return checkOutRepository.findAll();
    }
}
