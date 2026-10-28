package Task4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task4TagCounter {
    public static void main(String[] args) {
        String urlString = "https://example.com";

        try {
            URL url = new URI(urlString).toURL();
            StringBuilder content = new StringBuilder();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
            }

            Map<String, Integer> tagMap = extractTags(content.toString());

            System.out.println("=== a. В порядку зростання для тегів (лексикографічно) ===");
            List<Map.Entry<String, Integer>> lexicographicalList = new ArrayList<>(tagMap.entrySet());
            lexicographicalList.sort(Map.Entry.comparingByKey(String.CASE_INSENSITIVE_ORDER));
            for (Map.Entry<String, Integer> entry : lexicographicalList) {
                System.out.printf("%-15s : %d%n", entry.getKey(), entry.getValue());
            }

            System.out.println("\n=== b. В порядку зростання по частоті появи тегів ===");
            List<Map.Entry<String, Integer>> frequencyList = new ArrayList<>(tagMap.entrySet());
            frequencyList.sort(Map.Entry.comparingByValue());
            for (Map.Entry<String, Integer> entry : frequencyList) {
                System.out.printf("%-15s : %d%n", entry.getKey(), entry.getValue());
            }

        } catch (Exception e) {
            System.out.println("Помилка під час отримання даних: " + e.getMessage());
        }
    }

    private static Map<String, Integer> extractTags(String html) {
        Map<String, Integer> tagMap = new HashMap<>();
        Pattern pattern = Pattern.compile("<\\s*([a-zA-Z0-9]+)");
        Matcher matcher = pattern.matcher(html);

        while (matcher.find()) {
            String tagName = matcher.group(1).toLowerCase();
            tagMap.put(tagName, tagMap.getOrDefault(tagName, 0) + 1);
        }
        return tagMap;
    }
}