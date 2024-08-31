package com.example.taskservice.repositories;

import com.example.taskservice.models.TaskEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface TaskRepository extends ReactiveCrudRepository<TaskEntity, Long> {
}
