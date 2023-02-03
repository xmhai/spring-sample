package com.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class IAMRunner {
	public static void run() throws Exception {
		System.out.println("*** Start Processing...");
		Map<String, String> applicationMap = new HashMap<String, String>();
		
		// load Application configuration
		ObjectMapper objectMapper = new ObjectMapper();
		NexusApplications applications = objectMapper.readValue(new File("application.json"), NexusApplications.class);
		applications.getApplications().forEach((app) -> applicationMap.put(app.getName(), app.getId()));
		System.out.println(String.format("%d applications are loaded", applicationMap.size()));
		
		// read request file
		BufferedReader reader = new BufferedReader(new FileReader("request.txt"));
		String line = reader.readLine();
		// first line is userId
		String userId = line;
		System.out.println("User ID: "+userId);
		if (userId==null || userId.isBlank()) {
			throw new Exception("Invalid request.txt: userId is missing");
		}

		List<String> applicationUuidList = new ArrayList<String>();
		int lineNo = 1;
		while (line != null) {
			line = reader.readLine();
			lineNo++;
			if (lineNo % 2 == 0) { // even row
				// get Application Name
				if (line==null || line.isBlank()) {
					System.out.println(String.format("Warning: empty application name encounter at line %d", lineNo));
					continue;
				}
				
				String applicationName = line.substring(4).trim();
				if (applicationName==null || applicationName.isBlank()) {
					System.out.println(String.format("Warning: empty application name encounter at line %d", lineNo));
					continue;
				}
	
				String applicationUuid = applicationMap.get(applicationName); 
				if (applicationUuid==null || applicationUuid.isBlank()) {
					System.out.println(String.format("Warning: application name %s not found in json file", applicationName));
					continue;
				}
	
				applicationUuidList.add(applicationUuid);
			}
		}
		reader.close();
		
		// trigger requests
		
		reader = new BufferedReader(new FileReader("curl.txt"));
		String curlCmd = reader.readLine();
		System.out.println("curl command template: "+curlCmd);
		reader.close();
		
		System.out.println("** Generate curl commands...");
		for (String applicationUuid : applicationUuidList) {
			String cmd = String.format(curlCmd, applicationUuid, userId);
			System.out.println(cmd);
			//Runtime.getRuntime().exec(cmd);
		}		
		System.out.println("** DONE ***");
	}
}
