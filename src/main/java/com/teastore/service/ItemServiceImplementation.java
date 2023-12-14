package com.teastore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teastore.model.Item;
import com.teastore.repository.ItemRepository;


@Service
public class ItemServiceImplementation implements ItemService{

	@Autowired
	private ItemRepository itemRepository;
	
	@Override
	public Item addItem(Item item) {
		return itemRepository.save(item);
	}

	@Override
	public Item getItemById(long id) {
		return itemRepository.findById(id).get();
	}

	@Override
	public List<Item> getAllItems() {
		return itemRepository.findAll();
	}

	@Override
	public void deleteItemById(long id) {
		itemRepository.deleteById(id);
		
	}

	@Override
	public void updateItem(Item item) {
		itemRepository.save(item);
		
	}

	@Override
	public List<Item> findItemsByName(String name) {
		 return itemRepository.findByName(name);
	}

}
