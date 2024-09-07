package com.example.taskservice.services;

import com.example.taskservice.dto.TaskApplicationDTO;
import com.example.taskservice.dto.TaskEntityDTO;
import com.example.taskservice.models.TaskEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TaskService {

    // Methods Repository

    Mono<TaskEntity> getTaskById(Long id);

    Flux<TaskEntity> getAllTasks();

    Flux<TaskEntity> getAllTasksByUser(String userUsername);

    Mono<TaskEntity> saveTask(TaskEntity taskEntity);

    // Repository methods returning a DTO

    Mono<TaskEntityDTO> getTaskDTOById(Long id);

    Flux<TaskEntityDTO> getAllTasksDTO();

    Flux<TaskEntityDTO> getAllTasksDTOByUser(String userUsername);

    // Methods Controller

    Mono<TaskEntityDTO> createTask(Mono<TaskApplicationDTO> taskAppMono);

    Mono<TaskEntityDTO> updateTask(Long id, TaskApplicationDTO taskApp);

    Mono<TaskEntity> setProperties(TaskEntity taskEntity, TaskApplicationDTO taskApp);

    Mono<Void> deleteTask(Long id);

    // validations

    Mono<TaskApplicationDTO> validateTaskApp(TaskApplicationDTO taskApp);

}
