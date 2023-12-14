package com.teastore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.teastore.model.Product;

@Repository 
public interface ProductRepository extends JpaRepository<Product, Long> {

	//finder method to generate query
    public List<Product> findByName(String name);
}
