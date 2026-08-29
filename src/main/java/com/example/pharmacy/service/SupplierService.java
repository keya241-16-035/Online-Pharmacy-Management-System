package com.example.pharmacy.service;

import com.example.pharmacy.model.Supplier;

import java.util.List;

public interface SupplierService {
    List<Supplier> getAllSuppliers();
    Supplier getSupplierById(Long id);
    Supplier saveSupplier(Supplier supplier);
    void deleteSupplier(Long id);
    List<Supplier> searchSuppliers(String keyword);
}
