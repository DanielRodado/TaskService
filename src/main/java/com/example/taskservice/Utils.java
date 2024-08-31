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
    public CommandLineRunner initData(TaskRepository taskRepository) {
        return args -> {

            /*Task task = new Task("Preparar desayuno", "Preparar una taza de cafe", TaskStatus.COMPLETED, 1L);
            taskRepository.save(task).subscribe();

            Task task2 = new Task("Hacer la cama", "Dejar la habitacion en orden", TaskStatus.PENDING, 1L);
            taskRepository.save(task2).subscribe();

            Task task3 = new Task("Ir al gimnasio", "Ir al gimnasio a hacer ejercicio", TaskStatus.IN_PROGRESS, 1L);
            taskRepository.save(task3).subscribe();

            Task task4 = new Task("Hace la compra", "Comprar alimentos en el supermercado", TaskStatus.PENDING, 2L);
            taskRepository.save(task4).subscribe();

            Task task5 = new Task("Leer un libro", "Leer un libro de 30 minutos", TaskStatus.COMPLETED, 2L);
            taskRepository.save(task5).subscribe();*/

        };
    }

}
