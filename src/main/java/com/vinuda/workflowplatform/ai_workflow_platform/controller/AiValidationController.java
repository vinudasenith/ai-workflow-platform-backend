package com.vinuda.workflowplatform.ai_workflow_platform.controller;

import com.vinuda.workflowplatform.ai_workflow_platform.service.AiValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/ai")
public class AiValidationController {

    @Autowired
    private AiValidationService aiValidationService;

    @PostMapping("/validate")
    public Map<String, Object> validateForm(@RequestBody Map<String, Object> requestData) {
        Map<String, Object> response = new HashMap<>();
        try {
            Map<String, Object> validatedData = aiValidationService.validateForm(requestData);
            response.put("status", "success");
            response.put("data", validatedData);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", "AI validation failed: " + e.getMessage());
        }
        return response;
    }

}
