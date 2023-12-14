package com.teastore.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.teastore.model.Role;
import com.teastore.model.Customer;
import com.teastore.repository.CustomerRepository;
import com.teastore.repository.RoleRepository;

@RestController
@RequestMapping("/customer/auth")
@CrossOrigin(origins = "http://localhost:4200/")
public class CustomerController {
	@Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @PostMapping("/signup")
    public ResponseEntity<?> customerSignup(@RequestBody Customer customer )
    {
        HashMap<String , String> response=new HashMap<>();
        if(customerRepository.existsByUsername(customer.getUsername()))
        {
            response.put("message", "Username is already exist!!");
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
        
        if(customerRepository.existsByEmail(customer.getEmail()))
        {
            response.put("message", "Email is already exist!!");
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
        
        
        Customer cust=new Customer();
        cust.setName(customer.getName());
        cust.setEmail(customer.getEmail());
        cust.setUsername(customer.getUsername());
        cust.setPassword(passwordEncoder.encode(customer.getPassword()));
        
        Optional<Role> role=roleRepository.findByName("ROLE_CUSTOMER");
        
        cust.setRoles(Collections.singleton(role.get()));
        
        customerRepository.save(cust);
        response.put("message","User Registered successfully");
        return new ResponseEntity<>(response,HttpStatus.OK);
        
    }
    
    @PostMapping("/signin")
    public ResponseEntity<?> registerCustomer(@RequestBody Customer customer)
    {
        Map<String, String> response=new HashMap<>();
        
        try
        {
            Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(customer.getEmail(),customer.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
                
            response.put("message", "User signed-in successfully!");
        
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch(Exception e)
        {
            response.put("message", "Problem in Signin!");
            
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    
}
	

