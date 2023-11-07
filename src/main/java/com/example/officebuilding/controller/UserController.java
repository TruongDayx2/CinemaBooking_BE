package com.example.officebuilding.controller;

import com.example.officebuilding.security.entities.User;
import com.example.officebuilding.security.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/api", produces = "application/json")
public class UserController {
    @Autowired
    private IUserService userService;

    @GetMapping("/admin/u/getAllUser")
    public ResponseEntity<List<Map<String, Object>>> getAllUsers(){
        return new ResponseEntity<>(userService.findAllUser(), HttpStatus.OK);
    }

    @GetMapping("/admin/u/getUserByStatus/{status}")
    public ResponseEntity<List<Map<String, Object>>> getAllUserByStatus(@PathVariable Integer status){
        return new ResponseEntity<>(userService.findAllbyUserStatus(status),HttpStatus.OK);
    }
    @PutMapping("/admin/u/update/{userId}")
    public ResponseEntity<Integer> updateUser(@PathVariable Integer userId, @RequestBody User updatedUser) {
        Integer updatedUserData = userService.update(userId, updatedUser);
        return new ResponseEntity<>(updatedUserData, HttpStatus.OK);
    }
    @PutMapping("/admin/u/lock/{userId}")
    public ResponseEntity<Integer> updateUser(@PathVariable Integer userId) {
        Integer updatedUserData = userService.lockUser(userId);
        return new ResponseEntity<>(updatedUserData, HttpStatus.OK);
    }
}
