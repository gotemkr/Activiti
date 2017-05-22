package org.activiti.engine.impl.persistence.entity.data.impl;

import java.util.List;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.cfg.PerformanceSettings;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.entity.ExceptionEntity;
import org.activiti.engine.impl.persistence.entity.ExceptionEntityImpl;
import org.activiti.engine.impl.persistence.entity.data.AbstractDataManager;
import org.activiti.engine.impl.persistence.entity.data.ExceptionDataManager;

public class MybatisExceptionDataManager extends AbstractDataManager<ExceptionEntity> implements ExceptionDataManager {

	private PerformanceSettings performanceSettings;

	public MybatisExceptionDataManager(ProcessEngineConfigurationImpl processEngineConfiguration) {
		super(processEngineConfiguration);
	    this.performanceSettings = processEngineConfiguration.getPerformanceSettings();
	}

	@Override
	public ExceptionEntity create() {
		return ExceptionEntityImpl.createEmpty();
	}

	@Override
	public ExceptionEntity findById(String entityId) {
	      return super.findById(entityId);
	}



	@Override
	public List<ExceptionEntity> getExceptionByProcessInstId(String processInstId) {
		return getList("selectExceptionByProcInstId", processInstId, null, false);
	}

	@Override
	public Class<? extends ExceptionEntity> getManagedEntityClass() {
		return ExceptionEntityImpl.class;
	}

	@Override
	public void deleteExceptionByExecutionId(String executionId) {
		getDbSqlSession().delete("deleteExceptionByExecutionId", executionId, ExceptionEntityImpl.class);
		//getDbSqlSession().flush();
	}

	@Override
	public List<ExceptionEntity> getExceptionByExecutionId(String executionId) {
		return getList("selectExceptionByExecutionId", executionId, null, false);
	}

	@Override
	public void deleteExceptionByIds(List<String> list) {
		getDbSqlSession().delete("deleteExceptionByIds", list, ExceptionEntityImpl.class);
	}

	@Override
	public void deleteExceptions(List<ExceptionEntity> list) {
		if(list == null || list.isEmpty())
			throw new ActivitiException("ExceptionEntity list to be deleted is empty.");
		getDbSqlSession().delete("deleteExceptions", list, ExceptionEntityImpl.class);
	}

}
