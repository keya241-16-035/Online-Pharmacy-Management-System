package com.example.pharmacy.config;

import com.example.pharmacy.model.Category;
import com.example.pharmacy.model.Customer;
import com.example.pharmacy.model.Medicine;
import com.example.pharmacy.model.Supplier;
import com.example.pharmacy.model.User;
import com.example.pharmacy.repository.*;
import com.example.pharmacy.service.SalesService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;
    private final CustomerRepository customerRepository;
    private final MedicineRepository medicineRepository;
    private final UserRepository userRepository;
    private final SalesService salesService;

    @Override
    @SuppressWarnings({"null","unused"})
    public void run(String... args) throws Exception {
        if (categoryRepository.count() == 0) {
            // 1. Seed Categories
            Category antibiotics = categoryRepository.save(Category.builder().name("Antibiotics").build());
            Category painkillers = categoryRepository.save(Category.builder().name("Painkillers").build());
            Category vitamins = categoryRepository.save(Category.builder().name("Vitamins & Supplements").build());
            Category antihistamines = categoryRepository.save(Category.builder().name("Antihistamines").build());

            // 2. Seed Suppliers
            Supplier pharmaCare = supplierRepository.save(Supplier.builder()
                    .name("PharmaCare Ltd")
                    .contactPerson("Dr. Robert Vance")
                    .phone("01711223344")
                    .email("contact@pharmacare.com")
                    .address("104 Tech Park, Boston")
                    .build());

            Supplier healthMed = supplierRepository.save(Supplier.builder()
                    .name("HealthMed Distribution")
                    .contactPerson("Sarah Jenkins")
                    .phone("01899887766")
                    .email("support@healthmed.com")
                    .address("55 Industrial Ave, New York")
                    .build());

            // 3. Seed Customers
            Customer customer1 = customerRepository.save(Customer.builder()
                    .name("John Doe")
                    .phone("01700001122")
                    .email("john.doe@example.com")
                    .address("123 Maple Street")
                    .build());

            Customer customer2 = customerRepository.save(Customer.builder()
                    .name("Alice Smith")
                    .phone("01800003344")
                    .email("alice.smith@example.com")
                    .address("456 Oak Avenue")
                    .build());

            // 4. Seed Medicines
            Medicine paracetamol = medicineRepository.save(Medicine.builder()
                    .name("Paracetamol 500mg")
                    .description("Pain reliever and fever reducer")
                    .price(new BigDecimal("5.50"))
                    .quantity(150)
                    .expiryDate(LocalDate.now().plusMonths(18))
                    .category(painkillers)
                    .supplier(pharmaCare)
                    .build());

            Medicine amoxicillin = medicineRepository.save(Medicine.builder()
                    .name("Amoxicillin 250mg")
                    .description("Broad-spectrum antibiotic")
                    .price(new BigDecimal("12.00"))
                    .quantity(45)
                    .expiryDate(LocalDate.now().plusMonths(12))
                    .category(antibiotics)
                    .supplier(pharmaCare)
                    .build());

            Medicine vitaminC = medicineRepository.save(Medicine.builder()
                    .name("Vitamin C 1000mg")
                    .description("Immune support supplement")
                    .price(new BigDecimal("8.75"))
                    .quantity(8) // Low stock demo!
                    .expiryDate(LocalDate.now().plusDays(20)) // Expiring soon demo!
                    .category(vitamins)
                    .supplier(healthMed)
                    .build());

            Medicine cetirizine = medicineRepository.save(Medicine.builder()
                    .name("Cetirizine 10mg")
                    .description("Allergy relief medication")
                    .price(new BigDecimal("6.00"))
                    .quantity(90)
                    .expiryDate(LocalDate.now().plusMonths(24))
                    .category(antihistamines)
                    .supplier(healthMed)
                    .build());

            // 5. Seed Users
            userRepository.save(User.builder()
                    .username("admin")
                    .password("admin123")
                    .role("ADMIN")
                    .build());

            userRepository.save(User.builder()
                    .username("pharmacist")
                    .password("pharm123")
                    .role("PHARMACIST")
                    .build());

            // 6. Seed Sample Sale
            try {
                salesService.createSale(customer1.getId(), List.of(paracetamol.getId(), amoxicillin.getId()), List.of(2, 1));
            } catch (Exception ignored) {
            }
        }
    }
}
