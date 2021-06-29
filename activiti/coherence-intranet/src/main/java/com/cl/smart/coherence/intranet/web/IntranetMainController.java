package com.cl.smart.coherence.intranet.web;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.cl.smart.coherence.intranet.web.vm.UpdateTaskRequest;
import com.cl.smart.coherence.intranet.web.vm.Application;
import com.cl.smart.coherence.intranet.web.vm.ProcessInstanceActivity;
import com.cl.smart.coherence.intranet.web.vm.Task;

@Controller
public class IntranetMainController {
	RestTemplate restTemplate = new RestTemplate(); 
	
	@RequestMapping(value={"/","/login"})
	public String welcome(Map<String, Object> model) {
		model.put("message", "Coherence");
		return "login";
	}

	@PostMapping("/login")
	public String login(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		String userId = request.getParameter("userId");
		if (userId==null) {
			return "redirect:login";
		}
		
		request.getSession().setAttribute("userId", userId);
		return "redirect:main";
	}

	@GetMapping(value={"/main","/myinbox"})
	private String showMyInbox(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		String userId = (String) request.getSession().getAttribute("userId");
		if (userId==null) {
			return "redirect:login";
		}
		
		Map<String, String> ids = getApplicationInbox(userId, false);
		List<Application> applicationList = findApplicationById(ids); 

	    model.put("userId", userId);
		model.put("applications", applicationList);
		
		String action = "evaluate";
		if (StringUtils.startsWithIgnoreCase(userId, "a")) {
			action = "approve";
		}
	    model.put("action", action);
	    
		return "myinbox";
	}
	
	@GetMapping(value={"/groupinbox"})
	private String showGroupInbox(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		String userId = (String) request.getSession().getAttribute("userId");
		if (userId==null) {
			return "redirect:login";
		}
		
		Map<String, String> ids = getApplicationInbox(userId, true);
		List<Application> applicationList = findApplicationById(ids); 
		
		model.put("userId", userId);
		model.put("applications", applicationList);
		return "groupinbox";
	}

	private Map<String, String> getApplicationInbox(String userId, boolean isGroupInbox) {
		String url = "http://localhost:8103/api/myInbox/"+userId;
		if (isGroupInbox) {
			url = "http://localhost:8103/api/groupInbox/"+userId;
		}
		
		Map<String, String> idMap = new HashMap<String, String>();
		Task[] taskList = restTemplate.getForObject(url, Task[].class);
		for (Task task : taskList) {
			Map<String, String> variables = task.getVariables();
			if (variables!=null && variables.containsKey("applicationId")) {
				//applicationIdList
				idMap.put(variables.get("applicationId"), task.getTaskId());
			}
		}
		return idMap;
	}
	
	private List<Application> findApplicationById(Map<String, String> idMap) {
		List<Application> applicationList = new ArrayList<Application>();
		List<String> ids = new ArrayList<>(idMap.keySet());
		if (ids.size()>0) {
			// invoke application service to get applications
			Application[] result = restTemplate.getForObject("http://localhost:8104/applications?ids="+String.join(",", ids), Application[].class);
		    applicationList= Arrays.asList(result);
			// set task id
			for (Application application : applicationList) {
				application.setTaskId(idMap.get(application.getId()));
			}
		}
		
		return applicationList;
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		return "login";
	}

	@PostMapping("/inbox/acquire/{taskId}")
	public String acquire(HttpServletRequest request, @PathVariable String taskId) {
		UpdateTaskRequest req = new UpdateTaskRequest();
		req.setAction("claim");
		String userId = (String) request.getSession().getAttribute("userId");
		req.setUserId(userId);
		req.setTaskId(taskId);
		restTemplate.put("http://localhost:8103/api/tasks", req);
		return "groupinbox";
	}

	@PostMapping("/inbox/unclaim/{taskId}")
	public String unclaim(HttpServletRequest request, @PathVariable String taskId) {
		UpdateTaskRequest req = new UpdateTaskRequest();
		req.setAction("unclaim");
		String userId = (String) request.getSession().getAttribute("userId");
		req.setUserId(userId);
		req.setTaskId(taskId);
		restTemplate.put("http://localhost:8103/api/tasks", req);
		return "groupinbox";
	}

	@GetMapping("/inbox/open/{taskId}/{applicationId}/{action}")
	public String openWorkSpace(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model,
			@PathVariable String taskId, @PathVariable String applicationId, @PathVariable String action) {
		Application application = restTemplate.getForObject("http://localhost:8104/applications/"+applicationId, Application.class);
		application.setTaskId(taskId);
		model.put("application", application);
		
		if ("evaluate".equals(action)) {
			return "workspace";
		} else {
			Task task = restTemplate.getForObject("http://localhost:8103/api/tasks/"+taskId, Task.class);
			ProcessInstanceActivity[] history = restTemplate.getForObject("http://localhost:8103/api/instances/"+task.getProcessInstanceId()+"/history", ProcessInstanceActivity[].class);
			model.put("history", Arrays.asList(history));

			return "workspace_approver";
		}
	}

	@GetMapping("/apply")
	public String apply(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		return "application";
	}

	@PostMapping("/workspace/evaluate")
	public String evaluate(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) throws RestClientException, URISyntaxException {
		String userId = (String) request.getSession().getAttribute("userId");
		if (userId==null) {
			return "redirect:login";
		}
		
		String taskId = (String) request.getParameter("taskId");
		//String applicationId = (String) request.getParameter("applicationId");
		String recommendation = (String) request.getParameter("recommendation");
		String routeToUserId = (String) request.getParameter("routeToUserId");
		
		if (recommendation.equals("approval") || recommendation.equals("rejected")) {
			evaluate(taskId, userId, recommendation);
		} else if (recommendation.equals("route")) {
			route(taskId, routeToUserId);
		}
		
		return "redirect:/myinbox";
	}
	
	private void evaluate(String taskId, String userId, String recommendation) {
		// invoke bpm service to complete task with the recommendation
		UpdateTaskRequest req = new UpdateTaskRequest();
		req.setAction("complete");
		req.setUserId(userId);
		req.setTaskId(taskId);
		Map<String, String> variables = new HashMap<String, String>();
		variables.put("recommendation", recommendation);
		variables.put("evaluationOutcome", "success");
		req.setVariables(variables);
		restTemplate.put("http://localhost:8103/api/tasks", req);
	}

	private void route(String taskId, String routeToUserId) {
		// invoke bpm service to reassign task to routeToUserId
		UpdateTaskRequest req = new UpdateTaskRequest();
		req.setAction("assign");
		req.setUserId(routeToUserId);
		req.setTaskId(taskId);
		restTemplate.put("http://localhost:8103/api/tasks", req);
	}

	@PostMapping("/workspace/approve")
	public String approve(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) throws RestClientException, URISyntaxException {
		String userId = (String) request.getSession().getAttribute("userId");
		if (userId==null) {
			return "redirect:login";
		}
		
		String taskId = (String) request.getParameter("taskId");
		//String applicationId = (String) request.getParameter("applicationId");
		String recommendation = (String) request.getParameter("recommendation");
		String routeToUserId = (String) request.getParameter("routeToUserId");
		
		if (recommendation.equals("approval") || recommendation.equals("rejected")) {
			String approvalOutcome = recommendation.equals("approval") ? "success" : "failed";
			approve(taskId, userId, approvalOutcome);
		} else if (recommendation.equals("route")) {
			route(taskId, routeToUserId);
		}
		
		return "redirect:/myinbox";
	}

	private void approve(String taskId, String userId, String approvalOutcome) {
		// invoke bpm service to complete task with the recommendation
		UpdateTaskRequest req = new UpdateTaskRequest();
		req.setAction("complete");
		req.setUserId(userId);
		req.setTaskId(taskId);
		Map<String, String> variables = new HashMap<String, String>();
		variables.put("approvalOutcome", approvalOutcome);
		req.setVariables(variables);
		restTemplate.put("http://localhost:8103/api/tasks", req);
	}
}
