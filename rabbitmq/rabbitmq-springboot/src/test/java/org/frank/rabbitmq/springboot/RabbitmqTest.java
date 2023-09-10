package org.frank.rabbitmq.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

@SpringBootTest
public class RabbitmqTest {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private Environment env;

    @Test
    void testSend() {
        for(int i=0;i<5;i++){
            template.convertAndSend(env.getProperty("mq.config.exchange"), "order.new","新订单");
        }
    }
}
