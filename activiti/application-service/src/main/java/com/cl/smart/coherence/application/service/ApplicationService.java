package com.cl.smart.coherence.application.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.cl.smart.coherence.application.domain.Application;
import com.cl.smart.coherence.application.repository.ApplicationRepository;

import lombok.Getter;
import lombok.Setter;

@Component
public class ApplicationService {
	RestTemplate restTemplate = new RestTemplate(); 
	
	@Autowired
	ApplicationRepository repository;
	
	@Autowired
    JdbcTemplate jdbcTemplate;	

	public List<Application> findAll() {
		return repository.findAll();
	}

	public Optional<Application> findById(Long id) {
		return repository.findById(id);
	}

	public List<Application> findAllById(List<Long> ids) {
		return repository.findAllById(ids);
	}

	@Transactional
	public Application save(Application application) {
		if (application.getId()==null) {
			Application app = saveNewApplication(application);
			startBpmProcess(app);
			return app;
		} else {
			return repository.save(application);
		}
	}

	private Application saveNewApplication(Application newApplication) {
		// generate applicationNumber
		String sql = "SELECT NEXT VALUE FOR ApplicationNumber";
		Long seq = jdbcTemplate.queryForObject(sql, new Object[] {}, Long.class);

		newApplication.setApplicationNumber("APP-N-"+String.format("%05d", seq));
		newApplication.setStatus("PROCESSING");
		
		return repository.save(newApplication);
	}
	
	private String startBpmProcess(Application newApplication) {
		// invoke bpm microservice to start backend processing process
		Map<String, String> variables = new HashMap<String, String>();
		variables.put("applicationId", newApplication.getId().toString());
		StartProcessRequest startProcessRequest = new StartProcessRequest("activitiLicenceApplication", variables);
		String response = restTemplate.postForObject("http://localhost:8103/api/instances", startProcessRequest, String.class);
		return response;
	}
	
	@Getter @Setter
	class StartProcessRequest {
		private String processDefinitionKey;
		private Map<String, String> variables = new HashMap<String, String>();
		public StartProcessRequest(String processDefinitionKey, Map<String, String> variables) {
			this.processDefinitionKey = processDefinitionKey;
			this.variables = variables;
		}
	}
}
