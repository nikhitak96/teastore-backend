package com.teastore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.teastore.model.Customer;



@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{

	 Optional<Customer> findByEmail(String email);
     Optional<Customer> findByUsername(String username);
     Optional<Customer> findByEmailOrUsername(String email,String Username);
     
     Boolean existsByUsername(String username);
     Boolean existsByEmail(String email);
}
