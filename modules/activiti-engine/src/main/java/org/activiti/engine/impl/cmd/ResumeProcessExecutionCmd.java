package org.activiti.engine.impl.cmd;

import java.util.List;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExceptionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.data.ExceptionDataManager;
import org.activiti.engine.runtime.Execution;

public class ResumeProcessExecutionCmd implements Command<Void>{

	
	protected final String executionId;
	
	public ResumeProcessExecutionCmd(String executionId) {
		this.executionId = executionId;
	}
	
	@Override
	public Void execute(CommandContext commandContext) {
		if(this.executionId == null){
			throw new ActivitiIllegalArgumentException("ExecutionId cannot be null.");
		}
		
		ExecutionEntity executionEntity = commandContext.getExecutionEntityManager().findById(executionId);
		
		if (executionEntity == null) {
	      throw new ActivitiObjectNotFoundException("Cannot find execution entity for execution id '" + executionId + "'.", Execution.class);
	    }
		
		ExceptionDataManager dataManager = commandContext.getProcessEngineConfiguration().getExceptionDataManager();
		List<ExceptionEntity> exceptions = dataManager.getExceptionByExecutionId(executionId);
		
		if(exceptions == null || exceptions.isEmpty())
			throw new ActivitiException("No exception is logged with execution id: "+executionId);
		
		dataManager.deleteExceptions(exceptions);
		
	    Context.getAgenda().planContinueProcessOperation(executionEntity);
		    
		return null;
	}

}
