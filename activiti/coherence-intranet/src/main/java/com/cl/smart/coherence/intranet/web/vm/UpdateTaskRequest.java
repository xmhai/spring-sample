package com.cl.smart.coherence.intranet.web.vm;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateTaskRequest {
    private String action;
    private String taskId;
    private String userId;
    private Map<String, String> variables;
}
