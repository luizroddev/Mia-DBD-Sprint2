package br.com.fiap.MiaDBD.services;

import java.util.List;

public record MiaStepsResponse(String appName, String question, List<String> screens, List<String> elements) {
}
