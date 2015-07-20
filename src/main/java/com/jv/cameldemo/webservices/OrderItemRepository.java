package com.jv.cameldemo.webservices;

import java.util.HashMap;
import java.util.Map;

import com.jv.cameldemo.domain.OrderItem;

public class OrderItemRepository {
	
	private static final Map<Long, OrderItem> database = new HashMap<Long, OrderItem>();
	static {
		
		database.put(1L, new OrderItem(1, "engine", 5, 1000d));
		database.put(2L, new OrderItem(2, "clutch", 5, 250d));
		database.put(3L, new OrderItem(3, "wheel", 20, 100d));
		database.put(4L, new OrderItem(4, "hood", 10, 100d));
		database.put(5L, new OrderItem(5, "door", 20, 60d));
	}
	
	public OrderItem findById(long id) { 
		return database.get(id);
	}

}
