package com.jv.cameldemo.camel;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.dataformat.xstream.XStreamDataFormat;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.spi.DataFormat;

import com.jv.cameldemo.domain.Order;
import com.jv.cameldemo.domain.OrderItem;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;



public class CamelDemo {
	
	public static void main(String[] args) throws Exception {
		
		CamelContext context = new DefaultCamelContext();
		
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
		context.addComponent("amq", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
		
		context.addRoutes(new CamelDemoRouteBuilder());
		
		context.start();
		
		Thread.sleep(1000 * 3600);
		
		context.stop();	
		
		
	}
	
	public static class CamelDemoRouteBuilder extends RouteBuilder {

		@Override
		public void configure() throws Exception {
			
			from("file:orders/inbox?noop=true")
				.tracing()
				.log("Received file:\r\nFileName: ${file:name}\r\nBody: ${body}")
				.choice()
					.when(simple("${file:ext} == 'dat'"))						
						.to("direct:processCsv")
					.otherwise()
						.log("Unexpected type of file received ${file:name}");
			
			from("amq:queue:orders.queue")
				.tracing()
				.log("Received Jms Message:\r\nId: ${id}\r\nBody: ${body}")
				.choice()
					.when(body().contains("?xml"))
						.to("direct:processXml")
					.otherwise()
						.to("direct:processCsv");				
			
			from("direct:processCsv")
				.tracing()
				.convertBodyTo(String.class)
				.unmarshal().csv()
				.process(new OrderCsvParserProcessor())
				.marshal().json(JsonLibrary.Jackson)
				.convertBodyTo(String.class)
				.log("Body is now: ${in.body}")
				.setHeader(Exchange.HTTP_METHOD, constant("POST"))
				.setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
				.to("http://localhost:8080/process");
			
			from("direct:processXml")
				.tracing()
				.convertBodyTo(String.class)
				.unmarshal(xstreamDataFormat())
				.process(new OrderXmlProcessor())
				.marshal().json(JsonLibrary.Jackson)
				.log("Body is now: ${in.body}")
				.setHeader(Exchange.HTTP_METHOD, constant("POST"))
				.setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
				.to("http://localhost:8080/process");
			
		}
		
	}
	
	public static class OrderCsvParserProcessor implements Processor {
		@SuppressWarnings("unchecked") @Override
		public void process(Exchange exchange) throws Exception {
			List<List<String>> input = (List<List<String>>) exchange.getIn().getBody();
			Order order = new OrderBuilder().buildFromCsv(input);							
			exchange.getIn().setBody(order);							
		}	
	}
	
	public static class OrderXmlProcessor implements Processor {
		@Override
		public void process(Exchange exchange) throws Exception {
			Order order = (Order) exchange.getIn().getBody();
			order.setDateReceived(new Date());
		} 		
	}
	
	private static DataFormat xstreamDataFormat() {
		XStream xstream = new XStream(new DomDriver());
		xstream.alias("order", Order.class);
		xstream.alias("order-item", OrderItem.class);
		return new XStreamDataFormat(xstream);
	}
	
	public static class OrderBuilder {
		public Order buildFromCsv(List<List<String>> csv) { 
			
			Iterator<List<String>> inputIt = csv.iterator();
			List<String> header = inputIt.next();
			long headerOriginId = Long.parseLong(header.get(0));
			
			Order order = new Order();
			order.setOriginId(headerOriginId);
			
			while (inputIt.hasNext()) { 
				List<String> inputOrderItem = inputIt.next();
				long orderItemId = Long.parseLong(inputOrderItem.get(0));
				String orderItemName = inputOrderItem.get(1);
				int quantity = Integer.parseInt(inputOrderItem.get(2));
				
				OrderItem item = new OrderItem();
				item.setId(orderItemId);
				item.setName(orderItemName);
				item.setQuantity(quantity);
				
				order.addItem(item);
			}
			
			return order;
		}
	}

}
