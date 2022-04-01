/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.emailservice.serverside.service;

import co.id.emailservice.serverside.model.Role;
import co.id.emailservice.serverside.model.User;
import co.id.emailservice.serverside.model.dto.UserData;
import co.id.emailservice.serverside.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author Deanchristt
 */
@Service
public class UserService {

    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private RoleService roleService;

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper, RoleService roleService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.roleService = roleService;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User tidak ditemukan")
        );
    }

    public User create(UserData userData) {
        User user = modelMapper.map(userData, User.class);
        user.setId(null);
        //kalau dicasting gini boleh?
        user.setRoles((List<Role>) roleService.getById(userData.getRoleId()));
        return userRepository.save(user);
    }
    
    public User update(Long id, User user) {
        User u = getById(id);
        user.setId(id);
//        user.setRole(u.getRole());
        user.setRoles(u.getRoles());
        return userRepository.save(user);
    }
    
    public User delete(Long id) {
        User user = getById(id);
        userRepository.delete(user);
        return user;
    }
}
