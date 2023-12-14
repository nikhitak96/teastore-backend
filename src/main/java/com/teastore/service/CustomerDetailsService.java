package com.teastore.service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.teastore.model.Customer;
import com.teastore.model.Role;
import com.teastore.repository.CustomerRepository;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


@Service
public class CustomerDetailsService implements UserDetailsService{

	 @Autowired
	    private CustomerRepository customerRepository;
	    
	    
	    @Override
	    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
	        
	        try {
	            
	            Customer customer=customerRepository.findByEmailOrUsername( usernameOrEmail, usernameOrEmail)
	                    .orElseThrow(() ->new Exception("Username or email not found!!"));
	            
	        return new User(customer.getEmail(),customer.getPassword(),mapRolesToAuthorities(customer.getRoles()));
	        
	        } catch (Exception e) {
	            
	        }
	        
	        return null;
	    }
	    
	    
	    public Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles)
	    {
	        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList()); 
	    }

	}

