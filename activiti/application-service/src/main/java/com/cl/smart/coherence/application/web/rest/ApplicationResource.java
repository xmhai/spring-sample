package com.cl.smart.coherence.application.web.rest;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cl.smart.coherence.application.domain.Application;
import com.cl.smart.coherence.application.service.ApplicationService;

@RestController
@CrossOrigin(origins = "http://localhost:9000")
public class ApplicationResource {
	@Autowired
	ApplicationService service;

	@GetMapping("/applications")
	public List<Application> findAll(@RequestParam(required=false) String ids) {
		if (ids==null) {
			return service.findAll();
		} else {
			List<Long> idList = Arrays.asList(ids.split(",")).stream()
			        .map(id -> Long.parseLong(id))
			        .collect(Collectors.toList());

			return service.findAllById(idList);
		}
	}

	@PostMapping("/applications")
	public Application save(@RequestBody Application newApplication) {
		return service.save(newApplication);
	}

	@PutMapping("/applications")
	public Application update(@RequestBody Map<String, String> updates) {
		String id = updates.get("id");
		Application newApplication = service.findById(Long.parseLong(id)).get();
		for (Map.Entry<String, String> entry : updates.entrySet()) {
		    String key = entry.getKey();
		    if ("status".equals(key)) {
			    newApplication.setStatus(entry.getValue());
		    }
		}		
		return service.save(newApplication);
	}

	@GetMapping("/applications/{id}")
	public Optional<Application> findById(@PathVariable Long id) {
		return service.findById(id);
	}
}
