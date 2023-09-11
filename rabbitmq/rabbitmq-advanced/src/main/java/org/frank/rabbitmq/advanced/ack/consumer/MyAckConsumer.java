package org.frank.rabbitmq.advanced.ack.consumer;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

public class MyAckConsumer extends DefaultConsumer {

    private Channel channel;
    
    /**
     * Constructs a new instance and records its association to the passed-in channel.
     *
     * @param channel the channel to which this consumer is attached
     */
    public MyAckConsumer(Channel channel) {
        super(channel);
        this.channel = channel;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

        System.out.println("收到消息："+new String(body));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        if((Integer)properties.getHeaders().get("num")==0){
            channel.basicNack(envelope.getDeliveryTag(),false,true); //这里的true表示要重回队列
        }else{
            channel.basicAck(envelope.getDeliveryTag(),false); //这里的false表示不批量签收
        }

        
    }
}
