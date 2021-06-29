package com.cl.smart.flow.web.rest;

import java.util.List;

import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cl.smart.flow.domain.ProcessInstanceActivity;
import com.cl.smart.flow.domain.ProcessInstanceCreateRequest;
import com.cl.smart.flow.domain.ProcessInstanceResponse;
import com.cl.smart.flow.service.ProcessInstanceHistoryService;
import com.cl.smart.flow.web.rest.util.RestResponseFactory;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * REST controller for process instance management.
 */
@RestController
@RequestMapping(value = "/api")
public class InstanceResource {
	private final Logger logger = LoggerFactory.getLogger(InstanceResource.class);

	@Autowired
	private RuntimeService runtimeService;	

	@Autowired
	private ProcessInstanceHistoryService processInstanceHistoryService;
	
	/**
	 * Start a process instance
	 *
	 * @param ProcessInstanceCreateRequest process creation information
	 * @return Process Instance information
	 * @throws JsonProcessingException 
	 */
	@PostMapping("/instances")
	public ProcessInstanceResponse startProcess(@RequestBody ProcessInstanceCreateRequest processInstanceCreateRequest) throws JsonProcessingException {
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processInstanceCreateRequest.getProcessDefinitionKey(), processInstanceCreateRequest.getVariables());
		return RestResponseFactory.createProcessInstanceResponse(processInstance);
	}

	/**
	 * Get a process instance
	 *
	 * @param processInstanceId
	 * @return Process Instance information
	 */
	@GetMapping(value = "/instances/{processInstanceId}", produces = "application/json")
	public ProcessInstanceResponse getProcessInstance(@PathVariable String processInstanceId) {
		logger.debug("Get Process Instance id: "+processInstanceId);
	    ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
	    if (processInstance == null) {
	      throw new ActivitiObjectNotFoundException("Could not find a process instance with id '" + processInstanceId + "'.");
	    }
	    return RestResponseFactory.createProcessInstanceResponse(processInstance);
	}

	/**
	 * Get a process instance history
	 *
	 * @param processInstanceId
	 * @return Process Instance History information
	 */
	@GetMapping(value = "/instances/{processInstanceId}/history", produces = "application/json")
	public List<ProcessInstanceActivity> getProcessInstanceHistory(@PathVariable String processInstanceId) {
		logger.debug("Get Process History for Instance id: "+processInstanceId);
	    ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
	    if (processInstance == null) {
	      throw new ActivitiObjectNotFoundException("Could not find a process instance with id '" + processInstanceId + "'.");
	    }
	    
	    List<ProcessInstanceActivity> history = processInstanceHistoryService.findByProcessInstanceId(processInstance.getId());
	    return history;
	}
}
