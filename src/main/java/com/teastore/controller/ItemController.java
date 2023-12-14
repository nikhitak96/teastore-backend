package com.teastore.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.teastore.model.Item;
import com.teastore.model.Product;
import com.teastore.repository.ItemRepository;
import com.teastore.service.ItemService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
public class ItemController {

		@Autowired
		private ItemRepository itemRepository;
		
		@Autowired
		private ItemService itemservice;
	
		@PostMapping("/item")
		public Item saveItemRecord(@Valid @RequestBody Item item) //request handler method
		{
			return this.itemservice.addItem(item);
		}
		
		
		@RequestMapping(path="/item/{id}",method = RequestMethod.GET)
	    //@GetMapping("/item/{id}")
	    public ResponseEntity<?> getItem(@PathVariable Long id)
	    {
	        if(this.itemRepository.findById(id).isPresent())
	        {
	            return new ResponseEntity(this.itemservice.getItemById(id),HttpStatus.OK);
	        }
	        else
	        {
	            return new ResponseEntity("Item Id not found!!",HttpStatus.NOT_FOUND);
	        }
	    }
		
		
		@GetMapping("/items")
		public List<Item> getItems()
		{
			return this.itemservice.getAllItems();
		}
	
		
		@DeleteMapping(path = "/item/{id}")
	    public ResponseEntity<HashMap> deleteItem(@PathVariable("id") Long itmid)   
	    {
	        HashMap<String, String> response=new HashMap<>(); 
	        
	        if(itemRepository.findById(itmid).isPresent())
	        {
	            itemservice.deleteItemById(itmid);
	            response.put("message", "Record deleted successfully!!");
	            return new ResponseEntity<HashMap>(response, HttpStatus.OK);
	        }
	        else
	        {
	            response.put("message", "Invalid Item id!!");
	            return new ResponseEntity<HashMap>(response, HttpStatus.NOT_FOUND);
	        }
	    }
		
		
		@PutMapping("/item/{id}")
	    public ResponseEntity<HashMap> updateItemById(@Valid @RequestBody Item item,@PathVariable("id")long id)
	    {
	        
	        HashMap<String , String> response=new HashMap<>();
	        if(itemRepository.findById(id).isPresent())
	        {
	            Item existingItm=itemRepository.findById(id).get();
	            
	            if(item.getName()!=null)
	            {
	                existingItm.setName(item.getName());
	            }
	            
	            if(item.getQuantity()!=0)
	            {
	                existingItm.setQuantity(item.getQuantity());
	            }
	             if(item.getPrice()!=0.0)
	            {
	                existingItm.setPrice(item.getPrice());
	            }
	            
	            itemservice.updateItem(existingItm);
	            
	            response.put("message", "Item information updated!");
	            return new ResponseEntity<HashMap>(response, HttpStatus.OK);
	            
	        }
	        else
	        {
	            response.put("message", "Invalid item id!");
	            return new ResponseEntity<HashMap>(response, HttpStatus.NOT_FOUND);
	        }
	        
	    }

		
		@PatchMapping("/item/{id}")
	    public ResponseEntity<HashMap> updateItem(@RequestBody Item item,@PathVariable("id")long id)
	    {
	        
	        HashMap<String , String> response=new HashMap<>();
	        if(itemRepository.findById(id).isPresent())
	        {
	            Item existingItm=itemRepository.findById(id).get();
	            
	            if(item.getName()!=null)
	            {
	                existingItm.setName(item.getName());
	            }
	             if(item.getQuantity()!=0)
	            {
	                existingItm.setQuantity(item.getQuantity());
	            }
	             if(item.getPrice()!=0.0)
	            {
	                existingItm.setPrice(item.getPrice());
	            }
	            
	            itemservice.updateItem(existingItm);
	            
	            response.put("message", "Item information updated!");
	            return new ResponseEntity<HashMap>(response, HttpStatus.OK);
	            
	        }
	        else
	        {
	            response.put("message", "Invalid item id!");
	            return new ResponseEntity<HashMap>(response, HttpStatus.NOT_FOUND);
	        }

	    }
		

        @GetMapping("/items/name")
        public ResponseEntity<List<Item>> getItemsByName(@RequestParam("name") String itmName)
        {
          return new ResponseEntity<List<Item>>(itemservice.findItemsByName(itmName),HttpStatus.OK);    
        }
		
		
}
