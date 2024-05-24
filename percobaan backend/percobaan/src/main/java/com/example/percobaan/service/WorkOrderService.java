package com.example.percobaan.service;

import com.example.percobaan.model.WorkOrder;
import com.example.percobaan.repository.WorkOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkOrderService {

    @Autowired
    private WorkOrderRepository workOrderRepository;

    public List<WorkOrder> getAllWorkOrders() {
        return workOrderRepository.findAll();
    }

    public Optional<WorkOrder> getWorkOrderById(Long id) {
        return workOrderRepository.findById(id);
    }

    public WorkOrder createWorkOrder(WorkOrder workOrder) {
        return workOrderRepository.save(workOrder);
    }

    public WorkOrder updateWorkOrder(Long id, WorkOrder updatedWorkOrder) {
        Optional<WorkOrder> existingWorkOrderOptional = workOrderRepository.findById(id);
        if (existingWorkOrderOptional.isPresent()) {
            WorkOrder existingWorkOrder = existingWorkOrderOptional.get();
            existingWorkOrder.setTitle(updatedWorkOrder.getTitle());
            existingWorkOrder.setDescription(updatedWorkOrder.getDescription());
            existingWorkOrder.setStatus(updatedWorkOrder.getStatus());
            existingWorkOrder.setAssignedTo(updatedWorkOrder.getAssignedTo());
            return workOrderRepository.save(existingWorkOrder);
        } else {
            return null; // WorkOrder with the given id not found
        }
    }

    public void deleteWorkOrder(Long id) {
        workOrderRepository.deleteById(id);
    }
}
