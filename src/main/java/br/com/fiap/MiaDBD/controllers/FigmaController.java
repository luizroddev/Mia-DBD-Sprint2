package br.com.fiap.MiaDBD.controllers;

import br.com.fiap.MiaDBD.services.FigmaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FigmaController {

    private FigmaService figmaService;

    public FigmaController(FigmaService figmaService) {
        this.figmaService = figmaService;
    }

    @GetMapping("/figma/files/{fileId}")
    public ResponseEntity<String> getFile(@PathVariable String fileId) {
        return figmaService.getFile(fileId);
    }

    @GetMapping("/figma/files/{fileId}/nodes")
    public ResponseEntity<String> getFileNodes(
            @PathVariable String fileId,
            @RequestParam("nodeIds") String[] nodeIds) {
        return figmaService.getFileNodes(fileId, nodeIds);
    }

    @GetMapping("/figma/images/{fileId}")
    public ResponseEntity<String> getImage(
            @PathVariable String fileId,
            @RequestParam("nodeIds") String[] nodeIds,
            @RequestParam("format") String format) {
        return figmaService.getImage(fileId, nodeIds, format);
    }
}
