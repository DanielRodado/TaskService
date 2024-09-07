package com.example.taskservice.controller;

import com.example.taskservice.dto.TaskCurrentApplicationDTO;
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
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.example.taskservice.utils.HeaderUtil.extractUsername;

@RestController
@RequestMapping("/api/tasks/current")
@Tag(name = "Task Operations", description = "Operations related to task management.")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Operation(
            summary = "Get tasks for authenticated user",
            description = "Retrieves the tasks associated with the currently authenticated user."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved the tasks for the authenticated user.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TaskEntityDTO.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access denied.",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @GetMapping
    public Flux<TaskEntityDTO> getTasksFromCurrentUser(ServerWebExchange serverWebExchange) {
        String username = extractUsername(serverWebExchange);
        return taskService.getAllTasksDTOByUser(username);
    }


    @Operation(
            summary = "Create a new task for authenticated user",
            description = "Creates a new task associated with the currently authenticated user.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "The data to create a new task.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = TaskCurrentApplicationDTO.class),
                            examples =  @ExampleObject(
                                    name = "Task create example",
                                    summary = "Task create details",
                                    description = "Example data for create a new task.",
                                    value = "{ \"title\": \"Example Task\", \"description\": \"This is an example task\", \"status\": \"PENDING\"}"
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
    public Mono<TaskEntityDTO> createTaskFromCurrentUser(@RequestBody Mono<TaskCurrentApplicationDTO> taskCurrentAppMono,
                                                         @Parameter(hidden = true) ServerWebExchange serverWebExchange) {
        String userUsername = extractUsername(serverWebExchange);
        return taskService.createNewTaskCurrentUser(taskCurrentAppMono, userUsername);
    }


    @Operation(
            summary = "Update a task by ID for authenticated user",
            description = "\"Updates an existing task identified by its ID, associated with the currently authenticated user.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "The data to update an task.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = TaskCurrentApplicationDTO.class),
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
                    responseCode = "200",
                    description = "Task updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TaskEntityDTO.class),
                            examples = @ExampleObject(
                                    name = "Task updated Example",
                                    summary = "Updated task",
                                    description = "Example of the response when a new task is successfully updated.",
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
    @PutMapping("/{taskId}")
    public Mono<TaskEntityDTO> updateTaskFromCurrentUser(@RequestBody TaskCurrentApplicationDTO taskCurrentApp,
                                                         @PathVariable Long taskId,
                                                         @Parameter(hidden = true) ServerWebExchange serverWebExchange) {
        String userUsername = extractUsername(serverWebExchange);
        return taskService.updateTaskCurrentUser(taskCurrentApp, taskId, userUsername);
    }

    @Operation(
            summary = "Delete a task by ID for authenticated user",
            description = "Deletes an existing task identified by its ID, associated with the currently authenticated user.",
            parameters = @Parameter(
                    name = "id",
                    description = "The ID of the task to delete.",
                    required = true,
                    example = "1"
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Task deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "The task does not belong to you"
            )
    })
    @DeleteMapping("/{taskId}")
    public Mono<ResponseEntity<String>> deleteTaskFromCurrentUser(@PathVariable Long taskId,
                                                                  @Parameter(hidden = true) ServerWebExchange serverWebExchange) {
        String userUsername = extractUsername(serverWebExchange);
        return taskService.deleteTaskCurrentUser(taskId, userUsername).then(Mono.just(ResponseEntity.noContent().build()));
    }
}
