package com.koffa.speculum_backend.entities.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    private Long id;
    private String name;
    private String email;
    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
