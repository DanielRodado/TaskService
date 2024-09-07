package com.example.taskservice.mappers;

import com.example.taskservice.dto.TaskApplicationDTO;
import com.example.taskservice.dto.TaskEntityDTO;
import com.example.taskservice.enums.TaskStatus;
import com.example.taskservice.models.TaskEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class TaskMapper {

    // from entity mono to entity DTO Mono

    public static Mono<TaskEntityDTO> toTaskDTOMono(Mono<TaskEntity> taskEntityMono) {
        return taskEntityMono.map(TaskMapper::toTaskDTO);
    }

    // from entity to DTO

    public static TaskEntityDTO toTaskDTO(TaskEntity taskEntity) {
        return new TaskEntityDTO(taskEntity);
    }

    // from flux to DTO Flux

    public static Flux<TaskEntityDTO> toTaskDTOFlux(Flux<TaskEntity> taskEntityFlux) {
        return taskEntityFlux.map(TaskMapper::toTaskDTO);
    }

    // from entity DTO Application to Entity Mono

    public static Mono<TaskEntity> toTaskEntityMono(TaskApplicationDTO taskApp) {
        return Mono.just(
                new TaskEntity(
                        taskApp.title(),
                        taskApp.description(),
                        TaskStatus.valueOf(taskApp.status().toUpperCase()),
                        taskApp.userUsername()
                )
        );
    }

}
