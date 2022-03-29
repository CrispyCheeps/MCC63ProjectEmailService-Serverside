/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.emailservice.serverside.controller;

import co.id.emailservice.serverside.model.User;
import co.id.emailservice.serverside.model.dto.UserData;
import co.id.emailservice.serverside.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Deanchristt
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;
    
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        return new ResponseEntity(userService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        return new ResponseEntity(userService.getById(id), HttpStatus.OK);
    }
    
    @PostMapping
    public User create(@RequestBody UserData userData) {
        return userService.create(userData);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User employeeData) {
        return new ResponseEntity(userService.update(id, employeeData), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteByIdQuery(@PathVariable Long id) {
        return new ResponseEntity(userService.delete(id), HttpStatus.OK);
    }
    
}
