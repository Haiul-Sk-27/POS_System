package com.StoreHub.StoreHub.pos.system.repository;

import com.StoreHub.StoreHub.pos.system.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    List<Customer> findByFullNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String fullName,String email);

}
