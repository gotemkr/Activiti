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
package org.activiti.engine.test.bpmn.exception;

import java.util.List;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.impl.persistence.entity.ExceptionEntity;
import org.activiti.engine.impl.test.PluggableActivitiTestCase;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.Deployment;

public class ProcessExceptionHandlingTest extends PluggableActivitiTestCase {

  static int denominator;
  static int result;

  public static class Delegate1 implements JavaDelegate {	
    public void execute(DelegateExecution execution) {
      result = 10/denominator;
    }	
  }

  public static class Delegate2 implements JavaDelegate {
    public void execute(DelegateExecution execution) {
      denominator = 20;
    }
  }

  @Override
  protected void setUp() throws Exception {
    processEngineConfiguration.setProcessResumeEnabled(true);
  }

  @Override
  protected void tearDown() throws Exception {
    processEngineConfiguration.setProcessResumeEnabled(false);
  }

  @Deployment
  public void testExceptionLogging(){
    denominator = 0;
    ProcessInstance pi = runtimeService.startProcessInstanceByKey("process");
    ExceptionEntity exceptionEntity = runtimeService.getExceptionOfProcessInstance(pi.getId());
    assertNotNull(exceptionEntity);
    assertEquals(exceptionEntity.getProcInstId(), pi.getId()); 

    for (org.activiti.engine.repository.Deployment deployment : repositoryService.createDeploymentQuery().list()) {
      repositoryService.deleteDeployment(deployment.getId(), true);
    }
  }

  /**
   * Resume process without fixing the issue
   */
  @Deployment (resources = "org/activiti/engine/test/bpmn/exception/ProcessExceptionHandlingTest.testExceptionLogging.bpmn20.xml")
  public void testResumeProcessWithException(){
    denominator = 0;
    ProcessInstance pi = runtimeService.startProcessInstanceByKey("process");
    String procInstId = pi.getId();
    ExceptionEntity exceptionEntity = runtimeService.getExceptionOfProcessInstance(procInstId);
    String exceptionId = exceptionEntity.getId();
    assertNotNull(exceptionEntity);
    assertEquals(exceptionEntity.getProcInstId(), procInstId); 
    runtimeService.resumeProcessInstance(exceptionEntity.getExecutionId());
    exceptionEntity = runtimeService.getExceptionOfProcessInstance(procInstId);
    assertNotNull(exceptionEntity);
    assertNotSame(exceptionId, exceptionEntity.getId());

    for (org.activiti.engine.repository.Deployment deployment : repositoryService.createDeploymentQuery().list()) {
      repositoryService.deleteDeployment(deployment.getId(), true);
    }
  }


  /**
   * Resume process after fixing the issue
   */
  @Deployment (resources = "org/activiti/engine/test/bpmn/exception/ProcessExceptionHandlingTest.testExceptionLogging.bpmn20.xml")
  public void testResumeProcess(){
    denominator = 0;
    ProcessInstance pi = runtimeService.startProcessInstanceByKey("process");
    String procInstId = pi.getId();
    ExceptionEntity exceptionEntity = runtimeService.getExceptionOfProcessInstance(procInstId);
    assertNotNull(exceptionEntity);
    assertEquals(exceptionEntity.getProcInstId(), procInstId); 

    denominator = 10;
    runtimeService.resumeProcessInstance(exceptionEntity.getExecutionId());
    exceptionEntity = runtimeService.getExceptionOfProcessInstance(procInstId);
    assertNull(exceptionEntity);
    assertEquals(result, 1);
    assertEquals(denominator, 20);

    for (org.activiti.engine.repository.Deployment deployment : repositoryService.createDeploymentQuery().list()) {
      repositoryService.deleteDeployment(deployment.getId(), true);
    }
  }


  /**
   * list exceptions 
   */
  @Deployment (resources = "org/activiti/engine/test/bpmn/exception/ProcessExceptionHandlingTest.testExceptionLogging.bpmn20.xml")
  public void testListException(){
    denominator = 0;
    ProcessInstance pi = runtimeService.startProcessInstanceByKey("process");
    String procInstId = pi.getId();

    List<ExceptionEntity> exceptions = runtimeService.getAllExceptionEntities();		
    assertNotNull(exceptions);
    assertEquals(exceptions.size(), 1);

    ExceptionEntity exceptionEntity = exceptions.get(0);
    assertEquals(procInstId, exceptionEntity.getProcInstId());

    denominator = 10;
    runtimeService.resumeProcessInstance(exceptionEntity.getExecutionId());
    exceptions = runtimeService.getAllExceptionEntities();
    assertEquals(exceptions.size(), 0);

    for (org.activiti.engine.repository.Deployment deployment : repositoryService.createDeploymentQuery().list()) {
      repositoryService.deleteDeployment(deployment.getId(), true);
    }
  }

  /**
   * get exception by process instance id
   */
  @Deployment (resources = "org/activiti/engine/test/bpmn/exception/ProcessExceptionHandlingTest.testExceptionLogging.bpmn20.xml")
  public void testGetException(){
    denominator = 0;
    ProcessInstance pi = runtimeService.startProcessInstanceByKey("process");
    String procInstId = pi.getId();

    ExceptionEntity exception = runtimeService.getExceptionOfProcessInstance(procInstId);
    assertNotNull(exception);
    assertEquals(procInstId, exception.getProcInstId());

    denominator = 10;
    runtimeService.resumeProcessInstance(exception.getExecutionId());
    exception = runtimeService.getExceptionOfProcessInstance(procInstId);
    assertNull(exception);

    for (org.activiti.engine.repository.Deployment deployment : repositoryService.createDeploymentQuery().list()) {
      repositoryService.deleteDeployment(deployment.getId(), true);
    }		
  }
}