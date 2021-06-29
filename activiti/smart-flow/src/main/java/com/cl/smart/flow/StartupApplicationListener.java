package com.cl.smart.flow;

import org.activiti.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StartupApplicationListener {
	@Autowired
	RuntimeService runtimeService;
 
	@Autowired
	SmartFlowEngineEventListener smartFlowEngineEventListener;
	
	@EventListener 
    public void onApplicationEvent(ContextRefreshedEvent event) {
		runtimeService.addEventListener(smartFlowEngineEventListener);
    }
}
