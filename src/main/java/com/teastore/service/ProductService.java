package com.teastore.service;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.teastore.model.Product;



public interface ProductService {

	public Product addProduct(Product product);
	public Product getProductById(long id);
	public List<Product> getAllProducts();
	public void deleteProductById(long id);
	public void updateProduct(Product product);
	public List<Product> findProductsByName(String name);
	
	
}
