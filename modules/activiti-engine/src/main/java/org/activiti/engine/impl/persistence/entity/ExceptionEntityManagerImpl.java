package org.activiti.engine.impl.persistence.entity;

import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.entity.data.DataManager;
import org.activiti.engine.impl.persistence.entity.data.ExceptionDataManager;

public class ExceptionEntityManagerImpl extends AbstractEntityManager<ExceptionEntity> implements ExceptionEntityManager {

	private ExceptionDataManager exceptionDataManager;

	public ExceptionEntityManagerImpl(ProcessEngineConfigurationImpl processEngineConfiguration, ExceptionDataManager dataManager) {
		super(processEngineConfiguration);
		this.exceptionDataManager = dataManager;
	}

	@Override
	public ExceptionEntity findExceptionByProcessInstanceId(String procInstId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected DataManager<ExceptionEntity> getDataManager() {
		return this.exceptionDataManager;
	}

}
