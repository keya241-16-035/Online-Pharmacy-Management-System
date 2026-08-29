package com.example.pharmacy.service;

import com.example.pharmacy.model.Medicine;

import java.util.List;

public interface MedicineService {
    List<Medicine> getAllMedicines();
    Medicine getMedicineById(Long id);
    Medicine saveMedicine(Medicine medicine);
    void deleteMedicine(Long id);
    List<Medicine> searchMedicines(String keyword);
    List<Medicine> getLowStockMedicines(Integer threshold);
    List<Medicine> getExpiringMedicines(int daysAhead);
    long countTotalMedicines();
}
