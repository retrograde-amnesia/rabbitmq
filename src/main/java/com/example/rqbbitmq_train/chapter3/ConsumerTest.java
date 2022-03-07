package com.example.rqbbitmq_train.chapter3;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ConsumerTest {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        String queue="queue";
        Address[] address=new Address[]{new Address("127.0.0.1", 5672)};
        ConnectionFactory connectionFactory=new ConnectionFactory();
        connectionFactory.setUsername("guest");
        connectionFactory.setUsername("guest");
        Connection connection=connectionFactory.newConnection(address);
        Channel channel=connection.createChannel();
        channel.basicQos(64);
        Consumer consumer=new DefaultConsumer(channel){
            @Override
           public void handleDelivery(String consumerTag,Envelope envelope,AMQP.BasicProperties properties,byte[] body) throws IOException {
               System.out.println(new String(body));
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };
     //   channel.;
        channel.basicConsume(queue,false,consumer);
        TimeUnit.SECONDS.sleep(9);
        channel.close();
        connection.close();


    }
}
