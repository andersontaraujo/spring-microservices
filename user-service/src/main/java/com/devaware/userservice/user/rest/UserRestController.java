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

import com.devaware.userservice.http.HttpClient;
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
    private HttpClient request;

    @PostMapping
    public ResponseEntity<UserResource> create(@Valid @RequestBody UserResource body) {
        User entity = repository.save(mapper.map(body, User.class));
        UserResource resource = mapper.map(entity, UserResource.class);
        resource.setRoles(getDetailedRoles(entity));
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/users/{id}").buildAndExpand(entity.getId()).toUri();
        return ResponseEntity.created(location).body(resource);
    }

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(required = false) String name, 
    		@RequestParam(required = false) String username, @RequestParam(required = false) Boolean enabled) {
        List<User> users = repository.search(UserFilter.builder().name(name).username(username).enabled(enabled).build());
        return ResponseEntity.ok().body(mapper.mapAsList(users, UserResource.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResource> findById(@PathVariable Long id) {
        Optional<User> user = repository.findById(id);
        if (!user.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        UserResource resource = mapper.map(user.get(), UserResource.class);
        resource.setRoles(getDetailedRoles(user.get()));
        return ResponseEntity.ok().body(resource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResource> update(@PathVariable Long id, @Valid @RequestBody UserResource body) {
        if (!id.equals(body.getId())) {
            return ResponseEntity.badRequest().build();
        }
        Optional<User> user = repository.findById(id);
        if (!user.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        User entity = repository.save(mapper.map(body, User.class));
        UserResource resource = mapper.map(entity, UserResource.class);
        resource.setRoles(getDetailedRoles(entity));
        return ResponseEntity.ok().body(resource);
    }
    
    private List<RoleVO> getDetailedRoles(User user) {
    	ArrayList<RoleVO> roles = new ArrayList<>();    			
    	for (UserRole role : user.getRoles()) {
        	ResponseEntity<RoleVO> response = request.getRole(role.getRoleId());
        	if (response.getStatusCode().equals(HttpStatus.OK)) {
        		roles.add(response.getBody());
        	}
        }
    	return roles;
    }

}
