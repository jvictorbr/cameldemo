package com.jv.cameldemo.domain;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
	
	private Date dateReceived;
	private long id;
	private long originId;
	private List<OrderItem> items = new ArrayList<OrderItem>();
	
	public Order() {
		this.dateReceived = new Date();
		this.id = new SecureRandom().nextLong();
	}

	public Date getDateReceived() {
		return dateReceived;
	}
	public void setDateReceived(Date dateReceived) {
		this.dateReceived = dateReceived;
	}
	public long getOriginId() {
		return originId;
	}
	public void setOriginId(long originId) {
		this.originId = originId;
	}
	public void addItem(OrderItem orderItem) { 
		this.items.add(orderItem);
	}
	public List<OrderItem> getItems() {
		return items;
	}
	public void setItems(List<OrderItem> items) {
		this.items = items;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}	
	
	@Override
	public String toString() { 
		return String.format("{id: %s, originId: %s, dateReceived: %s}", getId(), getOriginId(), getDateReceived());
	}

}
