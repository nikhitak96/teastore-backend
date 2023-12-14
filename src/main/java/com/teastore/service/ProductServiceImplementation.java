package com.teastore.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.teastore.model.Product;
import com.teastore.repository.ProductRepository;

@Service
public class ProductServiceImplementation implements ProductService{

	@Autowired
	private ProductRepository productRepository;
	

	
	@Override
	public Product addProduct(Product product) {
		
		return productRepository.save(product);
	}

	@Override
	public Product getProductById(long id) {
		
		return productRepository.findById(id).get();
	}

	@Override
	public List<Product> getAllProducts() {
		
		return productRepository.findAll();
	}

	@Override
	public void deleteProductById(long id) {
		productRepository.deleteById(id);
		
	}

	@Override
	public void updateProduct(Product product) {
		
		productRepository.save(product);
		
	}

	@Override
    public List<Product> findProductsByName(String name) {
        
        return productRepository.findByName(name);
    }
	
	
	
	

    

	
}
