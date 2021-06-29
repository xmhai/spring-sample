package com.cl.smart.flow.web.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cl.smart.flow.domain.TaskRequest;
import com.cl.smart.flow.domain.TaskResponse;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * REST controller for task management.
 */
@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class TaskResource {
	private final Logger logger = LoggerFactory.getLogger(TaskResource.class);

	@Autowired
	private TaskService taskService;	

	@Autowired
	private RuntimeService runtimeService;	

	/**
	 * Get user task list
	 *
	 * @param userId
	 * @return Task list
	 */
	@GetMapping(value = "/groupInbox/{userId}")
	public List<TaskResponse> getGroupInbox(@PathVariable String userId) {
		logger.debug("Get task list for user id: "+userId);

		List<Task> taskList = taskService.createTaskQuery().taskCandidateUser(userId).list();
		return createTaskResponse(taskList);
	}

	// add process instance variables to task Response
	private List<TaskResponse> createTaskResponse(List<Task> taskList) {
		List<TaskResponse> taskResponseList = new ArrayList<TaskResponse>(); 
		
		for (Task task : taskList) {
			taskResponseList.add(createTaskResponse(task));
		}
		
		return taskResponseList;
	}
	
	private TaskResponse createTaskResponse(Task task) {
		TaskResponse taskResponse = new TaskResponse();
		taskResponse.setTaskId(task.getId());
		taskResponse.setName(task.getName());
		taskResponse.setAssignee(task.getAssignee());
		taskResponse.setProcessInstanceId(task.getProcessInstanceId());

        Map<String, String> variables = new HashMap<>();
		Map<String, Object> processVariables = runtimeService.getVariables(task.getProcessInstanceId());
		for (Entry<String, Object> entry : processVariables.entrySet()) {
			if (entry.getValue()!=null) {
				variables.put(entry.getKey(), entry.getValue().toString());
			}
	    }
		taskResponse.setVariables(variables);
		return taskResponse;
	}
	
	/**
	 * Get user task list
	 *
	 * @param userId
	 * @return Task list
	 */
	@GetMapping(value = "/myInbox/{userId}")
	public List<TaskResponse> getMyInbox(@PathVariable String userId) {
		logger.debug("Get task list for user id: "+userId);
		List<Task> taskList = taskService.createTaskQuery().taskAssignee(userId).list();
		return createTaskResponse(taskList);
	}

	/**
	 * Get task
	 *
	 * @param taskId
	 * @return Task list
	 */
	@GetMapping(value = "/tasks/{taskId}")
	public TaskResponse getTask(@PathVariable String taskId) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		return createTaskResponse(task);
	}

	/**
	 * process task
	 *
	 * @param taskRequest
	 * @return status
	 * @throws JsonProcessingException 
	 */
	@PutMapping(value = "/tasks")
	public String processTask(@RequestBody TaskRequest request) throws JsonProcessingException {
		String result;
		
		// get task information
		String taskId = request.getTaskId();
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
	    if (task == null) {
	    	throw new ActivitiObjectNotFoundException("Could not find a task with id '" + taskId + "'.", Task.class);
	    }

	    // perform action
		String action = request.getAction();
		if ("claim".equals(action)) {
			result = claimTask(request);
		} else if ("unclaim".equals(action)) {
			result = unclaimTask(request);
		} else if ("assign".equals(action)) {
			result = assignTask(request);
		} else if ("complete".equals(action)) {
			result = completeTask(request);
		} else {
			return "Error: invalid action parameter";
		}
		
		return result;
	}
	
	private String claimTask(TaskRequest request) {
		taskService.claim(request.getTaskId(), request.getUserId());
	    return "success";
	}

	private String unclaimTask(TaskRequest request) {
		taskService.setAssignee(request.getTaskId(), null);
	    return "success";
	}

	private String assignTask(TaskRequest request) {
		if (StringUtils.isNotEmpty(request.getUserId())) {
			taskService.setAssignee(request.getTaskId(), request.getUserId());
		} else if (StringUtils.isNotEmpty(request.getGroupId())) {
			taskService.addCandidateGroup(request.getTaskId(), request.getGroupId());
		}
	    return "success";
	}

	private String completeTask(TaskRequest request) {
		taskService.complete(request.getTaskId(), request.getVariables());
	    return "success";
	}
}
