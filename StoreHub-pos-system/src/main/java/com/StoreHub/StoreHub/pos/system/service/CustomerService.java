package com.StoreHub.StoreHub.pos.system.service;

import com.StoreHub.StoreHub.pos.system.model.Customer;

import java.util.List;

public interface CustomerService {

    Customer createCustomer(Customer customer);
    Customer updateCustomer(Customer customer, Long id) throws  Exception;
    void  deleteCustomer(Long id) throws  Exception;
    Customer getCustomer(Long id) throws  Exception;
    List<Customer> getAllCustomer() throws Exception;
    List<Customer> searchCustomers(String keyword) throws Exception;
}
