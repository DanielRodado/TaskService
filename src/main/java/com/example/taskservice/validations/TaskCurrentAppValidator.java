package com.example.taskservice.validations;

import com.example.taskservice.dto.TaskCurrentApplicationDTO;
import com.example.taskservice.enums.TaskStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class TaskCurrentAppValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return TaskCurrentApplicationDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        TaskCurrentApplicationDTO taskCurrentApp = (TaskCurrentApplicationDTO) target;

        isValidTitle(taskCurrentApp.title(), errors);
        isValidDescription(taskCurrentApp.description(), errors);
        isValidStatus(taskCurrentApp.status(), errors);
        isValidTaskStatus(taskCurrentApp.status().toUpperCase(), errors);
    }

    public void isValidTitle(String title, Errors errors) {
        if (title == null || title.isBlank()) {
            errors.rejectValue("title", "taskCurrentApp.title.empty", "Title is required.");
        }
    }

    public void isValidDescription(String description, Errors errors) {
        if (description == null || description.isBlank()) {
            errors.rejectValue("description", "taskCurrentApp.description.empty", "Description is required.");
        }
    }

    public void isValidStatus(String status, Errors errors) {
        if (status == null || status.isBlank()) {
            errors.rejectValue("status", "taskCurrentApp.status.empty", "Status is required.");
        }
    }

    public void isValidTaskStatus(String status, Errors errors) {
        try {
            TaskStatus.valueOf(status);
        } catch (Exception ignore) {
            errors.rejectValue("status", "taskCurrentApp.status.invalid", "Status is invalid.");
        }
    }
}
