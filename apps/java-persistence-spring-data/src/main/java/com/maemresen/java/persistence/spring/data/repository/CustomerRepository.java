package com.maemresen.java.persistence.spring.data.repository;

import com.maemresen.java.persistence.spring.data.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
