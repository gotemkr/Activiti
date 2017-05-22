package org.activiti.engine;

import org.activiti.engine.impl.persistence.entity.ExceptionEntity;

public class ActivitiLoggableException extends ActivitiException {

	/**
	 * Runtime exception which can hold ExceptionEntity object, which can be saved into the DB later  
	 */
	private static final long serialVersionUID = 1L;
	private ExceptionEntity exceptionEntity;
	

	public ActivitiLoggableException(String message) {
		super(message);
	}
	
	public ActivitiLoggableException(String message, ExceptionEntity exceptionEntity, Throwable cause) {
		super(message, cause);
		this.exceptionEntity = exceptionEntity;
	}

	public ExceptionEntity getExceptionEntity() {
		return exceptionEntity;
	}
	
	public void setExceptionEntity(ExceptionEntity exceptionEntity) {
		this.exceptionEntity = exceptionEntity;
	}
	

}
