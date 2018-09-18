package net.sajeesh.retail.order.message;

import net.sajeesh.retail.order.domain.Order;
import net.sajeesh.retail.order.persistence.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@EnableBinding(Sink.class)
public class MessageListener {

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    @StreamListener(target = Sink.INPUT, condition="(headers['messageType']?:'')=='OrderPlacedEvent'")
    public void receiveMessage(Message<Order> message){

        Order order = message.getPayload();

        System.out.println("New order placed, start flow. " + order);

        orderRepository.save(order);

    }
}
