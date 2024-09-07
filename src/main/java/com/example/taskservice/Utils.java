package com.example.taskservice;

import com.example.taskservice.enums.TaskStatus;
import com.example.taskservice.models.TaskEntity;
import com.example.taskservice.repositories.TaskRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Utils {

    @Bean
    public CommandLineRunner initData(TaskRepository taskRepository) {
        return args -> {

            /*TaskEntity task = new TaskEntity("Preparar desayuno", "Preparar una taza de cafe", TaskStatus.COMPLETED, "daniel14");
            taskRepository.save(task).subscribe();

            TaskEntity task2 = new TaskEntity("Hacer la cama", "Dejar la habitacion en orden", TaskStatus.PENDING, "daniel14");
            taskRepository.save(task2).subscribe();

            TaskEntity task3 = new TaskEntity("Ir al gimnasio", "Ir al gimnasio a hacer ejercicio", TaskStatus.IN_PROGRESS, "daniel14");
            taskRepository.save(task3).subscribe();

            TaskEntity task4 = new TaskEntity("Hace la compra", "Comprar alimentos en el supermercado", TaskStatus.PENDING, "Steph23");
            taskRepository.save(task4).subscribe();

            TaskEntity task5 = new TaskEntity("Leer un libro", "Leer un libro de 30 minutos", TaskStatus.COMPLETED, "prezzz12");
            taskRepository.save(task5).subscribe();*/

        };
    }

}
