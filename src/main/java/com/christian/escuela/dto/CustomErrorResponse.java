package com.christian.escuela.dto;

public record CustomErrorResponse(
        int codigo,
        String mensaje
) {}