package org.activiti.engine.impl.persistence.entity.data;

import java.util.List;

import org.activiti.engine.impl.persistence.entity.ExceptionEntity;

public interface ExceptionDataManager extends DataManager<ExceptionEntity> {
	
	List<ExceptionEntity> getExceptionByProcessInstId(String processInstId);
	
	List<ExceptionEntity> getExceptionByExecutionId(String executionId);
	
	public void deleteExceptionByExecutionId(String executionId);
	
	public void deleteExceptionByIds(List<String> list);
	
	public void deleteExceptions(List<ExceptionEntity> list);
	
}
