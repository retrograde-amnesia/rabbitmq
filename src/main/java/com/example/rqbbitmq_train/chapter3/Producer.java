package com.example.rqbbitmq_train.chapter3;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {
    public static void main(String[] args) throws IOException, TimeoutException {
        String queue= "queue",exchange="exchange",routing_key="key";
        byte[] message="双卡单发贺卡上“".getBytes();
        ConnectionFactory connectionFactory=new ConnectionFactory();
        connectionFactory.setPassword("guest");
        connectionFactory.setUsername("guest");
        connectionFactory.setPort(5672);
        connectionFactory.setHost("127.0.0.1");
        Connection connection=connectionFactory.newConnection();
        Channel channel=connection.createChannel();
        channel.exchangeDeclare(exchange,"direct",true );
        /* channel.exchangeDeclarePassive(exchange); */
        channel.queueDeclare("b",true,true,true,null);
        channel.queueBind("b",exchange,routing_key);
        channel.basicPublish(exchange,routing_key, MessageProperties.PERSISTENT_TEXT_PLAIN,message);
        channel.close();
        connection.close();

    }
}
