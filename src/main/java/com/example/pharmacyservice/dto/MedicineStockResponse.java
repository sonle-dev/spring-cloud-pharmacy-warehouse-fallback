package com.example.pharmacyservice.dto;

import java.time.LocalDateTime;

public class MedicineStockResponse {
    private String medicineCode;
    private int availableStock;
    private String source;
    private String message;
    private String timestamp;

    public MedicineStockResponse() {
    }

    public MedicineStockResponse(String medicineCode, int availableStock, String source, String message) {
        this.medicineCode = medicineCode;
        this.availableStock = availableStock;
        this.source = source;
        this.message = message;
        this.timestamp = LocalDateTime.now().toString();
    }

    public String getMedicineCode() {
        return medicineCode;
    }

    public void setMedicineCode(String medicineCode) {
        this.medicineCode = medicineCode;
    }

    public int getAvailableStock() {
        return availableStock;
    }

    public void setAvailableStock(int availableStock) {
        this.availableStock = availableStock;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}