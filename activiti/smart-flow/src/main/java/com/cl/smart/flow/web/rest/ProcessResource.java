package com.cl.smart.flow.web.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cl.smart.flow.domain.ProcessDefinitionResponse;
import com.cl.smart.flow.web.rest.util.RestResponseFactory;

/**
 * REST controller for process deployment
 */
@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class ProcessResource {
	@Autowired
	protected RepositoryService repositoryService;
	
	/**
	 * Get process definition list
	 *
	 * @param 
	 * @return process definition list
	 */
	@GetMapping(value = "/processes")
	public List<ProcessDefinitionResponse> getProcesses() {
		List<ProcessDefinition> processDefinitionList = repositoryService.createProcessDefinitionQuery().latestVersion().list();
		
		List<ProcessDefinitionResponse> processes = new ArrayList<ProcessDefinitionResponse>();
		for (ProcessDefinition processDefinition : processDefinitionList) {
			Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(processDefinition.getDeploymentId()).singleResult();
			processes.add(RestResponseFactory.createProcessDefinitionResponse(processDefinition, deployment));
			deployment.getDeploymentTime();
		}
		return processes;
	}

	/**
	 * Deploy a process
	 *
	 * @param process definition
	 * @return success/failed
	 * @throws IOException 
	 */
	@PostMapping(value = "/processes")
	public String deploy(@RequestParam("file") MultipartFile file) throws IOException {
		Deployment deployment = repositoryService.createDeployment()
			.addInputStream(file.getOriginalFilename(), file.getInputStream())
		    .deploy();
		return "success";
	}
}
