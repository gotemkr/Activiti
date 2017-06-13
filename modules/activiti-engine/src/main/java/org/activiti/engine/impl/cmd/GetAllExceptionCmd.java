package org.activiti.engine.impl.cmd;

import java.util.List;

import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExceptionEntity;

public class GetAllExceptionCmd implements Command<List<ExceptionEntity>> {

	
	
	@Override
	public List<ExceptionEntity> execute(CommandContext commandContext) {
		return commandContext.getProcessEngineConfiguration().getExceptionDataManager().getAllExceptions();
	}

}
