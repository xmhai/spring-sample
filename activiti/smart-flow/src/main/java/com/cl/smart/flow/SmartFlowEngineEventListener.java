package com.cl.smart.flow;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl;
import org.activiti.engine.impl.persistence.entity.TaskEntityImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cl.smart.flow.service.ProcessInstanceHistoryService;

@Component
public class SmartFlowEngineEventListener implements ActivitiEventListener {
	@Autowired
	ProcessInstanceHistoryService processInstanceHistoryService;

	@Autowired
	protected RepositoryService repositoryService;
	
	@Override
	public void onEvent(ActivitiEvent event) {
		switch (event.getType()) {
		case PROCESS_STARTED:
		case PROCESS_COMPLETED:
			// PROCESS_STARTED happened after TASK_CREATED which could confuse user
			// so it would be better not to include this event in history 
			//ProcessDefinition processDefinition = getProcessDefinition(event.getProcessDefinitionId());
			//processInstanceHistoryService.saveProcessActivity(event.getType(), event, processDefinition);
			break;
			
		case TASK_CREATED:
		case TASK_ASSIGNED:
		case TASK_COMPLETED:
			TaskEntityImpl taskEntity = getTaskEntity(event);
			processInstanceHistoryService.saveTaskActivity(event.getType(), taskEntity);
			break;
	
		default:
			// do nothing
		}
	}

	@Override
	public boolean isFailOnException() {
		return false;
	}

	/*
	private ProcessDefinition getProcessDefinition(String processDefinitionId) {
		return repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
	}
	*/
	
	private TaskEntityImpl getTaskEntity(ActivitiEvent event) {
	    ActivitiEntityEventImpl activitiEntityEventImpl = (ActivitiEntityEventImpl) event;
	    if (activitiEntityEventImpl==null)
	    	return null;
	    	
	    Object entity = activitiEntityEventImpl.getEntity();

	    if (!(entity instanceof TaskEntityImpl)) {
	        return null;
	    }

	    return (TaskEntityImpl) entity;		
	}
}
