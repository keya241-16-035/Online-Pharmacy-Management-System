package com.example.pharmacy.controller;

import com.example.pharmacy.model.Sale;
import com.example.pharmacy.service.CustomerService;
import com.example.pharmacy.service.MedicineService;
import com.example.pharmacy.service.SalesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/sales")
@RequiredArgsConstructor
public class SalesController {

    private final SalesService salesService;
    private final CustomerService customerService;
    private final MedicineService medicineService;

    @GetMapping
    public String listSales(Model model) {
        model.addAttribute("sales", salesService.getAllSales());
        return "sales/list";
    }

    @GetMapping("/new")
    public String showSaleForm(Model model) {
        model.addAttribute("customers", customerService.getAllCustomers());
        model.addAttribute("medicines", medicineService.getAllMedicines());
        return "sales/form";
    }

    @PostMapping("/save")
    public String processSale(@RequestParam(value = "customerId", required = false) Long customerId,
                              @RequestParam("medicineId") List<Long> medicineIds,
                              @RequestParam("quantity") List<Integer> quantities,
                              RedirectAttributes redirectAttributes) {
        try {
            Sale sale = salesService.createSale(customerId, medicineIds, quantities);
            redirectAttributes.addFlashAttribute("successMessage", "Sale processed successfully!");
            return "redirect:/sales/view/" + sale.getId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/sales/new";
        }
    }

    @GetMapping("/view/{id}")
    public String viewSaleInvoice(@PathVariable("id") Long id, Model model) {
        model.addAttribute("sale", salesService.getSaleById(id));
        return "sales/view";
    }
}
