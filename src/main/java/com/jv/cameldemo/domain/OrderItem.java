package com.jv.cameldemo.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class OrderItem {

	private long id;
	private String name;
	private int quantity;
	private int stock;
	private double price;
	
	public OrderItem() {

	}

	public OrderItem(long id, String name, int stock, double price) {
		this.id = id;
		this.name = name;
		this.stock = stock;
		this.price = price;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() { 
		return String.format("{id: %s, name: %s, quantity: %s, stock: %s, price: %s}", getId(), getName(), getQuantity(), getStock(), getPrice());
	}

}
