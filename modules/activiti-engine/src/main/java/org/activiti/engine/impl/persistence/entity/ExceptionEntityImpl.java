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
package org.activiti.engine.impl.persistence.entity;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Anoop Verma
 */
public class ExceptionEntityImpl extends AbstractEntity  implements ExceptionEntity, Serializable {

  private static final long serialVersionUID = 1L;

  private String procInstId;
  private String exceptionActId;
  private String exceptionMessage;
  private String exceptionDetails;
  private String executionId;

  @Override
  public Object getPersistentState() {
    Map<String, Object> persistentState = new HashMap<String, Object>();
    persistentState.put("id", id);
    persistentState.put("procInstId", procInstId);
    persistentState.put("executionId", executionId);
    persistentState.put("exceptionActId", exceptionActId);
    persistentState.put("exceptionMessage", exceptionMessage);
    persistentState.put("exceptionDetails", exceptionDetails);	
    return persistentState;
  }

  @Override
  public String getProcInstId() {
    return this.procInstId;
  }

  @Override
  public String getExceptionActId() {
    return this.exceptionActId;
  }

  @Override
  public String getExceptionMessage() {
    return this.exceptionMessage;
  }

  @Override
  public String getExceptionDetails() {
    return this.exceptionDetails;
  }

  @Override
  public void setProcInstId(String procInstId) {
    this.procInstId = procInstId;
  }

  @Override
  public void setExceptionActId(String exceptionActId) {
    this.exceptionActId = exceptionActId;
  }

  @Override
  public void setExceptionMessage(String exceptionMessage) {
    this.exceptionMessage = exceptionMessage;
  }

  @Override
  public void setExceptionDetails(String exceptionDetails) {
    this.exceptionDetails = exceptionDetails;
  }

  public static ExceptionEntity createEmpty() {
    return new ExceptionEntityImpl();
  }

  @Override
  public void setExceptionDetails(Throwable throwable) {
    StringWriter sw = new StringWriter();
    throwable.printStackTrace(new PrintWriter(sw));
    this.setExceptionDetails(sw.toString());
  }

  @Override
  public void setExecutionId(String executionId) {
    this.executionId = executionId;
  }

  @Override
  public String getExecutionId() {
    return this.executionId;
  }
}
