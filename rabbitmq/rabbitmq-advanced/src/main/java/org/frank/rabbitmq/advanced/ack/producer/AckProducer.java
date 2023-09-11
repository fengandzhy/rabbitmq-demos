package org.frank.rabbitmq.advanced.ack.producer;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.frank.rabbitmq.advanced.common.CommonUtil;
import org.frank.rabbitmq.advanced.common.Constant;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class AckProducer {

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = CommonUtil.createConnection();
        Channel channel = connection.createChannel();
        
        
        for(int i=0;i<10;i++){
            String message = "ack机制-publish message01  " + i;
            Map<String,Object> headers = new HashMap<>();
            headers.put("num",i);
            AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                                                .deliveryMode(2) // deliveryMode=2持久化消息：
                                                .contentEncoding("UTF-8")
                                                .headers(headers)
                                                .build();
            channel.basicPublish(Constant.ACK_EXCHANGE_NAME_01,
                    "mq:ack:routine:k.01",properties,message.getBytes());
        }        

        System.out.println("生产者发送消息成功---> ");

//        CommonUtil.close(channel,connection);
        
    }
}
