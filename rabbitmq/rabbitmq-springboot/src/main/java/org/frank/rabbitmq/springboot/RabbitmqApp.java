package org.frank.rabbitmq.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * 1.创建 RabbitmqConfig 里面有 exchange, queue 和 binding 
 * 2.创建 OrderMQListener 里面有一个handler 相当于 CommonUtil 里面的 consumer 
 * 
 * 
 * */
@SpringBootApplication
public class RabbitmqApp {
    public static void main(String[] args) {
        SpringApplication.run(RabbitmqApp.class, args);
    }
}
