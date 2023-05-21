package br.com.fiap.MiaDBD.controllers;

import br.com.fiap.MiaDBD.records.FigmaFileResponse;
import br.com.fiap.MiaDBD.records.FigmaImagesResponse;
import br.com.fiap.MiaDBD.services.MiaFigmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
public class MiaFigmaController {

    @Autowired
    private MiaFigmaService service;

    @GetMapping("getFigmaImage/{fileId}/{nodeId}")
    public Mono<FigmaImagesResponse> getFigmaImage(@PathVariable String fileId, @PathVariable String nodeId) {
        return service.getFigmaImage(fileId, nodeId);
    }

    @GetMapping("getFigmaFileNodes/{fileId}")
    public Mono<FigmaFileResponse> getFigmaFileNodesIds(@PathVariable String fileId) {
        return service.getFigmaFileNodesIds(fileId);
    }
}
