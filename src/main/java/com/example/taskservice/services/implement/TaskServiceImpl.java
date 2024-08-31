package com.example.taskservice.services.implement;

import com.example.taskservice.dto.TaskApplicationDTO;
import com.example.taskservice.dto.TaskEntityDTO;
import com.example.taskservice.enums.TaskStatus;
import com.example.taskservice.exceptions.TaskNotFoundException;
import com.example.taskservice.mappers.TaskMapper;
import com.example.taskservice.models.TaskEntity;
import com.example.taskservice.repositories.TaskRepository;
import com.example.taskservice.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.example.taskservice.mappers.TaskMapper.*;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    // Methods Repository

    @Override
    public Mono<TaskEntity> getTaskById(Long id) {
        return taskRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new TaskNotFoundException("Task with id \"" + id + "\" was not found.")));
    }

    @Override
    public Flux<TaskEntity> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Mono<TaskEntity> saveTask(TaskEntity taskEntity) {
        return taskRepository.save(taskEntity);
    }

    // Repository methods returning a DTO

    @Override
    public Mono<TaskEntityDTO> getTaskDTOById(Long id) {
        return toTaskDTOMono(getTaskById(id));
    }

    @Override
    public Flux<TaskEntityDTO> getAllTasksDTO() {
        return toTaskDTOFlux(getAllTasks());
    }

    // Methods Controller

    @Override
    public Mono<TaskEntityDTO> createTask(Mono<TaskApplicationDTO> taskAppMono) {
        return toTaskEntityMono(taskAppMono)
                .flatMap(this::saveTask)
                .map(TaskMapper::toTaskDTO);
    }

    @Override
    public Mono<TaskEntityDTO> updateTask(Long id, TaskApplicationDTO taskApp) {
        return getTaskById(id)
                .flatMap(taskEntity -> setProperties(taskEntity, taskApp))
                .flatMap(this::saveTask)
                .map(TaskMapper::toTaskDTO);
    }

    @Override
    public Mono<TaskEntity> setProperties(TaskEntity taskEntity, TaskApplicationDTO taskApp) {
        taskEntity.setTitle(taskApp.title());
        taskEntity.setDescription(taskApp.description());
        taskEntity.setTaskStatus(TaskStatus.valueOf(taskApp.taskStatus()));
        taskEntity.setUserId(taskApp.userId());
        return Mono.just(taskEntity);
    }

    @Override
    public Mono<Void> deleteTask(Long id) {
        return getTaskById(id).flatMap(taskRepository::delete);
    }
}
