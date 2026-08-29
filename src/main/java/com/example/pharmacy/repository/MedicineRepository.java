package com.example.pharmacy.repository;

import com.example.pharmacy.model.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.time.LocalDate;
import java.util.List;


public interface MedicineRepository extends JpaRepository<Medicine, Long> {
    
    List<Medicine> findByNameContainingIgnoreCase(String name);
    
    List<Medicine> findByQuantityLessThanEqual(Integer threshold);
    
    List<Medicine> findByExpiryDateBefore(LocalDate date);
    
    List<Medicine> findByExpiryDateBetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT m FROM Medicine m WHERE LOWER(m.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(m.category.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Medicine> searchByKeyword(@Param("keyword") String keyword);
}
