package com.example.pharmacyservice.service;

import com.example.pharmacyservice.dto.MedicineStockResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class PharmacyInventoryService {

    private static final Logger log = LoggerFactory.getLogger(PharmacyInventoryService.class);
    private final RestTemplate restTemplate;

    // Kho đệm tồn kho cục bộ tại quầy thuốc
    private static final Map<String, Integer> LOCAL_STOCK_CACHE = new HashMap<>();

    static {
        LOCAL_STOCK_CACHE.put("PANADOL_500MG", 35);
        LOCAL_STOCK_CACHE.put("BERBERIN_100MG", 50);
        LOCAL_STOCK_CACHE.put("AMOXICILLIN_500MG", 20);
    }

    public PharmacyInventoryService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @CircuitBreaker(name = "warehouseCB", fallbackMethod = "checkWarehouseFallback")
    public ResponseEntity<MedicineStockResponse> checkStock(String medicineCode) {
        log.info("Dang kiem tra ton kho Kho tong qua Warehouse-Service voi ma: {}", medicineCode);
        String warehouseUrl = "http://localhost:8082/api/warehouse/inventory/" + medicineCode;

        // Thuc hien goi sang kho tong
        restTemplate.getForObject(warehouseUrl, String.class);

        // Neu goi thanh cong
        MedicineStockResponse response = new MedicineStockResponse(
                medicineCode,
                250,
                "CENTRAL_WAREHOUSE",
                "Kiem tra ton kho tong thanh cong"
        );
        return ResponseEntity.ok(response);
    }

    // Fallback khi mat ket noi kho tong theo dung yeu cau de bai
    public ResponseEntity<MedicineStockResponse> checkWarehouseFallback(String medicineCode, Throwable e) {
        log.warn("Kho tong bi loi hoac mach dang OPEN. Su dung ton kho cuc bo. Chi tiet: {}", e.getMessage());

        int localStock = LOCAL_STOCK_CACHE.getOrDefault(medicineCode.toUpperCase(), 15);

        MedicineStockResponse fallbackResponse = new MedicineStockResponse(
                medicineCode,
                localStock,
                "LOCAL_CACHE",
                "Không thể kết nối kho tổng. Hệ thống sẽ sử dụng dữ liệu tồn kho cục bộ để tiếp tục giao dịch"
        );

        return ResponseEntity.ok(fallbackResponse);
    }
}