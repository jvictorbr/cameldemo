package com.jv.cameldemo.webservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jv.cameldemo.domain.Order;
import com.jv.cameldemo.domain.OrderItem;

@RestController
@EnableAutoConfiguration
public class OrderRestWebService {
	
	@RequestMapping(value="/hello", method=RequestMethod.GET)
	public ResponseEntity<String> hello() { 
		
		return new ResponseEntity<String>("Hello World", HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/process", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> process(@RequestBody Order order) { 
		System.out.println(String.format("Received new order from origin# %s with %s items", order.getOriginId(), order.getItems().size()));
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/item", method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<OrderItem> getStockAndPrice(@RequestParam(value="itemId") long itemId) {
		
		OrderItemRepository repository =  new OrderItemRepository();
		OrderItem item = repository.findById(itemId);
		
		return new ResponseEntity<OrderItem>(item, HttpStatus.OK);
		
		
	}
	
	public static void main(String[] args) {
		SpringApplication.run(OrderRestWebService.class, args);
	}

}
