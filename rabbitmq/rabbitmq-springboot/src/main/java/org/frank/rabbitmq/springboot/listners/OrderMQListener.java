package org.frank.rabbitmq.springboot.listners;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RabbitListener(queues = {"${mq.config.queue}"})
public class OrderMQListener {

    @RabbitHandler
    public void messageHandler(String body, Message message, Channel channel) throws IOException {
        long msgTag = message.getMessageProperties().getDeliveryTag();
        System.out.println("msgTag="+msgTag);
        System.out.println("message="+message.toString());
        System.out.println("body="+body);

        //告诉broker，消息已经被确认
        channel.basicAck(msgTag,false);

        //告诉broker，消息拒绝确认 basicNack⽅法可以⽀持⼀次0个或多个消息的拒收，可以设置是否requeue。        
        //channel.basicNack(msgTag,false,true);

        // basicReject⼀次只能拒绝接收⼀个消息，可以设置是否 requeue。
        //channel.basicReject(msgTag,true);
    }
}
