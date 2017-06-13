package org.activiti.engine.impl.persistence.entity;

import java.util.List;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.CountingExecutionEntity;
import org.activiti.engine.impl.persistence.entity.data.DataManager;
import org.activiti.engine.impl.persistence.entity.data.ExceptionDataManager;

public class ExceptionEntityManagerImpl extends AbstractEntityManager<ExceptionEntity> implements ExceptionEntityManager {

	private ExceptionDataManager exceptionDataManager;

	public ExceptionEntityManagerImpl(ProcessEngineConfigurationImpl processEngineConfiguration, ExceptionDataManager dataManager) {
		super(processEngineConfiguration);
		this.exceptionDataManager = dataManager;
	}
	
	
	@Override
	public void insert(ExceptionEntity entity) {
		super.insert(entity);
		if (entity.getExecutionId() != null && isExecutionRelatedEntityCountEnabledGlobally()) {
			 CountingExecutionEntity executionEntity = (CountingExecutionEntity) getExecutionEntityManager().findById(entity.getExecutionId());
		     if (isExecutionRelatedEntityCountEnabled(executionEntity)) {
		        executionEntity.setExceptionCount(executionEntity.getExceptionCount() + 1);
		     }
		}
	}
	
	
	@Override
	public void delete(ExceptionEntity entity) {
		super.delete(entity);
		
		entity.setDeleted(true);
		
		if (entity.getExecutionId() != null && isExecutionRelatedEntityCountEnabledGlobally()) {
	      CountingExecutionEntity executionEntity = (CountingExecutionEntity) getExecutionEntityManager().findById(entity.getExecutionId());
	      if (isExecutionRelatedEntityCountEnabled(executionEntity)) {
	        executionEntity.setExceptionCount(executionEntity.getExceptionCount() - 1);
	      }
	    }
		
	}

	@Override
	public ExceptionEntity findExceptionByProcessInstanceId(String procInstId) {
		
		List<ExceptionEntity> exceptions = this.exceptionDataManager.getExceptionByProcessInstId(procInstId);
		if(exceptions == null || exceptions.isEmpty()){
			return null;
		}
		if(exceptions.size() > 1){
			throw new ActivitiException("More than one exception found for process instance with id: "+procInstId);
		}
		return exceptions.get(0);
	}

	@Override
	protected DataManager<ExceptionEntity> getDataManager() {
		return this.exceptionDataManager;
	}

}
