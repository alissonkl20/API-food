package com.backend.DTO;

public class RecuperarSenhaResponseDTO {
    private String message;

    public RecuperarSenhaResponseDTO(String message) {
        this.message = message;
    }

    // Getters and Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
