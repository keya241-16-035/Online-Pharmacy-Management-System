package com.example.pharmacy.service.impl;

import com.example.pharmacy.exception.ResourceNotFoundException;
import com.example.pharmacy.model.Medicine;
import com.example.pharmacy.repository.MedicineRepository;
import com.example.pharmacy.service.MedicineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MedicineServiceImpl implements MedicineService {

    private final MedicineRepository medicineRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Medicine> getAllMedicines() {
        return medicineRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Medicine getMedicineById(Long id) {
        return medicineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medicine not found with id: " + id));
    }

    @Override
    public Medicine saveMedicine(Medicine medicine) {
        return medicineRepository.save(medicine);
    }

    @Override
    public void deleteMedicine(Long id) {
        if (!medicineRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cannot delete. Medicine not found with id: " + id);
        }
        medicineRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Medicine> searchMedicines(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllMedicines();
        }
        return medicineRepository.searchByKeyword(keyword.trim());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Medicine> getLowStockMedicines(Integer threshold) {
        return medicineRepository.findByQuantityLessThanEqual(threshold != null ? threshold : 10);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Medicine> getExpiringMedicines(int daysAhead) {
        LocalDate today = LocalDate.now();
        LocalDate targetDate = today.plusDays(daysAhead);
        return medicineRepository.findByExpiryDateBetween(today, targetDate);
    }

    @Override
    @Transactional(readOnly = true)
    public long countTotalMedicines() {
        return medicineRepository.count();
    }
}
