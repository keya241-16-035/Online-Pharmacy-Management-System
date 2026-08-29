package com.example.pharmacy.service.impl;

import com.example.pharmacy.exception.InsufficientStockException;
import com.example.pharmacy.exception.ResourceNotFoundException;
import com.example.pharmacy.model.Customer;
import com.example.pharmacy.model.Medicine;
import com.example.pharmacy.model.Sale;
import com.example.pharmacy.model.SaleItem;
import com.example.pharmacy.repository.CustomerRepository;
import com.example.pharmacy.repository.MedicineRepository;
import com.example.pharmacy.repository.SaleRepository;
import com.example.pharmacy.service.SalesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SalesServiceImpl implements SalesService {

    private final SaleRepository saleRepository;
    private final MedicineRepository medicineRepository;
    private final CustomerRepository customerRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Sale> getAllSales() {
        return saleRepository.findAllByOrderBySaleDateDesc();
    }

    @Override
    @Transactional(readOnly = true)
    public Sale getSaleById(Long id) {
        return saleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sale record not found with id: " + id));
    }

    @Override
    public Sale createSale(Long customerId, List<Long> medicineIds, List<Integer> quantities) {
        if (medicineIds == null || medicineIds.isEmpty() || quantities == null || quantities.size() != medicineIds.size()) {
            throw new IllegalArgumentException("Invalid sale item details provided.");
        }

        Customer customer = null;
        if (customerId != null) {
            customer = customerRepository.findById(customerId)
                    .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + customerId));
        }

        Sale sale = Sale.builder()
                .customer(customer)
                .saleDate(LocalDateTime.now())
                .totalAmount(BigDecimal.ZERO)
                .items(new ArrayList<>())
                .build();

        BigDecimal grandTotal = BigDecimal.ZERO;

        for (int i = 0; i < medicineIds.size(); i++) {
            Long medId = medicineIds.get(i);
            Integer qty = quantities.get(i);

            if (medId == null || qty == null || qty <= 0) {
                continue;
            }

            Medicine medicine = medicineRepository.findById(medId)
                    .orElseThrow(() -> new ResourceNotFoundException("Medicine not found with id: " + medId));

            // Stock Check
            if (medicine.getQuantity() < qty) {
                throw new InsufficientStockException("Insufficient stock for medicine '" + medicine.getName() +
                        "'. Requested: " + qty + ", Available: " + medicine.getQuantity());
            }

            // Deduct Stock
            medicine.setQuantity(medicine.getQuantity() - qty);
            medicineRepository.save(medicine);

            // Subtotal
            BigDecimal unitPrice = medicine.getPrice();
            BigDecimal subtotal = unitPrice.multiply(BigDecimal.valueOf(qty));
            grandTotal = grandTotal.add(subtotal);

            SaleItem item = SaleItem.builder()
                    .medicine(medicine)
                    .quantity(qty)
                    .unitPrice(unitPrice)
                    .subtotal(subtotal)
                    .build();

            sale.addItem(item);
        }

        if (sale.getItems().isEmpty()) {
            throw new IllegalArgumentException("Sale must contain at least one valid medicine item.");
        }

        sale.setTotalAmount(grandTotal);
        return saleRepository.save(sale);
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal getTodayTotalSalesAmount() {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);
        return saleRepository.calculateTotalSalesForPeriod(startOfDay, endOfDay);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Sale> getRecentSales(int count) {
        List<Sale> allSales = getAllSales();
        return allSales.stream().limit(count).toList();
    }
}
