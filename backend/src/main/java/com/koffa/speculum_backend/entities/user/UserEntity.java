package com.koffa.speculum_backend.entities.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @GeneratedValue
    @Id
    private long id;
    private String name;
    private String email;
    public UserEntity(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
