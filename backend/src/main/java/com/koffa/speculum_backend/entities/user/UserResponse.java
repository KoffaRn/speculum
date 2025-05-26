package com.koffa.speculum_backend.entities.user;

import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@Data
@Getter
@Setter
public class UserResponse {
    String message;
    User user;
    List<User> userList;
}
