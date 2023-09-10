package org.frank.rabbitmq.springboot.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {

    @Value("${mq.config.exchange}")
    private String exchangeName;

    @Value("${mq.config.queue}")
    private String queueName;
    
    @Bean
    public Exchange orderExchange(){
        return ExchangeBuilder.topicExchange(exchangeName).durable(true).build();
    }
    
    @Bean
    public Queue orderQueue(){
        return QueueBuilder.durable(queueName).build();
    }
    
    @Bean
    public Binding orderBinding(Queue queue, Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("order.#").noargs();
    }
}
