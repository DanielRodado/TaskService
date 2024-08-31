package com.example.taskservice.controller;

import com.example.taskservice.dto.TaskApplicationDTO;
import com.example.taskservice.dto.TaskEntityDTO;
import com.example.taskservice.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/{taskId}")
    public Mono<TaskEntityDTO> getTaskDTOById(@PathVariable Long taskId) {
        return taskService.getTaskDTOById(taskId);
    }

    @GetMapping
    public Flux<TaskEntityDTO> getAllTasksDTO() {
        return taskService.getAllTasksDTO();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<TaskEntityDTO> createTask(@RequestBody Mono<TaskApplicationDTO> taskAppMono) {
        return taskService.createTask(taskAppMono);
    }

    @PutMapping("/{taskId}")
    public Mono<TaskEntityDTO> updateTask(@PathVariable Long taskId, @RequestBody TaskApplicationDTO taskApp) {
        return taskService.updateTask(taskId, taskApp);
    }

    @DeleteMapping("/{taskId}")
    public Mono<ResponseEntity<String>> deleteTask(@PathVariable Long taskId) {
        return taskService.deleteTask(taskId).then(Mono.just(ResponseEntity.noContent().build()));
    }

}
