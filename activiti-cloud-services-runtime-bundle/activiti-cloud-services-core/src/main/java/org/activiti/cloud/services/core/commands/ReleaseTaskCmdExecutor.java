package org.activiti.cloud.services.core.commands;

import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.payloads.ReleaseTaskPayload;
import org.activiti.api.task.model.results.TaskResult;
import org.activiti.api.task.runtime.TaskAdminRuntime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class ReleaseTaskCmdExecutor implements CommandExecutor<ReleaseTaskPayload> {

    private TaskAdminRuntime taskAdminRuntime;
    private MessageChannel commandResults;

    @Autowired
    public ReleaseTaskCmdExecutor(TaskAdminRuntime taskAdminRuntime,
                                  MessageChannel commandResults) {
        this.taskAdminRuntime = taskAdminRuntime;
        this.commandResults = commandResults;
    }

    @Override
    public String getHandledType() {
        return ReleaseTaskPayload.class.getName();
    }

    @Override
    public void execute(ReleaseTaskPayload releaseTaskPayload) {
        Task task = taskAdminRuntime.release(releaseTaskPayload);
        TaskResult result = new TaskResult(releaseTaskPayload,
                                           task);
        commandResults.send(MessageBuilder.withPayload(result).build());
    }
}
