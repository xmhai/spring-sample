package com.cl.smart.coherence.admin.web;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.cl.smart.coherence.admin.web.vm.ProcessDefinitionResponse;

@Controller
public class AdminMainController {
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
		ProcessDefinitionResponse[] result = restTemplate.getForObject("http://localhost:8103/api/processes", ProcessDefinitionResponse[].class);
	    List<ProcessDefinitionResponse> processes = Arrays.asList(result);
		model.put("processes", processes);
		return "main";
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		return "login";
	}

	/**
	 * Deploy a process
	 *
	 * @param process definition
	 * @return success/failed
	 * @throws IOException 
	 */
	@PostMapping(value = "/deploy")
	public String deploy(@RequestParam("file") MultipartFile file) throws IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		
		ByteArrayResource fileResource = new ByteArrayResource(file.getBytes()){
            @Override
            public String getFilename(){
                return file.getOriginalFilename();
            }
        };		
		body.add("file", fileResource);
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
		 
		String serverUrl = "http://localhost:8103/api/processes";
		 
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.postForEntity(serverUrl, requestEntity, String.class);
		return "redirect:main";
	}
}
