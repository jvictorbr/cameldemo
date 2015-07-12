# cameldemo

Steps:

Setup
-----

1) clone git repository
2) mvn eclipse:clean eclipse:eclipse
3) have active mq up and running
4) run OrderRestWebService class to boot up the Order webservice
5) run CamelDemo class to start camel routes


Tests
-----

1) put files on orders/inbox folder to be consumed
2) put csv strings on 'orders.queue' to be consumed
3) put xml strings on 'orders.queue' to be consumed

Both csv and xml samples can be found on sample folder 
