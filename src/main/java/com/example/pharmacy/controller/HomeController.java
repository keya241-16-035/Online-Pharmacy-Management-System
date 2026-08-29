package com.example.pharmacy.controller;

import com.example.pharmacy.service.CustomerService;
import com.example.pharmacy.service.MedicineService;
import com.example.pharmacy.service.SalesService;
import com.example.pharmacy.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MedicineService medicineService;
    private final SupplierService supplierService;
    private final CustomerService customerService;
    private final SalesService salesService;

    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("totalMedicines", medicineService.countTotalMedicines());
        model.addAttribute("lowStockList", medicineService.getLowStockMedicines(10));
        model.addAttribute("expiringList", medicineService.getExpiringMedicines(30));
        model.addAttribute("totalSuppliers", supplierService.getAllSuppliers().size());
        model.addAttribute("totalCustomers", customerService.getAllCustomers().size());
        model.addAttribute("todaySalesAmount", salesService.getTodayTotalSalesAmount());
        model.addAttribute("recentSales", salesService.getRecentSales(5));
        
        return "index";
    }
}
