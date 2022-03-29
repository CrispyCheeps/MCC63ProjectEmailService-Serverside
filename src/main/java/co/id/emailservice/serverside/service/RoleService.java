/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.emailservice.serverside.service;

import co.id.emailservice.serverside.model.Role;
import co.id.emailservice.serverside.model.dto.RoleData;
import co.id.emailservice.serverside.repository.RoleRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.modelmapper.ModelMapper;

/**
 *
 * @author Deanchristt
 */
@Service
public class RoleService {

    private RoleRepository roleRepository;
    private ModelMapper modelMapper;

    @Autowired
    public RoleService(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    public Role getById(Long id) {
        return roleRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role Not Found"));
    }

    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    public Role create(Role role) {
        if (role.getId() != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Role already exist");
        }
        return roleRepository.save(role);
    }

    public Role update(Long id, Role role) {
        getById(id);
        role.setId(id);
        return roleRepository.save(role);
    }

    public Role delete(Long id) {
        Role role = getById(id);
        roleRepository.delete(role);
        return role;
    }
}
