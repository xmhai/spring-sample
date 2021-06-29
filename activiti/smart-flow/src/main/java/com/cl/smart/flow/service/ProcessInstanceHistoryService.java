package com.cl.smart.flow.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.impl.persistence.entity.TaskEntityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cl.smart.flow.domain.ProcessInstanceActivity;
import com.cl.smart.flow.repository.ProcessInstanceActivityRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ProcessInstanceHistoryService {
	private final Logger logger = LoggerFactory.getLogger(ProcessInstanceHistoryService.class);

	@Autowired
	private ProcessInstanceActivityRepository processInstanceActivityRepository; 
	
	public List<ProcessInstanceActivity> findByProcessInstanceId(String processInstanceId) {
		return processInstanceActivityRepository.findByProcessInstanceId(processInstanceId);
	}

	public void saveProcessActivity(ActivitiEventType type, ActivitiEvent event, ProcessDefinition processDefinition) {
		String action = "";
		String description = "";
		if (type==ActivitiEventType.PROCESS_STARTED) {
			action = "create";
			description = "Process started";
		} else if (type==ActivitiEventType.PROCESS_COMPLETED) {
			action = "complete";
			description = "Process ended";
		}
		
		ProcessInstanceActivity processInstanceActivity = createActivity(action, description, null);
		processInstanceActivity.setProcessInstanceId(event.getProcessInstanceId());
		processInstanceActivity.setName(processDefinition.getName());
		save(processInstanceActivity);
	}
	
	private ProcessInstanceActivity createActivity(String action, String description, Map<String, Object> variables) {
		ProcessInstanceActivity processInstanceActivity = new ProcessInstanceActivity();
		processInstanceActivity.setAction(action);
		processInstanceActivity.setDescription(description);
		processInstanceActivity.setActionTime(new Date());
		if (variables!=null) {
			try {
				processInstanceActivity.setVariables(new ObjectMapper().writeValueAsString(variables));
			} catch (JsonProcessingException e) {
				// should never happen, ignore it
				logger.error(e.getMessage());
			}
		}
		
		return processInstanceActivity;
	}
	
	public ProcessInstanceActivity save(ProcessInstanceActivity processInstanceActivity) {
		return processInstanceActivityRepository.save(processInstanceActivity);
	}

	public void saveTaskActivity(ActivitiEventType type, TaskEntityImpl taskEntity) {
		String description = "";
		String action = "";
		String actionUserId = taskEntity.getAssignee();
		if (type==ActivitiEventType.TASK_CREATED) {
			action = "create";
			description = "Task created by system";
		} else if (type==ActivitiEventType.TASK_ASSIGNED) {
			if (taskEntity.getAssignee()==null) {
				description = "Task returned to group inbox";
				action = "return";
				actionUserId = taskEntity.getOriginalAssignee();
			} else if (taskEntity.getOriginalAssignee()==null || taskEntity.getAssignee().equals(taskEntity.getOriginalAssignee()) ) {
				description = "Task acquired by "+taskEntity.getAssignee();
				action = "acquire";
			} else {
				description = "Task assigned to "+taskEntity.getAssignee()+" from "+taskEntity.getOriginalAssignee();
				action = "assign";
				actionUserId = taskEntity.getOriginalAssignee();
			}
		} else if (type==ActivitiEventType.TASK_COMPLETED) {
			action = "complete";
			description = "Task completed by "+taskEntity.getAssignee();
		}
		
		ProcessInstanceActivity processInstanceActivity = createActivity(action, description, taskEntity.getTaskLocalVariables());
		
		processInstanceActivity.setName(taskEntity.getName());
		processInstanceActivity.setProcessInstanceId(taskEntity.getProcessInstanceId());
		processInstanceActivity.setTaskId(taskEntity.getId());
		processInstanceActivity.setActionUserId(actionUserId);
		
		save(processInstanceActivity);
	}
}
