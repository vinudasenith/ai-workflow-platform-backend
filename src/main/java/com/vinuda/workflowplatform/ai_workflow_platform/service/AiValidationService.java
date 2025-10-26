package com.vinuda.workflowplatform.ai_workflow_platform.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import org.springframework.beans.factory.annotation.Value;

import java.util.Map;
import java.util.HashMap;

@Service
public class AiValidationService {

    private final String OPENAI_URL = "https://api.openai.com/v1/chat/completions";

    @Value("${openai.api.key}")
    private String API_KEY;

    @SuppressWarnings("unchecked")
    public Map<String, Object> validateForm(Map<String, Object> requestData) throws Exception {

        Map<String, String> formData = (Map<String, String>) requestData.get("fields");

        String prompt = "You are an AI that only returns valid JSON.\n" +
                "Clean the following data and return corrected fields and warnings in JSON format ONLY.\n" +
                "Do NOT include any explanation, quotes, or code fences.\n\n" +
                "organizationName: " + formData.getOrDefault("organizationName", "") + "\n" +
                "description: " + formData.getOrDefault("description", "") + "\n" +
                "industry: " + formData.getOrDefault("industry", "") + "\n" +
                "organizationSize: " + formData.getOrDefault("organizationSize", "") + "\n" +
                "ownerName: " + formData.getOrDefault("ownerName", "") + "\n" +
                "ownerEmail: " + formData.getOrDefault("ownerEmail", "") + "\n" +
                "ownerPassword: " + formData.getOrDefault("ownerPassword", "") + "\n" +
                "phoneNumber: " + formData.getOrDefault("phoneNumber", "") + "\n" +
                "\n" +
                "Return JSON with keys 'correctedFields' and 'warnings'. Only return these keys.";

        Map<String, Object> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", prompt);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-4.1-mini");
        requestBody.put("messages", new Map[] { message });
        requestBody.put("max_tokens", 500);

        ObjectMapper mapper = new ObjectMapper();
        String jsonBody = mapper.writeValueAsString(requestBody);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(API_KEY);

        HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(OPENAI_URL, entity, String.class);

        Map<String, Object> responseMap = mapper.readValue(response.getBody(), Map.class);
        Map<String, Object> choice = ((Map<String, Object>) ((java.util.List<?>) responseMap.get("choices")).get(0));
        Map<String, Object> messageObj = (Map<String, Object>) choice.get("message");
        String content = (String) messageObj.get("content");

        // Clean and parse JSON
        content = content.replaceAll("(?s)```.*?```", "").trim();
        int jsonStart = content.indexOf("{");
        int jsonEnd = content.lastIndexOf("}") + 1;
        if (jsonStart == -1 || jsonEnd == -1) {
            throw new RuntimeException("No JSON found in AI response:\n" + content);
        }
        String jsonString = content.substring(jsonStart, jsonEnd);

        Map<String, Object> aiResponse = mapper.readValue(jsonString, Map.class);

        // Ensure it has both keys
        if (!aiResponse.containsKey("correctedFields")) {
            aiResponse.put("correctedFields", formData);
        }
        if (!aiResponse.containsKey("warnings")) {
            aiResponse.put("warnings", new HashMap<>());
        }

        return aiResponse;
    }

}
