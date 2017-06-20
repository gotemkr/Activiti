/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.activiti.engine.impl.persistence.entity.data.impl;

import java.util.List;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.entity.ExceptionEntity;
import org.activiti.engine.impl.persistence.entity.ExceptionEntityImpl;
import org.activiti.engine.impl.persistence.entity.data.AbstractDataManager;
import org.activiti.engine.impl.persistence.entity.data.ExceptionDataManager;

/**
 * @author Anoop Verma
 */
public class MybatisExceptionDataManager extends AbstractDataManager<ExceptionEntity> implements ExceptionDataManager {

	public MybatisExceptionDataManager(ProcessEngineConfigurationImpl processEngineConfiguration) {
		super(processEngineConfiguration);
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

	@Override
	public List<ExceptionEntity> getAllExceptions() {
		return getList("selectAllException", null, null, false);
	}
}
