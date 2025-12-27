package com.backend.DTO;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String email;
    private String senha;
}
