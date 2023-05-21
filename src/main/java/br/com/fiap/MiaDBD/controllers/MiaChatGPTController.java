package br.com.fiap.MiaDBD.controllers;

import br.com.fiap.MiaDBD.services.MiaChatGPTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class MiaChatGPTController {

    @Autowired
    private MiaChatGPTService service;

    @PostMapping("explanation")
    public Mono<String> createTaskExplanation(@RequestBody String question) {
        return service.createTaskExplanation(question).map(response -> response.choices().get(0).message().content());
    }
}
