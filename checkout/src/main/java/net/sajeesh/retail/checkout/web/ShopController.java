package net.sajeesh.retail.checkout.web;

import net.sajeesh.retail.checkout.domain.Customer;
import net.sajeesh.retail.checkout.domain.Order;
import net.sajeesh.retail.checkout.message.Message;
import net.sajeesh.retail.checkout.message.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShopController {

    @Autowired
    private MessageSender messageSender;

    @RequestMapping(path = "/api/cart/order", method = RequestMethod.PUT)
    public String placeOrder(@RequestParam(value = "customerId") String customerId){
        Order order = new Order();
        order.addItem("article1", 5);
        order.addItem("article2", 10);

        order.setCustomer(new Customer("Camunda", "Zossener Strasse 55\n10961 Berlin\nGermany"));

        Message<Order> message = new Message<Order>("OrderPlacedEvent", order);
        messageSender.send(message);

        // note that we cannot easily return an order id here - as everything is asynchronous
        // and blocking the client is not what we want.
        // but we return an own correlationId which can be used in the UI to show status maybe later
        return "{\"traceId\": \"" + message.getTraceId() + "\"}";
    }
}
