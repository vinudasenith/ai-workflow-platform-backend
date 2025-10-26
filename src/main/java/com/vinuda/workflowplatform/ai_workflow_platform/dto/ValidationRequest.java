package com.vinuda.workflowplatform.ai_workflow_platform.dto;

import lombok.Data;
import java.util.Map;

@Data
public class ValidationRequest {
    private String formType;
    private Map<String, String> fields;

}
