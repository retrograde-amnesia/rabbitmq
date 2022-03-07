package com.example.rqbbitmq_train;
import com.rabbitmq.client.*;
import java.io.IOException;
import java.util.concurrent.*;

public class RabbitConsumer {
    private static final String QUEUE_NAME="queue_demo";
    private static final String IP_ADDRESS="127.0.0.1";
    private static final int port=5672;
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Address[] addresses=new Address[]{
                new Address(IP_ADDRESS,port)
        };
        ConnectionFactory factory=new ConnectionFactory();
        factory.setUsername("guest");
        factory.setPassword("guest");
        Connection connection=factory.newConnection(addresses);
        final Channel channel= connection.createChannel();
        channel.basicQos(64);
        Consumer consumer=new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag,Envelope envelope,AMQP.BasicProperties properties,byte[] body) throws IOException {
                System.out.println("recv message:"+new String(body));
            /** */  try {
                  TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };
        channel.basicConsume(QUEUE_NAME,consumer);
        TimeUnit.SECONDS.sleep(5);
        channel.close();
        connection.close();
    }
}
