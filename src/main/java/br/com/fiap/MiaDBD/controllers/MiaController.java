package br.com.fiap.MiaDBD.controllers;

import br.com.fiap.MiaDBD.records.MiaImagesResponse;
import br.com.fiap.MiaDBD.records.MiaStepsResponse;
import br.com.fiap.MiaDBD.records.MiaResponse;
import br.com.fiap.MiaDBD.services.MiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

class Question {
    private String appName;
    private String text;
    // ...


    public String getAppName() {
        return appName;
    }

    public String getText() {
        return text;
    }
}

@RestController
public class MiaController {

    @Autowired
    private MiaService service;

    @PostMapping("ask")
    public Mono<MiaResponse> getStepsAndImages(@RequestBody Question question) {
        return service.getSteps(question.getAppName(), question.getText())
                .flatMap(steps -> service.getImages(steps)
                        .map(images -> new MiaResponse(steps, images)));
    }
}
