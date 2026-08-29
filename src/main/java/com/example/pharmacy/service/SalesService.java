package com.example.pharmacy.service;

import com.example.pharmacy.model.Sale;

import java.math.BigDecimal;
import java.util.List;


public interface SalesService {
    List<Sale> getAllSales();
    Sale getSaleById(Long id);
    Sale createSale(Long customerId, List<Long> medicineIds, List<Integer> quantities);
    BigDecimal getTodayTotalSalesAmount();
    List<Sale> getRecentSales(int count);
}
