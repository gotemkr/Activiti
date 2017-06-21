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
package org.activiti.engine.impl.cmd;

import java.util.List;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExceptionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.data.ExceptionDataManager;
import org.activiti.engine.runtime.Execution;

/**
 * @author Anoop Verma
 */
public class ResumeProcessExecutionCmd implements Command<Void>{

  protected final String executionId;

  public ResumeProcessExecutionCmd(String executionId) {
    this.executionId = executionId;
  }

  @Override
  public Void execute(CommandContext commandContext) {
    if(this.executionId == null){
      throw new ActivitiIllegalArgumentException("ExecutionId cannot be null.");
    }

    ExecutionEntity executionEntity = commandContext.getExecutionEntityManager().findById(executionId);
    if (executionEntity == null) {
      throw new ActivitiObjectNotFoundException("Cannot find execution entity for execution id '" + executionId + "'.", Execution.class);
    }

    ExceptionDataManager dataManager = commandContext.getProcessEngineConfiguration().getExceptionDataManager();
    List<ExceptionEntity> exceptions = dataManager.getExceptionByExecutionId(executionId);

    if(exceptions == null || exceptions.isEmpty()){
      throw new ActivitiException("No exception is logged with execution id: "+executionId);
    }

    dataManager.deleteExceptions(exceptions);
    Context.getAgenda().planContinueProcessOperation(executionEntity);
    return null;
  }
}
