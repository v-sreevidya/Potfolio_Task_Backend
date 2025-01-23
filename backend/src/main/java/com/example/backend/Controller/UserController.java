package com.example.backend.Controller;

import com.example.backend.Entity.UserEntity;
import com.example.backend.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserRepo userRepo;

    //saving data
    @PostMapping
    public UserEntity saveUserEntity(@RequestBody UserEntity userEntity){
        return userRepo.save(userEntity);
    }

    //getting all data
    @GetMapping
    public List<UserEntity> getAllUserEntity(){
        return  userRepo.findAll();
    }

    //get by id
    @GetMapping("/{id}")
    public ResponseEntity<UserEntity>
    getUserEntityByUUID(@PathVariable UUID id){
        return userRepo.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
