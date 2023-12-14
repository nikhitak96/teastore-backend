package com.teastore;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.teastore.service.FilesStorageService;

import jakarta.annotation.Resource;

@SpringBootApplication
public class TeaStoreBackendApplication implements CommandLineRunner {

	@Resource
	  FilesStorageService   storageService;
	
	public static void main(String[] args) {
		SpringApplication.run(TeaStoreBackendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		 //storageService.deleteAll();
		    storageService.init();
		
	}

}
