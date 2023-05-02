package br.com.fiap.MiaDBD.services;

import br.com.fiap.MiaDBD.records.ChatGPTResponse;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class MiaChatGPTService {

    private WebClient webClient;

    public MiaChatGPTService(WebClient.Builder builder, @Value("${openai.api.key}") String apiKey) {

        this.webClient = builder.baseUrl("https://api.openai.com/v1/completions").defaultHeader("Content-Type", "application/json").defaultHeader("Authorization", String.format("Bearer %s", apiKey)).build();
    }

    public Mono<ChatGPTResponse> createTaskExplanation(String topic) {
        ChatGPTRequest request = createTaskRequest(topic);

        return webClient.post().bodyValue(request).retrieve().bodyToMono(ChatGPTResponse.class);
    }

    private ChatGPTRequest createTaskRequest(String topic) {
        String context = "You are a helpful assistant that provide complete explanations on how to specific tasks in a app, step by step ion the minimum details.\n" +
                "r\n" +
                "You first provide a explanation and after you provide a formatted array containing all the screens that you accessed and the elements that you touched in orde\n" +
                "\n" +
                "Example: [Whatsapp_screen_Home, Whatsapp_element_SettingsMenu, Whatsapp_screen_Settings]";
        return new ChatGPTRequest("text-davinci-003", context, 0.3, 2000, 1.0, 0.0, 0.0);
    }
}

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
record ChatGPTRequest(
        String model, String prompt, Double temperature, Integer maxTokens, Double topP,
        Double frequencyPenalty, Double presencePenalty) { }
