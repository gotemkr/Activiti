package org.activiti.engine.impl.persistence.entity;

public interface ExceptionEntityManager  extends EntityManager<ExceptionEntity> {
	
	ExceptionEntity findExceptionByProcessInstanceId(String procInstId);
		
}
