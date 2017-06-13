package org.activiti.app.rest.runtime;

import java.util.List;

import javax.inject.Inject;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.persistence.entity.ExceptionEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProcessExceptionResource {
	
	@Inject
	protected RuntimeService runtimeService;
	
	@RequestMapping(value = "/rest/process-instances/list/exceptions", method = RequestMethod.GET, produces = "application/json")
	public List<ExceptionEntity> listExceptions() {		
		return runtimeService.getAllExceptionEntities();
	}
	
	@RequestMapping(value = "/rest/process-instances/{processInstanceId}/exception", method = RequestMethod.GET)
    public ExceptionEntity getProcessDefinitions(@PathVariable String processInstanceId) {
	    
	    return runtimeService.getExceptionOfProcessInstance(processInstanceId);
    }
}
