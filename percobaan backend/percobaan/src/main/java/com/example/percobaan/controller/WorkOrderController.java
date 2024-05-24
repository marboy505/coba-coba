package com.example.percobaan.controller;

import com.example.percobaan.model.WorkOrder;
import com.example.percobaan.model.User;
import com.example.percobaan.service.WorkOrderService;
import com.example.percobaan.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/workorders")
@CrossOrigin(origins = "http://localhost:5174")
public class WorkOrderController {

    @Autowired
    private WorkOrderService workOrderService;

    @Autowired
    private UserRepository userRepository;

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping
    public ResponseEntity<List<WorkOrder>> getAllWorkOrders() {
        try {
            List<WorkOrder> workOrders = workOrderService.getAllWorkOrders();
            return new ResponseEntity<>(workOrders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/{id}")
    public ResponseEntity<WorkOrder> getWorkOrderById(@PathVariable("id") Long id) {
        try {
            Optional<WorkOrder> workOrderOptional = workOrderService.getWorkOrderById(id);
            return workOrderOptional.map(workOrder -> new ResponseEntity<>(workOrder, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/created")
    public ResponseEntity<WorkOrder> createWorkOrder(@RequestBody WorkOrder workOrder) {
        try {
            if (workOrder.getAssignedTo() != null) {
                Optional<User> userOptional = userRepository.findById(workOrder.getAssignedTo().getId());
                if (userOptional.isPresent()) {
                    workOrder.setAssignedTo(userOptional.get());
                } else {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }
            WorkOrder createdWorkOrder = workOrderService.createWorkOrder(workOrder);
            return new ResponseEntity<>(createdWorkOrder, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @PutMapping("/{id}")
    public ResponseEntity<WorkOrder> updateWorkOrder(@PathVariable("id") Long id, @RequestBody WorkOrder updatedWorkOrder) {
        try {
            if (updatedWorkOrder.getAssignedTo() != null) {
                Optional<User> userOptional = userRepository.findById(updatedWorkOrder.getAssignedTo().getId());
                if (userOptional.isPresent()) {
                    updatedWorkOrder.setAssignedTo(userOptional.get());
                } else {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }
            WorkOrder updated = workOrderService.updateWorkOrder(id, updatedWorkOrder);
            if (updated != null) {
                return new ResponseEntity<>(updated, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkOrder(@PathVariable("id") Long id) {
        try {
            workOrderService.deleteWorkOrder(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
