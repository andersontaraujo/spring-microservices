package com.devaware.userservice.user.rest;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devaware.userservice.http.RoleClient;
import com.devaware.userservice.user.User;
import com.devaware.userservice.user.UserFilter;
import com.devaware.userservice.user.UserRepository;
import com.devaware.userservice.user.UserRole;

import ma.glasnost.orika.MapperFacade;

@RestController
@RequestMapping("/users")
public class UserRestController {

    @Autowired
    private UserRepository repository;

    @Autowired
    private MapperFacade mapper;
    
    @Autowired
    private RoleClient roleClient;

    @PostMapping
    public ResponseEntity<UserResource> create(@Valid @RequestBody UserResource body) {
        User entity = repository.save(mapper.map(body, User.class));
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/users/{userId}").buildAndExpand(entity.getId()).toUri();
        return ResponseEntity.created(location).body(mapper.map(entity, UserResource.class));
    }

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(required = false) String name, 
    		@RequestParam(required = false) String username, @RequestParam(required = false) Boolean enabled) {
        List<User> users = repository.search(UserFilter.builder().name(name).username(username).enabled(enabled).build());
        return ResponseEntity.ok().body(mapper.mapAsList(users, UserResource.class));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResource> findById(@PathVariable Long userId) {
        Optional<User> user = repository.findById(userId);
        if (!user.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(mapper.map(user.get(), UserResource.class));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResource> update(@PathVariable Long userId, 
    		@Valid @RequestBody UserResource body) {
        if (!userId.equals(body.getId())) {
            return ResponseEntity.badRequest().build();
        }
        Optional<User> user = repository.findById(userId);
        if (!user.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        User entity = repository.save(mapper.map(body, User.class));
        return ResponseEntity.ok().body(mapper.map(entity, UserResource.class));
    }
    
    @GetMapping("/{userId}/roles")
    public ResponseEntity<?> findRolesByUserId(@PathVariable Long userId) {
        Optional<User> user = repository.findById(userId);
        if (!user.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(getRolesDetails(user.get()));
    }
    
    @GetMapping("/{userId}/roles/{roleId}")
    public ResponseEntity<RoleVO> findRoleByUserId(@PathVariable Long userId, @PathVariable Long roleId) {
        Optional<User> user = repository.findById(userId);
        if (!user.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(getRoleDetails(roleId));
    }
    
    private List<RoleVO> getRolesDetails(User user) {
    	ArrayList<RoleVO> roles = new ArrayList<>();    			
    	for (UserRole role : user.getUserRoles()) {
    		roles.add(getRoleDetails(role.getRoleId()));
        }
    	return roles;
    }
    
    private RoleVO getRoleDetails(Long roleId) {
    	ResponseEntity<RoleVO> response = roleClient.getRole(roleId);
    	if (response.getStatusCode().equals(HttpStatus.OK)) {
    		return response.getBody();
    	}
    	return null;
    }

}
