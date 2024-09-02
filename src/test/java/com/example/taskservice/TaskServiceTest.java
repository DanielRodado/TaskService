package com.example.taskservice;

import com.example.taskservice.dto.TaskApplicationDTO;
import com.example.taskservice.dto.TaskEntityDTO;
import com.example.taskservice.enums.TaskStatus;
import com.example.taskservice.models.TaskEntity;
import com.example.taskservice.repositories.TaskRepository;
import com.example.taskservice.services.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static com.example.taskservice.mappers.TaskMapper.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class TaskServiceTest {

    @MockBean
    private TaskRepository taskRepository;

    @Autowired
    private TaskService taskService;

    @Test
    void createTask_ShouldReturnSavedTaskEntityDTO() {
        TaskApplicationDTO taskApplicationDTO = new TaskApplicationDTO("Sample Title", "Sample Description", "PENDING", 1L);
        Mono<TaskEntity> taskEntityMono = toTaskEntityMono(taskApplicationDTO);

        when(taskRepository.save(any(TaskEntity.class))).thenReturn(taskEntityMono);

        StepVerifier.create(taskService.createTask(Mono.just(taskApplicationDTO)))
                .expectNextMatches(taskEntityDTO ->
                        taskEntityDTO.getTitle().equals(taskApplicationDTO.title())
                        && taskEntityDTO.getDescription().equals(taskApplicationDTO.description())
                        && taskEntityDTO.getStatus() == TaskStatus.PENDING)
                .verifyComplete();
    }


    @Test
    void getAllTasks_ShouldReturnFluxOfTasks() {
        TaskEntity task1 = new TaskEntity("Title1", "Description1", TaskStatus.PENDING, 1L);
        TaskEntity task2 = new TaskEntity("Title2", "Description2", TaskStatus.IN_PROGRESS, 2L);
        when(taskRepository.findAll()).thenReturn(Flux.just(task1, task2));

        StepVerifier.create(taskService.getAllTasks())
                .expectNext(task1)
                .expectNext(task2)
                .verifyComplete();
    }

    @Test
    void deleteTask_ShouldDeleteTaskSuccessfully() {
        Long taskId = 1L;
        TaskEntity existingTask = new TaskEntity("Sample Title", "Sample Description", TaskStatus.PENDING, 1L);

        when(taskRepository.findById(taskId)).thenReturn(Mono.just(existingTask));
        when(taskRepository.delete(existingTask)).thenReturn(Mono.empty());

        StepVerifier.create(taskService.deleteTask(taskId))
                .verifyComplete();
    }

}

