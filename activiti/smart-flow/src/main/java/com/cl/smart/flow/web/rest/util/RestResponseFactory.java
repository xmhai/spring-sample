package com.cl.smart.flow.web.rest.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import com.cl.smart.flow.domain.ProcessDefinitionResponse;
import com.cl.smart.flow.domain.ProcessInstanceResponse;
import com.cl.smart.flow.domain.TaskResponse;

public class RestResponseFactory {
	public static ProcessInstanceResponse createProcessInstanceResponse(ProcessInstance processInstance) {
		ProcessInstanceResponse processInstanceResponse = new ProcessInstanceResponse();
		processInstanceResponse.setProcessInstanceId(processInstance.getId());
		processInstanceResponse.setProcessDefinitionKey(processInstance.getProcessDefinitionKey());
		processInstanceResponse.setStartTime(processInstance.getStartTime());
		processInstanceResponse.setStartUserId(processInstance.getStartUserId());
		processInstanceResponse.setEnded(processInstance.isEnded());
		
        /*Map<String, String> variables = new HashMap<>();
		for (Entry<String, Object> entry : processInstance.getProcessVariables().entrySet()) {
			variables.put(entry.getKey(), entry.getValue().toString());
	    }*/
		
		processInstanceResponse.setVariables(processInstance.getProcessVariables().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().toString())));		
		
		return processInstanceResponse;
	}

	public static TaskResponse createTaskResponse(Task task) {
		TaskResponse taskResponse = new TaskResponse();
		taskResponse.setTaskId(task.getId());
		taskResponse.setName(task.getName());
		taskResponse.setAssignee(task.getAssignee());

        Map<String, String> variables = new HashMap<>();
		for (Entry<String, Object> entry : task.getProcessVariables().entrySet()) {
			variables.put(entry.getKey(), entry.getValue().toString());
	    }
		taskResponse.setVariables(variables);
		
		return taskResponse;
	}

	public static ProcessDefinitionResponse createProcessDefinitionResponse(ProcessDefinition processDefinition, Deployment deployment) {
		ProcessDefinitionResponse processDefinitionResponse = new ProcessDefinitionResponse();
		processDefinitionResponse.setId(processDefinition.getKey());
		processDefinitionResponse.setName(processDefinition.getName());
		processDefinitionResponse.setDescription(processDefinition.getDescription());
		processDefinitionResponse.setVersion(processDefinition.getVersion());
		processDefinitionResponse.setDeploymentTime(deployment.getDeploymentTime());
		return processDefinitionResponse;
	}
}
