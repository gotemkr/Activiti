package org.activiti.engine.impl.cmd;

import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExceptionEntity;

public class GetExceptionEntityOfProcessInstanceCmd implements Command<ExceptionEntity> {

	private String processInstanceId;
	
	public GetExceptionEntityOfProcessInstanceCmd(String processInstanceId) {
		super();
		this.processInstanceId = processInstanceId;
	}
	
	@Override
	public ExceptionEntity execute(CommandContext commandContext) {
		return commandContext.getProcessEngineConfiguration().getExceptionEntityManager().findExceptionByProcessInstanceId(processInstanceId);
	
	}

}
