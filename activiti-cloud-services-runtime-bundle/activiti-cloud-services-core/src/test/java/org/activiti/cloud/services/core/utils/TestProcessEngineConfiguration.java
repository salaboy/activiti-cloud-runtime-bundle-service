/*
 * Copyright 2018 Alfresco, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.activiti.cloud.services.core.utils;

import org.activiti.engine.*;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.activiti.validation.ProcessValidator;
import org.activiti.validation.ProcessValidatorImpl;
import org.activiti.validation.validator.ValidatorSetFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.MessageChannel;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * Test context configuration for integration tests over Activiti process engine
 */
@Configuration
@ComponentScan({
        "org.activiti.engine",
        "org.activiti.image",
        "org.activiti.core.common.spring.security",
        "org.activiti.core.common.spring.identity",
        "org.activiti.core.common.spring.connector",
        "org.activiti.cloud.services.events.listeners",
        "org.activiti.cloud.services.events.converter",
        "org.activiti.cloud.services.events.configuration",
        "org.activiti.cloud.services.core",
        "org.activiti.cloud.services.core.pageable",
        "org.activiti.cloud.services.core.utils"


})
@EnableAutoConfiguration
public class TestProcessEngineConfiguration {

    @Autowired
    private ProcessEngine processEngine;

    @Bean
    public ProcessEngine processEngine() {
        return ProcessEngineConfiguration
                .createStandaloneInMemProcessEngineConfiguration()
                .setDatabaseSchemaUpdate(ProcessEngineConfigurationImpl.DB_SCHEMA_UPDATE_DROP_CREATE)
                .buildProcessEngine();
    }

    @Bean
    public RuntimeService runtimeService() {
        return processEngine.getRuntimeService();
    }

    @Bean
    public RepositoryService repositoryService() {
        return processEngine.getRepositoryService();
    }

    @Bean
    public TaskService taskService() {
        return processEngine.getTaskService();
    }

    @Bean
    public ProcessDiagramGenerator processDiagramGenerator() {
        return new DefaultProcessDiagramGenerator();
    }

    @Bean
    public ProcessValidator processValidator() {
        ProcessValidatorImpl processValidator = new ProcessValidatorImpl();
        processValidator.addValidatorSet(new ValidatorSetFactory().createActivitiExecutableProcessValidatorSet());
        return processValidator;
    }

    @Bean
    public MessageChannel commandResults() {
        return new MockMessageChannel();
    }

    @Bean
    public UserDetailsService myUserDetailsService() {
        return new InMemoryUserDetailsManager();
    }
}
