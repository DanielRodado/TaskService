package com.example.taskservice;

import com.example.taskservice.enums.TaskStatus;
import com.example.taskservice.models.Task;
import com.example.taskservice.repositories.TaskRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Utils {

    @Bean
    public CommandLineRunner commandLineRunner(TaskRepository taskRepository) {
        return args -> {

            Task task = new Task("Task 1", "Description 1", TaskStatus.PENDING, 1L);
            taskRepository.save(task).subscribe();

            task = new Task("Task 2", "Description 2", TaskStatus.PENDING, 1L);
            taskRepository.save(task).subscribe();

            task = new Task("Task 3", "Description 3", TaskStatus.PENDING, 1L);
            taskRepository.save(task).subscribe();

            task = new Task("Task 4", "Description 4", TaskStatus.PENDING, 2L);
            taskRepository.save(task).subscribe();

            task = new Task("Task 5", "Description 5", TaskStatus.PENDING, 2L);
            taskRepository.save(task).subscribe();

        };
    }

}
