package onetoone.Auto;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class AutoIndex {

    private static final String API_URL = "***REMOVED***";


    @PostMapping("/auto")
    public String callGeminiApi(@RequestParam("prompt") String prompt) {
        try {

            String jsonPayload = """
                    {
                      "contents": [{
                        "parts": [{"text": "%s"}]
                      }]
                    }
                    """.formatted(prompt);


            HttpClient client = HttpClient.newHttpClient();


            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                    .build();


            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


            if (response.statusCode() == 200) {

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(response.body());
                JsonNode textNode = rootNode.at("/candidates/0/content/parts/0/text");

                return textNode.asText();


            } else {
                System.err.println("Failed to fetch content. Status Code: " + response.statusCode());
                return null;
            }

        } catch (Exception e) {
            System.err.println("Error occurred while calling the Gemini API: " + e.getMessage());
            return null;
        }
    }
}

