package com.demo.mailbuildingpublisher.controller;

import com.demo.mailbuildingpublisher.dto.BuildingMailDTO;
import com.demo.mailbuildingpublisher.infrastructure.mqsender.RabbitMQSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/porteria" )
public class MailRestController {
    @Autowired
    private RabbitMQSender rabbitMQSender;

    @PostMapping("/correo")
    public Mono<String> sendMail(@RequestBody BuildingMailDTO buildingMailDTO){
        return rabbitMQSender.send(buildingMailDTO)
                .then(Mono.just(String.format("Se envía mensaje: %s", buildingMailDTO.getMessage())));
    }
}
