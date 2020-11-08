package org.zhouhy.rabbitmq.confirm;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.zhouhy.rabbitmq.common.CommUtil;
import org.zhouhy.rabbitmq.common.RabbitMQConstants;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConfirmConsumer {

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = CommUtil.createConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(RabbitMQConstants.Confirm_Exchange_Name,BuiltinExchangeType.TOPIC);
    }
}
