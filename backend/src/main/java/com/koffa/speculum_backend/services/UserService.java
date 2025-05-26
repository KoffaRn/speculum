package com.koffa.speculum_backend.services;

import com.koffa.speculum_backend.entities.user.User;
import com.koffa.speculum_backend.entities.user.UserEntity;
import com.koffa.speculum_backend.entities.user.UserResponse;

public interface UserService {
    UserResponse findAll();
    UserResponse findById(Long id);
    UserResponse save(User user);
    String delete(Long id);
    UserResponse update(Long id, User updatedUserEntity);
}
