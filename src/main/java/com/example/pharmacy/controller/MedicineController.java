package com.example.pharmacy.controller;

import com.example.pharmacy.model.Medicine;
import com.example.pharmacy.service.CategoryService;
import com.example.pharmacy.service.MedicineService;
import com.example.pharmacy.service.SupplierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/medicine")
@RequiredArgsConstructor
public class MedicineController {

    private final MedicineService medicineService;
    private final CategoryService categoryService;
    private final SupplierService supplierService;

    @GetMapping
    public String listMedicines(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        model.addAttribute("medicines", medicineService.searchMedicines(keyword));
        model.addAttribute("keyword", keyword);
        return "medicine/list";
    }

    @GetMapping("/search")
    public String searchMedicineRedirect(@RequestParam(value = "keyword", required = false) String keyword) {
        return "redirect:/medicine" + (keyword != null ? "?keyword=" + keyword : "");
    }

    @GetMapping("/new")
    public String showMedicineForm(Model model) {
        model.addAttribute("medicine", new Medicine());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("suppliers", supplierService.getAllSuppliers());
        return "medicine/form";
    }

    @PostMapping("/save")
    public String saveMedicine(@Valid @ModelAttribute("medicine") Medicine medicine,
                               BindingResult bindingResult,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            model.addAttribute("suppliers", supplierService.getAllSuppliers());
            return "medicine/form";
        }
        medicineService.saveMedicine(medicine);
        redirectAttributes.addFlashAttribute("successMessage", "Medicine saved successfully!");
        return "redirect:/medicine";
    }

    @GetMapping("/edit/{id}")
    public String editMedicine(@PathVariable("id") Long id, Model model) {
        model.addAttribute("medicine", medicineService.getMedicineById(id));
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("suppliers", supplierService.getAllSuppliers());
        return "medicine/form";
    }

    @GetMapping("/view/{id}")
    public String viewMedicine(@PathVariable("id") Long id, Model model) {
        model.addAttribute("medicine", medicineService.getMedicineById(id));
        return "medicine/view";
    }

    @GetMapping("/delete/{id}")
    public String deleteMedicine(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        medicineService.deleteMedicine(id);
        redirectAttributes.addFlashAttribute("successMessage", "Medicine deleted successfully!");
        return "redirect:/medicine";
    }
}
