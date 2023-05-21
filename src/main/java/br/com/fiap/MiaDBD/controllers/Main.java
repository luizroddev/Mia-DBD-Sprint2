package br.com.fiap.MiaDBD.controllers;

import java.util.*;
import java.util.regex.*;

public class Main {
    public static void main(String[] args) {
        String texto = "- Clique no botão de Menu da tela Principal (0)\n" +
                "- Clique na opção Configurações no Menu e depois clique na opção Perfil na tela de Configurações (1, 2)\n";

        String[] array = {"Whatsapp-Principal_Menu", "Whatsapp-Menu_Configuracoes", "Whatsapp-Configuracoes_Perfil"};

        List<String> arrayList = Arrays.asList(array);

        Map<String, List<String>> map = extractMap(texto, arrayList);

        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            System.out.println("Chave: " + entry.getKey() + ", Valor: " + entry.getValue());
        }
    }

    public static Map<String, List<String>> extractMap(String texto, List<String> arrayList) {
        Map<String, List<String>> map = new LinkedHashMap<>();
        Pattern pattern = Pattern.compile("- (.*?) \\((.*?)\\)");
        Matcher matcher = pattern.matcher(texto);
        while (matcher.find()) {
            String frase = matcher.group(1);
            String[] indices = matcher.group(2).split(", ");
            List<String> values = new ArrayList<>();
            for (String indice : indices) {
                int index = Integer.parseInt(indice);
                if (index < arrayList.size()) {
                    values.add(arrayList.get(index));
                }
            }
            map.put(frase, values);
        }
        return map;
    }
}
