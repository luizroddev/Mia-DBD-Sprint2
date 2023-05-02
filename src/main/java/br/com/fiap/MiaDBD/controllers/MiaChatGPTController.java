package br.com.fiap.MiaDBD.controllers;

import br.com.fiap.MiaDBD.services.MiaServiceChatGPT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class MiaChatGPTController {

    @Autowired
    private MiaServiceChatGPT service;

    @PostMapping("explanation")
    public Mono<String> createTaskExplanation(@RequestBody String topic) {
        return service.createTaskExplanation(topic).map(response -> response.choices().get(0).text());
    }
}
