package com.example.pharmacy.service;

import com.example.pharmacy.model.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> getAllCustomers();
    Customer getCustomerById(Long id);
    Customer saveCustomer(Customer customer);
    void deleteCustomer(Long id);
    List<Customer> searchCustomers(String keyword);
}
