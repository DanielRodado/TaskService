package com.example.taskservice.dto;

import com.example.taskservice.enums.TaskStatus;
import com.example.taskservice.models.TaskEntity;

public class TaskEntityDTO {

    private final Long id;

    private final String title, description;

    private final TaskStatus taskStatus;

    public TaskEntityDTO(TaskEntity taskEntity) {
        this.id = taskEntity.getId();
        this.title = taskEntity.getTitle();
        this.description = taskEntity.getDescription();
        this.taskStatus = taskEntity.getTaskStatus();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public TaskStatus getStatus() {
        return taskStatus;
    }
}
