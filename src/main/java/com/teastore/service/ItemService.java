package com.teastore.service;

import java.util.List;

import com.teastore.model.Item;



public interface ItemService {

	public Item addItem(Item item);
	public Item getItemById(long id);
	public List<Item> getAllItems();
	public void deleteItemById(long id);
	public void updateItem(Item item);
	public List<Item> findItemsByName(String name);
}
