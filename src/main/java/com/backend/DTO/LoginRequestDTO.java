package com.backend.DTO;

public class LoginDTO {

    public class LoginRequest {
        private String email;
        private String senha;

        // getters e setters
    }

    // src/main/java/com/seuprojeto/dto/LoginResponse.java
    public class LoginResponse {
        private String token;
        private String tipo = "Bearer";
        private Long id;
        private String email;
        private String role;

        // getters e setters
    }
}
