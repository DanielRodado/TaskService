package com.example.taskservice.controller;

import com.example.taskservice.dto.TaskApplicationDTO;
import com.example.taskservice.dto.TaskEntityDTO;
import com.example.taskservice.services.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/tasks")
@Tag(name = "Task Operations", description = "Operations related to task management.")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Operation(
            summary = "Retrieve task by ID",
            description = "Fetches the User details for the given task ID.",
            parameters = @Parameter(
                    name = "taskId",
                    description = "The ID of the user to retrieve",
                    required = true,
                    example = "1"
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Task retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TaskEntityDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Task not found",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @GetMapping("/{taskId}")
    public Mono<TaskEntityDTO> getTaskDTOById(@PathVariable Long taskId) {
        return taskService.getTaskDTOById(taskId);
    }

    @Operation( summary = "Retrieve all tasks", description = "Fetches a set of all tasks.")
    @ApiResponse(
            responseCode = "200",
            description = "Tasks retrieved successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TaskEntityDTO.class))
    )
    @GetMapping
    public Flux<TaskEntityDTO> getAllTasksDTO() {
        return taskService.getAllTasksDTO();
    }

    @Operation(
            summary = "Retrieve tasks by user",
            description = "Gets the tasks of a user by the given user username.",
            parameters = @Parameter(
                    name = "username",
                    description = "The username of the user to whom the tasks belong.",
                    required = true,
                    example = "john"
            )
    )
    @ApiResponse(
                    responseCode = "200",
                    description = "Tasks retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TaskEntityDTO.class))
    )
    @GetMapping("/user/{username}")
    public Flux<TaskEntityDTO> getAllTaskFromUser(@PathVariable String username) {
        return taskService.getAllTasksDTOByUser(username);
    };

    @Operation(
            summary = "Create a new task",
            description = "Create a new task with the provided details.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "The data to create a new task.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = TaskApplicationDTO.class),
                            examples =  @ExampleObject(
                                    name = "Task create example",
                                    summary = "Task create details",
                                    description = "Example data for create a new task.",
                                    value = "{ \"title\": \"Example Task\", \"description\": \"This is an example task\", \"status\": \"PENDING\", \"userId\": \"1\"}"
                            )
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Task successfully created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TaskEntityDTO.class),
                            examples = @ExampleObject(
                                    name = "Task Created Example",
                                    summary = "Created task",
                                    description = "Example of the response when a new task is successfully created.",
                                    value = "{ \"id\": 1, \"title\": \"Example Task\", \"description\": \"This is an example task\", \"status\": \"PENDING\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Invalid input data",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<TaskEntityDTO> createTask(@RequestBody Mono<TaskApplicationDTO> taskAppMono) {
        return taskService.createTask(taskAppMono);
    }

    @Operation(
            summary = "Update an existing task",
            description = "Updates the task identified by the given ID with the data provided.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "The data to update an task.",
                    content = @Content(
                            mediaType = "application/json",
                            examples =  @ExampleObject(
                                    name = "Task update example",
                                    summary = "Task update details",
                                    description = "Example data for update an task.",
                                    value = "{ \"title\": \"Example Task\", \"description\": \"This is an example task\", \"status\": \"PENDING\"}"
                            )
                    )
            ),
            parameters = @Parameter(
                    name = "taskId",
                    description = "The ID of the task to update.",
                    required = true,
                    example = "1"
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Task updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TaskEntityDTO.class),
                            examples = @ExampleObject(
                                    name = "Task updated Example",
                                    summary = "Updated task",
                                    description = "Example of response when a task is successfully updated.",
                                    value = "{ \"id\": 1, \"title\": \"Example Task\", \"description\": \"This is an example task\", \"status\": \"PENDING\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Task not found",
                    content = @Content(schema = @Schema(hidden = true))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Invalid input data",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @PutMapping("/{taskId}")
    public Mono<TaskEntityDTO> updateTask(@PathVariable Long taskId, @RequestBody TaskApplicationDTO taskApp) {
        return taskService.updateTask(taskId, taskApp);
    }

    @Operation(
            summary = "Delete an existing task",
            description = "Delete the task identified by the given ID.",
            parameters = @Parameter(
                    name = "taskId",
                    description = "The ID of the task to delete.",
                    required = true,
                    example = "1"
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Task updated successfully",
                    content = @Content(schema = @Schema(hidden = true))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Task not found",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @DeleteMapping("/{taskId}")
    public Mono<ResponseEntity<String>> deleteTask(@PathVariable Long taskId) {
        return taskService.deleteTask(taskId).then(Mono.just(ResponseEntity.noContent().build()));
    }

}
