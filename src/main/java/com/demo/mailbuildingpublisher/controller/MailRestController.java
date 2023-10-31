package com.demo.mailbuildingpublisher.controller;

import com.demo.mailbuildingpublisher.dto.BuildingMailDTO;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/porteria" )
public class MailRestController {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @PostMapping("/correo")
    public Mono<String> sendMail(@RequestParam("exchangeName") String exchange, @RequestParam("routingKey") String routingKey,  @RequestBody BuildingMailDTO buildingMailDTO){
        amqpTemplate.convertAndSend(exchange, routingKey, buildingMailDTO);
        return Mono.just(String.format("Se envía mensaje: %s", buildingMailDTO.getMessage()));
    }
}
