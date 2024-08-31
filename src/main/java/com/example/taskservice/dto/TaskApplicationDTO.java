package com.example.taskservice.dto;

public record TaskApplicationDTO(String title, String description, String status, Long userId) {
}
