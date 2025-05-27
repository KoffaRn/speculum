package com.koffa.speculum_backend.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.koffa.speculum_backend.entities.user.User;
import com.koffa.speculum_backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;

    public UserResponse findAll() {
        List<UserEntity> userEntities = userRepository.findAll();
        List<User> users = objectMapper.convertValue(userEntities, new TypeReference<List<User>>() {});
        return UserResponse.builder()
                .userList(users)
                .message("Succesfully fetched user list")
                .build();
    }
    public UserResponse findById(Long id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        if(userEntity.isPresent()) {
            User user = objectMapper.convertValue(userEntity.get(), User.class);
            return UserResponse.builder()
                    .user(user)
                    .message("Fetched user for id - " + id)
                    .build();
        } else {
            return UserResponse.builder()
                    .message("User not found")
                    .build();
        }
    }

    public UserResponse save(User user) {
        try {
            UserEntity userEntity = new UserEntity(user.getName(), user.getEmail());
            userRepository.save(userEntity);
            return UserResponse.builder()
                    .message("User saved successfully")
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Failed to save user");
        }
    }

    public String delete(Long id) {
        try {
            userRepository.deleteById(id);
            return "User deleted successfully";
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete user");
        }
    }

    public UserResponse update(Long id, User updatedUser) {
        try {
            Optional<UserEntity> existingUser = userRepository.findById(id);
            if(existingUser.isPresent()) {
                UserEntity userEntity = existingUser.get();
                if (!updatedUser.getName().isEmpty()) {
                    userEntity.setName(updatedUser.getName());
                }
                if (!updatedUser.getEmail().isEmpty()) {
                    userEntity.setEmail((updatedUser.getEmail()));
                }
                userRepository.save(userEntity);
                return UserResponse.builder()
                        .user(objectMapper.convertValue(userEntity, User.class))
                        .message("Successfully update user with id - " + id)
                        .build();
            } else {
                return UserResponse.builder()
                        .message("User not found with id - " + id )
                        .build();
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to update user");
        }
    }
}
