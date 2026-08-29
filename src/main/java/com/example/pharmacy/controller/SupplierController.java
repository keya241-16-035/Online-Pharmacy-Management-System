package com.example.pharmacy.controller;

import com.example.pharmacy.model.Supplier;
import com.example.pharmacy.service.SupplierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/supplier")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;

    @GetMapping
    public String listSuppliers(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        model.addAttribute("suppliers", supplierService.searchSuppliers(keyword));
        model.addAttribute("keyword", keyword);
        return "supplier/list";
    }

    @GetMapping("/new")
    public String showSupplierForm(Model model) {
        model.addAttribute("supplier", new Supplier());
        return "supplier/form";
    }

    @PostMapping("/save")
    public String saveSupplier(@Valid @ModelAttribute("supplier") Supplier supplier,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "supplier/form";
        }
        supplierService.saveSupplier(supplier);
        redirectAttributes.addFlashAttribute("successMessage", "Supplier saved successfully!");
        return "redirect:/supplier";
    }

    @GetMapping("/edit/{id}")
    public String editSupplier(@PathVariable("id") Long id, Model model) {
        model.addAttribute("supplier", supplierService.getSupplierById(id));
        return "supplier/form";
    }

    @GetMapping("/delete/{id}")
    public String deleteSupplier(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        supplierService.deleteSupplier(id);
        redirectAttributes.addFlashAttribute("successMessage", "Supplier deleted successfully!");
        return "redirect:/supplier";
    }
}
