package br.com.fiap.MiaDBD.controllers;

import br.com.fiap.MiaDBD.records.MiaResponse;
import br.com.fiap.MiaDBD.services.MiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

class Question {
    private String app;
    private String text;

    public String getApp() {
        return app;
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
    public Mono<ResponseEntity<MiaResponse>> getStepsAndImages(@RequestBody Question question) {
        return service.getSteps(question.getApp(), question.getText())
                .flatMap(stepsResponse -> service.getImages(stepsResponse)
                        .map(imagesResponse -> new MiaResponse(stepsResponse, imagesResponse)))
                .map(ResponseEntity::ok)
                .onErrorReturn(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }
}
