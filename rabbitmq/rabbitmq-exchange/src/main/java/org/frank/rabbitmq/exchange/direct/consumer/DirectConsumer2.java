package org.frank.rabbitmq.exchange.direct.consumer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.frank.rabbitmq.exchange.common.CommonUtil;
import org.frank.rabbitmq.exchange.common.Constant;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class DirectConsumer2 {

    @SuppressWarnings("DuplicatedCode")
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = CommonUtil.createConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(Constant.DIRECT_EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        channel.queueDeclare(Constant.DIRECT_QUEUE_NAME2, false, false, false, null);
        channel.queueBind(Constant.DIRECT_QUEUE_NAME2, Constant.DIRECT_EXCHANGE_NAME, Constant.INFO_ROUTING_KEY);
        channel.queueBind(Constant.DIRECT_QUEUE_NAME2, Constant.DIRECT_EXCHANGE_NAME, Constant.DEBUG_ROUTING_KEY);
        channel.queueBind(Constant.DIRECT_QUEUE_NAME2, Constant.DIRECT_EXCHANGE_NAME, Constant.ERROR_ROUTING_KEY);
        
        channel.basicConsume(Constant.DIRECT_QUEUE_NAME2, false, CommonUtil.createConsumer(channel));
    }
}
