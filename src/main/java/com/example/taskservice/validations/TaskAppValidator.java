package com.example.taskservice.validations;

import com.example.taskservice.dto.TaskApplicationDTO;
import com.example.taskservice.enums.TaskStatus;
import com.example.taskservice.models.TaskEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class TaskAppValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return TaskEntity.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        TaskApplicationDTO taskApp = (TaskApplicationDTO) target;

        isValidTitle(taskApp.title(), errors);
        isValidDescription(taskApp.description(), errors);
        isValidStatus(taskApp.status(), errors);
        isValidTaskStatus(taskApp.status().toUpperCase(), errors);
    }

    public void isValidTitle(String title, Errors errors) {
        if (title == null || title.isBlank()) {
            errors.rejectValue("title", "taskApp.title.empty", "Title is required.");
        }
    }

    public void isValidDescription(String description, Errors errors) {
        if (description == null || description.isBlank()) {
            errors.rejectValue("description", "taskApp.description.empty", "Description is required.");
        }
    }

    public void isValidStatus(String status, Errors errors) {
        if (status == null || status.isBlank()) {
            errors.rejectValue("status", "taskApp.status.empty", "Status is required.");
        }
    }

    public void isValidTaskStatus(String status, Errors errors) {
        try {
            TaskStatus.valueOf(status);
        } catch (Exception ignore) {
            errors.rejectValue("status", "taskApp.status.invalid", "Status is invalid.");
        }
    }

}
