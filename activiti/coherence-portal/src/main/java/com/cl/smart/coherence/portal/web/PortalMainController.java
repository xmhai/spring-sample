package com.cl.smart.coherence.portal.web;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.cl.smart.coherence.portal.web.vm.Application;

@Controller
public class PortalMainController {
	RestTemplate restTemplate = new RestTemplate(); 
	
	@GetMapping(value={"/","/login"})
	public String welcome(Map<String, Object> model) {
		model.put("message", "Coherence");
		return "login";
	}

	@PostMapping("/login")
	public String login(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		String userId = request.getParameter("userId");
		request.getSession().setAttribute("userId", userId);
		return "redirect:main";
	}

	@GetMapping("/main")
	private String showApplicationList(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		String userId = (String) request.getSession().getAttribute("userId");
		if (userId==null) {
			return "login"; 
		}
		model.put("userId", userId);
		
		// invoke application microservice to submit application
		Application[] result = restTemplate.getForObject("http://localhost:8104/applications", Application[].class);
	    List<Application> applicationList= Arrays.asList(result);
		model.put("applications", applicationList);
		return "main";
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		return "login";
	}

	@GetMapping("/apply")
	public String apply(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		return "application";
	}

	@PostMapping("/submit")
	public String submit(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model,
			Application application) throws RestClientException, URISyntaxException {
		String userId = (String) request.getSession().getAttribute("userId");
		application.setCreatedBy(userId);
		application.setCreatedDate(new Date());
		
		// invoke application microservice to submit application
		restTemplate.postForObject("http://localhost:8104/applications", application, Application.class);
		
		return "redirect:main";
	}
}
