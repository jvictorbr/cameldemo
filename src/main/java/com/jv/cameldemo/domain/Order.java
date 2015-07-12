package com.jv.cameldemo.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Order {
	
	private Date dateReceived;
	private long originId;
	private List<OrderItem> items = new ArrayList<OrderItem>();
	
	public Order() {
		this.dateReceived = new Date();
	}
	
	//@JsonFormat(pattern="yyyyMMdd hh:mm:sss")
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

}
