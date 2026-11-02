package com.example.pharmacyservice.controller;

import com.example.pharmacyservice.dto.MedicineStockResponse;
import com.example.pharmacyservice.service.PharmacyInventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/pharmacy/inventory")
public class PharmacyInventoryController {

    private final PharmacyInventoryService inventoryService;

    public PharmacyInventoryController(PharmacyInventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/{medicineCode}")
    public ResponseEntity<MedicineStockResponse> checkMedicineStock(@PathVariable String medicineCode) {
        return inventoryService.checkStock(medicineCode);
    }
}