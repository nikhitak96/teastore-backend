package com.teastore.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.teastore.message.ResponseMessage;
import com.teastore.model.FileInfo;
import com.teastore.model.Product;
import com.teastore.repository.ProductRepository;
import com.teastore.service.FilesStorageService;
import com.teastore.service.ProductService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
public class ProductController {

	@Autowired
	private FilesStorageService filestorageservice;

	@Autowired
	private ProductService productservice;

	@Autowired
	private ProductRepository productRepository;
	

	  @PostMapping("/product")
	  public ResponseEntity<ResponseMessage> addProduct(@RequestParam("name") String name,@RequestParam("description") String description,@RequestParam("quantity") double quantity,@RequestParam("price") double price,@RequestParam("image") MultipartFile image) {
	    String message = "";
	    Product product=new Product();
	    product.setName(name);
	    product.setDescription(description);
	    product.setQuantity(quantity);
	    product.setPrice(price);
	    product.setUrl(image.getOriginalFilename());
	    try {
	      this.productRepository.save(product);	
	      filestorageservice.save(image);

	      message = "Product Added: " + image.getOriginalFilename();
	      return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
	    } catch (Exception e) {
	      message = "Could not upload the file: " + image.getOriginalFilename() + ". Error: " + e.getMessage();
	      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
	    }
	  }
	  
	  @GetMapping("/products")
	  public ResponseEntity<List<Product>>  getAllProducts() {
	    
		  List<Product> products=this.productRepository.findAll();
		  
		  List<FileInfo> fileInfos = filestorageservice.loadAll().map(path -> {
		      String filename = path.getFileName().toString();
		      String url = MvcUriComponentsBuilder
		          .fromMethodName(ProductController.class, "getFile", path.getFileName().toString()).build().toString();

		      return new FileInfo(filename, url);
		    }).collect(Collectors.toList());
		  
		for(int i=0;i<products.size();i++)
		{
			products.get(i).setUrl(fileInfos.get(i).getUrl());
		}
		  
		    return ResponseEntity.status(HttpStatus.OK).body(products);
	  }
	  
	  @GetMapping("/product/{id}")
	  public ResponseEntity<Product>  getProduct(@PathVariable long id ) {
	    
		  List<Product> products=Arrays.asList(this.productRepository.findById(id).get());
		  
		  List<FileInfo> fileInfos = filestorageservice.loadAll().map(path -> {
		      String filename = path.getFileName().toString();
		      String url = MvcUriComponentsBuilder
		          .fromMethodName(ProductController.class, "getFile", path.getFileName().toString()).build().toString();

		      return new FileInfo(filename, url);
		    }).collect(Collectors.toList());
		  
			fileInfos=fileInfos.stream().filter(f->f.getName().equals(products.get(0).getUrl())).collect(Collectors.toList());

		  
		  for(int i=0;i<products.size();i++)
			{
			  
				products.get(i).setUrl(fileInfos.get(i).getUrl());
			}
		  

		    return ResponseEntity.status(HttpStatus.OK).body(products.get(0));
	  }

	// @RequestMapping(path="/product", method = RequestMethod.POST)
	// //localhost:9090/product
	/*
	 * @PostMapping("/product") public Product saveProductRecord(@Valid @RequestBody
	 * Product product) // request handler method { return
	 * this.productservice.addProduct(product); }
	 */

	// URI parameter: id /product/123
	/*
	 * @RequestMapping(path = "/product/{id}", method = RequestMethod.GET)
	 * // @GetMapping("/product/{id}") public ResponseEntity<?>
	 * getProduct(@PathVariable Long id) { if
	 * (this.productRepository.findById(id).isPresent()) { return new
	 * ResponseEntity(this.productservice.getProductById(id), HttpStatus.OK); } else
	 * { return new ResponseEntity("Product Id not found!!", HttpStatus.NOT_FOUND);
	 * } }
	 * 
	 * @GetMapping("/products") public List<Product> getProducts() { return
	 * this.productservice.getAllProducts(); }
	 */

	@DeleteMapping(path = "/product/{id}")
	public ResponseEntity<HashMap> deleteProduct(@PathVariable("id") Long prdid) {
		HashMap<String, String> response = new HashMap<>();

		if (productRepository.findById(prdid).isPresent()) {
			productservice.deleteProductById(prdid);
			response.put("message", "Record deleted successfully!!");
			return new ResponseEntity<HashMap>(response, HttpStatus.OK);
		} else {
			response.put("message", "Invalid Product id!!");
			return new ResponseEntity<HashMap>(response, HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/product/{id}")
	public ResponseEntity<HashMap> updateProductById(@Valid @RequestBody Product product, @PathVariable("id") long id) {

		HashMap<String, String> response = new HashMap<>();
		if (productRepository.findById(id).isPresent()) {
			Product existingPrd = productRepository.findById(id).get();

			if (product.getName() != null) {
				existingPrd.setName(product.getName());
			}
			if (product.getDescription() != null) {
				existingPrd.setDescription(product.getDescription());
			}

			if (product.getQuantity() != 0) {
				existingPrd.setQuantity(product.getQuantity());
			}
			if (product.getPrice() != 0.0) {
				existingPrd.setPrice(product.getPrice());
			}

			productservice.updateProduct(existingPrd);

			response.put("message", "Product information updated!");
			return new ResponseEntity<HashMap>(response, HttpStatus.OK);

		} else {
			response.put("message", "Invalid product id!");
			return new ResponseEntity<HashMap>(response, HttpStatus.NOT_FOUND);
		}

	}

	@PatchMapping("/product/{id}")
	public ResponseEntity<HashMap> updateProduct(@RequestBody Product product, @PathVariable("id") long id) {

		HashMap<String, String> response = new HashMap<>();
		if (productRepository.findById(id).isPresent()) {
			Product existingPrd = productRepository.findById(id).get();

			if (product.getName() != null) {
				existingPrd.setName(product.getName());
			}
			if (product.getDescription() != null) {
				existingPrd.setDescription(product.getDescription());
			}

			if (product.getQuantity() != 0) {
				existingPrd.setQuantity(product.getQuantity());
			}
			if (product.getPrice() != 0.0) {
				existingPrd.setPrice(product.getPrice());
			}

			productservice.updateProduct(existingPrd);

			response.put("message", "Product information updated!");
			return new ResponseEntity<HashMap>(response, HttpStatus.OK);

		} else {
			response.put("message", "Invalid product id!");
			return new ResponseEntity<HashMap>(response, HttpStatus.NOT_FOUND);
		}

	}
	// product/123 - URI parameter
	// product?name=aman - Query/request parameter

	@GetMapping("/products/name")
	public ResponseEntity<List<Product>> getProductsByName(@RequestParam("name") String prdName) {
		return new ResponseEntity<List<Product>>(productservice.findProductsByName(prdName), HttpStatus.OK);
	}

	// for uploaded images

	/*
	 * @PostMapping("/product/upload/{id}") public ResponseEntity<?>
	 * uploadFile(@RequestParam("file") MultipartFile file, @PathVariable long id) {
	 * String message = ""; HashMap<String, String> response = new HashMap<>(); if
	 * (productRepository.findById(id).isPresent()) { Product existingPrd =
	 * productRepository.findById(id).get();
	 * 
	 * try { filestorageservice.save(file);
	 * existingPrd.setImageName(file.getOriginalFilename());
	 * existingPrd.setUrl(file.getOriginalFilename());
	 * this.productRepository.save(existingPrd);
	 * 
	 * message = "Uploaded the file successfully: " + file.getOriginalFilename();
	 * return ResponseEntity.status(HttpStatus.OK).body(new
	 * com.teastore.message.ResponseMessage(message)); } catch (Exception e) {
	 * message = "Could not upload the file: " + file.getOriginalFilename() +
	 * ". Error: " + e.getMessage(); return
	 * ResponseEntity.status(HttpStatus.EXPECTATION_FAILED) .body(new
	 * com.teastore.message.ResponseMessage(message)); } } else {
	 * 
	 * response.put("message", "Invalid product id!"); return new
	 * ResponseEntity<HashMap>(response, HttpStatus.NOT_FOUND);
	 * 
	 * } }
	 */

	/*
	 * @GetMapping("/products/file") public ResponseEntity<?>
	 * getFileByName(@RequestParam("file") String name) throws IOException {
	 * HashMap<String, String> response = new HashMap<>(); List<Product> products =
	 * filestorageservice.loadAll().map(path -> { String filename =
	 * path.getFileName().toString(); String url = MvcUriComponentsBuilder
	 * .fromMethodName(ProductController.class, "getFile",
	 * path.getFileName().toString()).build() .toString(); response.put("filename",
	 * filename); response.put("url", url); return new
	 * Product(0,null,null,0.0,0.0,filename, url); }).collect(Collectors.toList());
	 * 
	 * products = products.stream().filter(f ->
	 * f.getName().equals(name)).collect(Collectors.toList());
	 * 
	 * return ResponseEntity.status(HttpStatus.OK).body(products.get(0)); }
	 */
	@GetMapping("/files/{filename:.+}")
	public ResponseEntity<Resource> getFile(@PathVariable String filename) {
		Resource file = filestorageservice.load(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

	@DeleteMapping("/files/{filename:.+}")
	public ResponseEntity<ResponseMessage> deleteFile(@PathVariable String filename) {
		String message = "";

		try {
			boolean existed = filestorageservice.delete(filename);

			if (existed) {
				message = "Delete the file successfully: " + filename;
				return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
			}

			message = "The file does not exist!";
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage(message));
		} catch (Exception e) {
			message = "Could not delete the file: " + filename + ". Error: " + e.getMessage();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage(message));
		}
	}

}
