package com.demo.mailbuildingpublisher.infrastructure.mqsender;

import com.demo.mailbuildingpublisher.dto.BuildingMailDTO;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
public class RabbitMQSender {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Value("${broker.rabbitmq.exchange}")
    String exchange;

    @Value("${broker.rabbitmq.routingkey}")
    private String routingKey;


    public Mono<Void> send(BuildingMailDTO buildingMailDTO){
        rabbitTemplate.convertAndSend(exchange, routingKey, buildingMailDTO);

        return Mono.empty();
    }
}
