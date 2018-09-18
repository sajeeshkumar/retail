package net.sajeesh.retail.checkout.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;


@Component
@EnableBinding(Source.class)
public class MessageSender {

    @Autowired
    public MessageChannel output;

    public void send(Message<?> m) {
        try {
            // avoid too much magic and transform ourselves
            ObjectMapper mapper = new ObjectMapper();
            String jsonMessage = mapper.writeValueAsString(m);
            // wrap into a proper message for the transport (Kafka/Rabbit) and send it
            output.send(
                    MessageBuilder.withPayload(jsonMessage).setHeader("messageType", m.getMessageType()).build());
        } catch (Exception e) {
            throw new RuntimeException("Could not tranform and send message due to: "+ e.getMessage(), e);
        }
    }
}
