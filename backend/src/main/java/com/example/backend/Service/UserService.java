package com.example.backend.Service;

import com.example.backend.DTO.UserDTO;
import com.example.backend.Entity.UserEntity;
import com.example.backend.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public UserDTO getUserById(UUID id) {
        UserEntity userEntity = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return convertEntityToDTO(userEntity);
    }

    private UserDTO convertEntityToDTO(UserEntity userEntity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userEntity.getId());
        userDTO.setName(userEntity.getName());
        userDTO.setUsername(userEntity.getUsername());
        userDTO.setPassword(userEntity.getPassword());
        userDTO.setEmail(userEntity.getEmail());
        return userDTO;
    }

    public UserDTO updateUserById(UUID id, UserDTO userDTO) {
        // Find the existing user
        UserEntity existingUser = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));

        // Update fields
        if (userDTO.getName() != null) {
            existingUser.setName(userDTO.getName());
        }

        if (userDTO.getUsername() != null) {
            existingUser.setUsername(userDTO.getUsername());
        }

        if (userDTO.getPassword() != null) {
            existingUser.setPassword(userDTO.getPassword());
        }

        if (userDTO.getEmail() != null) {
            existingUser.setEmail(userDTO.getEmail());
        }

        // Save the updated entity and return the updated DTO
        UserEntity updatedEntity = userRepo.save(existingUser);
        return convertEntityToDTO(updatedEntity);
    }

    public List<UserDTO> getAllUsers() {
        return userRepo.findAll().stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
    }

    public UserDTO saveUser(UserDTO userDTO) {
        UserEntity userEntity = convertDTOToEntity(userDTO);
        UserEntity savedEntity = userRepo.save(userEntity);
        return convertEntityToDTO(savedEntity);
    }

    private UserEntity convertDTOToEntity(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(userDTO.getName());
        userEntity.setUsername(userDTO.getUsername());
        userEntity.setPassword(userDTO.getPassword());
        userEntity.setEmail(userDTO.getEmail());
        return userEntity;
    }

    public void deleteUserById(UUID id) {
        if (!userRepo.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepo.deleteById(id);
    }

}
