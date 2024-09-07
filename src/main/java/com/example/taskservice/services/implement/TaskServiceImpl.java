package com.example.taskservice.services.implement;

import com.example.taskservice.dto.TaskApplicationDTO;
import com.example.taskservice.dto.TaskCurrentApplicationDTO;
import com.example.taskservice.dto.TaskEntityDTO;
import com.example.taskservice.enums.TaskStatus;
import com.example.taskservice.exceptions.InvalidTaskException;
import com.example.taskservice.exceptions.TaskNotFoundException;
import com.example.taskservice.exceptions.TaskNotOwnedByUserException;
import com.example.taskservice.mappers.TaskMapper;
import com.example.taskservice.models.TaskEntity;
import com.example.taskservice.repositories.TaskRepository;
import com.example.taskservice.services.TaskService;
import com.example.taskservice.validations.TaskAppValidator;
import com.example.taskservice.validations.TaskCurrentAppValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.example.taskservice.mappers.TaskMapper.*;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskAppValidator taskAppValidator;

    @Autowired
    private TaskCurrentAppValidator taskCurrentAppValidator;

    // Methods Repository

    @Override
    public Mono<TaskEntity> getTaskById(Long id) {
        return taskRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new TaskNotFoundException("Task with id \"" + id + "\" was not found.")));
    }

    @Override
    public Mono<TaskEntity> getTaskByIdAndUserUsername(Long id, String userUsername) {
        return taskRepository
                .findByIdAndUserUsername(id, userUsername)
                .switchIfEmpty(Mono.error(new TaskNotOwnedByUserException("The task does not belong to you.")));
    }

    @Override
    public Flux<TaskEntity> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Flux<TaskEntity> getAllTasksByUser(String userUsername) {
        return taskRepository.findByUserUsername(userUsername);
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

    @Override
    public Flux<TaskEntityDTO> getAllTasksDTOByUser(String userUsername) {
        return toTaskDTOFlux(getAllTasksByUser(userUsername));
    }

    // Methods Controller

    @Override
    public Mono<TaskEntityDTO> createTask(Mono<TaskApplicationDTO> taskAppMono) {
        return taskAppMono
                .flatMap(this::validateTaskApp)
                .flatMap(TaskMapper::toTaskEntityMono)
                .flatMap(this::saveTask)
                .map(TaskMapper::toTaskDTO);
    }

    @Override
    public Mono<TaskEntityDTO> updateTask(Long id, TaskApplicationDTO taskApp) {
        return validateTaskApp(taskApp)
                .then(getTaskById(id))
                .flatMap(taskEntity -> setProperties(taskEntity, taskApp))
                .flatMap(this::saveTask)
                .map(TaskMapper::toTaskDTO);
    }

    @Override
    public Mono<TaskEntity> setProperties(TaskEntity taskEntity, TaskApplicationDTO taskApp) {
        taskEntity.setTitle(taskApp.title());
        taskEntity.setDescription(taskApp.description());
        taskEntity.setTaskStatus(TaskStatus.valueOf(taskApp.status().toUpperCase()));
        return Mono.just(taskEntity);
    }

    @Override
    public Mono<Void> deleteTask(Long id) {
        return getTaskById(id).flatMap(taskRepository::delete);
    }

    // Methods Controller Current

    @Override
    public Mono<TaskEntityDTO> createNewTaskCurrentUser(Mono<TaskCurrentApplicationDTO> taskCurrentAppMono, String userUsername) {
        return taskCurrentAppMono
                .flatMap(this::validateTaskCurrentApp)
                .flatMap(taskCurrentApp -> toTaskEntityMono(taskCurrentApp, userUsername))
                .flatMap(this::saveTask)
                .map(TaskMapper::toTaskDTO);
    }

    @Override
    public Mono<TaskEntityDTO> updateTaskCurrentUser(TaskCurrentApplicationDTO taskCurrentApp, Long taskId, String userUsername) {
        return validateTaskCurrentApp(taskCurrentApp)
                .then(getTaskByIdAndUserUsername(taskId, userUsername))
                .flatMap(taskEntity -> setProperties(taskEntity, taskCurrentApp))
                .flatMap(this::saveTask)
                .map(TaskMapper::toTaskDTO);
    }

    @Override
    public Mono<TaskEntity> setProperties(TaskEntity taskEntity, TaskCurrentApplicationDTO taskCurrentApp) {
        taskEntity.setTitle(taskCurrentApp.title());
        taskEntity.setDescription(taskCurrentApp.description());
        taskEntity.setTaskStatus(TaskStatus.valueOf(taskCurrentApp.status().toUpperCase()));
        return Mono.just(taskEntity);
    }

    @Override
    public Mono<Void> deleteTaskCurrentUser(Long id, String userUsername) {
        return getTaskByIdAndUserUsername(id, userUsername).flatMap(taskRepository::delete);
    }

    @Override
    public Mono<TaskApplicationDTO> validateTaskApp(TaskApplicationDTO taskApp) {
        Errors errors = new BeanPropertyBindingResult(taskApp, "taskApp");
        taskAppValidator.validate(taskApp, errors);

        if (errors.hasErrors()) {
            return Mono.error(new InvalidTaskException(errors.getFieldError().getDefaultMessage()));
        }

        return Mono.just(taskApp);
    }

    @Override
    public Mono<TaskCurrentApplicationDTO> validateTaskCurrentApp(TaskCurrentApplicationDTO taskCurrentApp) {
        Errors errors = new BeanPropertyBindingResult(taskCurrentApp, "taskCurrentApp");
        taskCurrentAppValidator.validate(taskCurrentApp, errors);

        if (errors.hasErrors()) {
            return Mono.error(new InvalidTaskException(errors.getFieldError().getDefaultMessage()));
        }

        return Mono.just(taskCurrentApp);
    }
}
