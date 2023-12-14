package com.teastore.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
//import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
//import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
//import lombok.Setter;
//import lombok.ToString;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Data
@Builder
public class Product {

	 	@Id
	    @GeneratedValue(strategy = GenerationType.AUTO) //primary key generation strategy
	    private long id;
	    
	    @Column(nullable = false)
	    @NotEmpty
	    @Size(min = 2, message = "Product name should have atleast 2 characters")
	    private String name;
	    
	    
	    @Column(nullable = false)
	    @NotEmpty(message ="Description can not be empty" )
	    @Size(min = 2, message = "Product description should have atleast 2 characters")
	    private String description;
	    
	    
	    @Min(value = 1, message = "quantity must be atleast 1" )
	   // @NotEmpty(message ="quantity can not be empty" )
	    private double quantity ;
	    
	    
	    @Min(value = 100, message = "price must be atleast 100" )
	    @Max(value = 20000, message = "price should not be greater than 20000" )
	    private double price;

//images
	    
		private String url;
		        

}
