package br.com.fiap.MiaDBD.services;

import br.com.fiap.MiaDBD.records.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
public class MiaService {

    @Autowired
    private MiaFigmaService figmaService;

    @Autowired
    private MiaChatGPTService chatGPTService;

    @Autowired
    private ApplicationService applicationService;

    public static Map<String, List<String>> extractSteps(String text, List<String> arrayList) {
        Map<String, List<String>> map = new LinkedHashMap<>();
        Pattern pattern = Pattern.compile("- (.*?) \\((.*?)\\)");
        Matcher matcher = pattern.matcher(text);

        matcher.results().forEach(result -> {
            String sentence = result.group(1);
            List<String> values = Arrays.stream(result.group(2).split(", "))
                    .mapToInt(Integer::parseInt)
                    .filter(index -> index < arrayList.size())
                    .mapToObj(arrayList::get)
                    .collect(Collectors.toList());

            map.put(sentence, values);
        });

        return map;
    }

    public Mono<MiaStepsResponse> getSteps(String appName, String question) {
        Mono<ChatGPTResponse> explanation = chatGPTService.createTaskExplanation(question);

        return explanation.map(choice -> {
            String content = choice.choices().get(0).message().content();
            List<String> steps = List.of(content.substring(content.indexOf("["), content.indexOf("]")));

            Map<String, List<String>> map = extractSteps(choice.choices().get(0).message().content(), steps);
            ArrayList<String> screens = new ArrayList<>(Arrays.asList("Whatsapp-tela-Idioma".toLowerCase(), "Whatsapp-tela-Menu".toLowerCase()));
            ArrayList<String> elements = new ArrayList<>(Arrays.asList("image 1".toLowerCase(), "acao_opcao_Configuracoes".toLowerCase()));


            return new MiaStepsResponse(appName, question, map, screens, elements);
        });
    }

    public Mono<MiaImagesResponse> getImages(MiaStepsResponse response) {
        String fileId = "8tmmUHx8kFebwVBQh1L7dN";

        return figmaService.getFigmaFileNodesIds(fileId)
                .flatMap(figmaFile -> {
                    DocumentScreen[] documentScreens = figmaFile.document().children()[0].children();
                    List<DocumentScreen> screens = Arrays.stream(documentScreens)
                            .filter(screen -> response.screens().contains(screen.name().toLowerCase()))
                            .toList();
                    List<String> screenIds = screens.stream()
                            .map(DocumentScreen::id)
                            .collect(Collectors.toList());

                    List<DocumentElement> screenElements = new ArrayList<>();

                    for (int i = 0; i < response.elements().size(); i++) {
                        screenElements.addAll(List.of(screens.get(i).children()));
                    }

                    List<String> elementsIds = screenElements.stream()
                            .filter(element -> response.elements().contains(element.name().toLowerCase()))
                            .map(DocumentElement::id)
                            .toList();

                    Mono<FigmaImagesResponse> screenImages = figmaService.getFigmaImage(fileId, String.join(",", screenIds));
                    Mono<FigmaImagesResponse> elementsImages = figmaService.getFigmaImage(fileId, String.join(",", elementsIds));

                    return Mono.zip(screenImages, elementsImages)
                            .map(tuple -> new MiaImagesResponse(tuple.getT1(), tuple.getT2()));
                });
    }
}
