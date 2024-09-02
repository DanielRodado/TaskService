package com.example.taskservice;

import com.example.taskservice.enums.TaskStatus;
import com.example.taskservice.models.TaskEntity;
import com.example.taskservice.repositories.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@DataR2dbcTest
@ActiveProfiles("test")
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void saveAndFindById_ShouldReturnSavedTask() {
        TaskEntity task = new TaskEntity("Sample Title", "Sample Description", TaskStatus.PENDING, 1L);

        taskRepository.save(task)
                .flatMap(savedTask -> taskRepository.findById(savedTask.getId()))
                .as(StepVerifier::create)
                .expectNextMatches(foundTask ->
                        foundTask.getTitle().equals("Sample Title") &&
                                foundTask.getTaskStatus() == TaskStatus.PENDING &&
                                foundTask.getUserId().equals(1L))
                .verifyComplete();
    }

    @Test
    void deleteById_ShouldDeleteTask() {
        TaskEntity task = new TaskEntity("Title to Delete", "Description to Delete", TaskStatus.IN_PROGRESS, 1L);

        taskRepository.save(task)
                .flatMap(savedTask -> taskRepository.deleteById(savedTask.getId()))
                .as(StepVerifier::create)
                .verifyComplete();
    }

}
