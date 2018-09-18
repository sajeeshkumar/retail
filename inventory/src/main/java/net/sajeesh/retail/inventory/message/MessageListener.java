package net.sajeesh.retail.inventory.message;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@EnableBinding(Sink.class)
public class MessageListener {

    @Transactional
    @StreamListener(target = Sink.INPUT, condition="(headers['messageType']?:'')=='FetchGoodsCommand'")
    public void receiveMessage(String json){

    }
}
