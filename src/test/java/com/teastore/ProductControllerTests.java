package com.teastore;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teastore.model.Product;
import com.teastore.service.ProductService;


import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;



@WebMvcTest
public class ProductControllerTests {

	 @Autowired
	    private MockMvc mockMvc;

	    @MockBean
	    private ProductService productService;

	    @Autowired
	    private ObjectMapper objectMapper;
	    
	    
	    @Test
	    public void givenProductObject_whenCreateProduct_thenReturnSavedProduct() throws Exception{

	        // given - precondition or setup
	        Product product = Product.builder()
	                .name("Black ginger tea")
	                .description("Good for health")
	                .quantity(1)
	                .price(798)
	                .build();
	        given(productService.addProduct(any(Product.class)))
	                .willAnswer((invocation)-> invocation.getArgument(0));

	        // when - action or behaviour that we are going test
	        ResultActions response = mockMvc.perform(post("http://localhost:4200/products")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(product)));

	        // then - verify the result or output using assert statements
	        response.andDo(print()).
	                andExpect(status().isCreated())
	                .andExpect(jsonPath("$.name",
	                        is(product.getName())))
	                .andExpect(jsonPath("$.description",
	                        is(product.getDescription())))
	                .andExpect(jsonPath("$.quantity",
	                        is(product.getQuantity())))
	        		.andExpect(jsonPath("$.price",
	        				is(product.getPrice())));
	    		}
	    
	    
	 // JUnit test for Get All products REST API
	    @Test
	    public void givenListOfProducts_whenGetAllProducts_thenReturnProductsList() throws Exception{
	        // given - precondition or setup
	        List<Product> listOfProducts = new ArrayList<>();
	        listOfProducts.add(Product.builder().name("Black ginger tea").description("Good for health").quantity(1).price(798).build());
	        listOfProducts.add(Product.builder().name("Rose tea").description("for skincare").quantity(1).price(689).build());
	        given(productService.getAllProducts()).willReturn(listOfProducts);

	        // when -  action or the behaviour that we are going test
	        ResultActions response = mockMvc.perform(get("http://localhost:4200/products"));

	        // then - verify the output
	        response.andExpect(status().isOk())
	                .andDo(print())
	                .andExpect(jsonPath("$.size()",
	                        is(listOfProducts.size())));

	    }
	    
	    
	 // JUnit test for delete product 
	    @Test
	    public void givenProductId_whenDeleteProduct_thenReturn200() throws Exception{
	        // given - precondition or setup
	        long productId = 1L;
	        willDoNothing().given(productService).deleteProductById(productId);

	        // when -  action or the behaviour that we are going test
	        ResultActions response = mockMvc.perform(delete("http://localhost:4200/product/{id}", productId));

	        // then - verify the output
	        response.andExpect(status().isOk())
	                .andDo(print());
	    }
	    
	 // positive scenario - valid product id
	    // JUnit test for GET product by id 
	    @Test
	    public void givenProductId_whenGetProductById_thenReturnProductObject() throws Exception{
	        // given - precondition or setup
	        long productId = 1L;
	        Product product = Product.builder()
	                .name("Black ginger tea")
	                .description("Good for health")
	                .quantity(1)
	                .price(798)
	                .build();
	        given(productService.getProductById(productId)).willReturn(product);

	        // when -  action or the behaviour that we are going test
	        ResultActions response = mockMvc.perform(get("http://localhost:4200/product/{id}", productId));

	        // then - verify the output
	        response.andExpect(status().isOk())
	                .andDo(print())
	                .andExpect(jsonPath("$.name", is(product.getName())))
	                .andExpect(jsonPath("$.description", is(product.getDescription())))
	                .andExpect(jsonPath("$.quantity", is(product.getQuantity())))
	                .andExpect(jsonPath("$.price", is(product.getPrice())));

	    }
	    
	    
}
