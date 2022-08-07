package com.lin.example.batch.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// This controller is used to demonstrate how to trigger a job by a HTTP request
// As traditionally job will be invoked from command line, so comment the RestController out 
//@RestController
public class JobController {
	@RequestMapping("/")
	public String index(HttpServletRequest request, HttpServletResponse response) {
		return "Console Batch Job is running... ";
	}

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job job;

    @RequestMapping("/run")
    public String handle() throws Exception{
		JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters();
    	
        jobLauncher.run(job, jobParameters);
		return "Console Batch Job is triggered... ";
    }
}
