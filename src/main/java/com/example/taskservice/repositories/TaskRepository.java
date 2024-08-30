package com.example.taskservice.repositories;

import com.example.taskservice.models.Task;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface TaskRepository extends ReactiveCrudRepository<Task, Long> {
}
