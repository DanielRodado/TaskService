package com.example.taskservice.utils;

import org.springframework.web.server.ServerWebExchange;

public final class HeaderUtil {

    public static String extractUsername(ServerWebExchange exchange) {
        return exchange.getRequest().getHeaders().getFirst("username");
    }

}
