package com.example.pharmacy.repository;

import com.example.pharmacy.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;


public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    List<Supplier> findByNameContainingIgnoreCaseOrContactPersonContainingIgnoreCaseOrPhoneContaining(String name, String contact, String phone);
}
