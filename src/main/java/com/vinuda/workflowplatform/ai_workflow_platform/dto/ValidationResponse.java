package com.vinuda.workflowplatform.ai_workflow_platform.dto;

import lombok.Data;
import java.util.Map;
import java.util.List;

@Data
public class ValidationResponse {
    private List<String> issues;
    private Map<String, String> suggestions;

}
