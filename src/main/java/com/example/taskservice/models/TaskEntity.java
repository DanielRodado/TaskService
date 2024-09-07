package com.example.taskservice.models;

import com.example.taskservice.enums.TaskStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("tasks")
public class TaskEntity {

    @Id
    private Long id;

    private String title, description, userUsername;

    @Column("status")
    private TaskStatus taskStatus;

    public TaskEntity(String title, String description, TaskStatus taskStatus, String userUsername) {
        this.title = title;
        this.description = description;
        this.taskStatus = taskStatus;
        this.userUsername = userUsername;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getUserUsername() {
        return userUsername;
    }

    public void setUserUsername(String userUsername) {
        this.userUsername = userUsername;
    }
}
