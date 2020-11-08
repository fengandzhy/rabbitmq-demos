package org.zhouhy.rabbitmq.direct;

import com.rabbitmq.client.*;
import org.zhouhy.rabbitmq.common.CommUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer4Direct2 {

    private static final String Exchange_Name="rabbit:mq03:exchange:e01";

    private static final String Queue_Name_02="rabbit:mq03:queue:q02";

    private static final String Routing_Key_02="rabbit:mq03:routing:key:r02";
    private static final String Routing_Key_03="rabbit:mq03:routing:key:r03";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = CommUtil.createConnection();
        Channel channel = connection.createChannel();

//        channel.exchangeDeclare(Exchange_Name,BuiltinExchangeType.DIRECT);
//        channel.queueDeclare(Queue_Name_02,true, false, false, null);
//        channel.queueBind(Queue_Name_02,Exchange_Name,Routing_Key_02);
//        channel.queueBind(Queue_Name_02, Exchange_Name, Routing_Key_03);

        Consumer consumer = CommUtil.createConsumer(channel);

        channel.basicConsume(Queue_Name_02, true, consumer);


    }
}
