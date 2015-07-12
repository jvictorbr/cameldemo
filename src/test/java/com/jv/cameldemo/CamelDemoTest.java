package com.jv.cameldemo;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Before;
import org.junit.Test;

import com.jv.cameldemo.camel.CamelDemo;

public class CamelDemoTest extends CamelTestSupport {
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
		context.addComponent("amq", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
		context.addRoutes(new CamelDemo.CamelDemoRouteBuilder());
	}
	
	@Test
	public void testSendCsv() {
		
		String csv = "1020304005031\r\n" +
					 "00001,engine,1\r\n" +
					 "00002,clutch,1\r\n" +
					 "00003,wheel,4";
		
		template.sendBody("amq:queue:orders.queue", csv);
		
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> "+
					 "<order>\r\n"+
						 "<originId>14389753</originId>\r\n"+
						 "<items>\r\n"+
						 	"<order-item>\r\n"+
						 		"<id>1</id>\r\n"+
						 		"<name>engine</name>\r\n"+
						 		"<quantity>1</quantity>\r\n"+
						 	"</order-item>\r\n"+
						 	"<order-item>\r\n"+
						 		"<id>2</id>\r\n"+
						 		"<name>clutch</name>\r\n"+
						 		"<quantity>1</quantity>\r\n"+
						 	"</order-item>\r\n"+
						 	"<order-item>\r\n"+
						 		"<id>3</id>\r\n"+
						 		"<name>wheel</name>\r\n"+
						 		"<quantity>4</quantity>\r\n"+
						 	"</order-item>\r\n"+
						 "</items>\r\n"+
					 "</order>";
		
		template.sendBody("amq:queue:orders.queue", xml);		
	}

}
