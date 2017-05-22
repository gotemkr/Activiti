package org.activiti.engine.impl.persistence.entity;

public interface ExceptionEntity extends Entity{
	
	void setProcInstId(String procInstId);
	
	void setExecutionId(String executionId);
	
	void setExceptionActId(String exceptionActId);
	
	void setExceptionMessage(String exceptionMessage);
	
	void setExceptionDetails(String exceptionDetails);
	
	String getProcInstId();
	
	String getExecutionId();
	
	String getExceptionActId();
	
	String getExceptionMessage();
	
	String getExceptionDetails();

	void setExceptionDetails(Throwable throwable);
	
	
}
