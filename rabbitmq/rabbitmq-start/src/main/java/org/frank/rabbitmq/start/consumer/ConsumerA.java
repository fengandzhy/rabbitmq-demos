package org.frank.rabbitmq.start.consumer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConsumerA {
    public static void main(String[] args) throws IOException {
        Properties properties = new Properties();
        // 使用ClassLoader加载properties配置文件生成对应的输入流
        InputStream in = ConsumerA.class.getClassLoader().getResourceAsStream("config.properties");
        // 使用properties对象加载输入流
        properties.load(in);
        //获取key对应的value值
        System.out.println(properties.getProperty("host"));
    }
}
