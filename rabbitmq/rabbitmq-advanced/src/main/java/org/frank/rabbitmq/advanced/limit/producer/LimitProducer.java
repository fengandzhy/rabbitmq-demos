package org.frank.rabbitmq.advanced.limit.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.frank.rabbitmq.advanced.common.CommonUtil;
import org.frank.rabbitmq.advanced.common.Constant;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 关于这个限流, 关键有两个步骤, 第一, 手动签收 channel.basicConsume(Constant.LIMIT_QUEUE_NAME_01, false, CommonUtil.createConsumer(channel)); 这里的 autoAck 一定要false
 * 第二 加入 channel.basicQos(0,1,false); 一次性只消费一个消息, 消费完了再拿. 
 * 
 * */
public class LimitProducer {

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = CommonUtil.createConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(Constant.LIMIT_EXCHANGE_NAME_01, BuiltinExchangeType.FANOUT);
        channel.queueDeclare(Constant.LIMIT_QUEUE_NAME_01, true, false, false, null);
        channel.queueBind(Constant.LIMIT_QUEUE_NAME_01, Constant.LIMIT_EXCHANGE_NAME_01, "");

        for (int i = 0; i < 100; i++) {
            String message = "fanoutExchange-publish message " + i;
            channel.basicPublish(Constant.LIMIT_EXCHANGE_NAME_01, "", null, message.getBytes());
        }
        System.out.println("生产者发送消息成功---> ");

//        CommonUtil.close(channel,connection);

    }
}
