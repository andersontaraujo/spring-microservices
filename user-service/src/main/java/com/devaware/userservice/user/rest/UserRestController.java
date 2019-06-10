package com.devaware.userservice.user.rest;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.devaware.userservice.role.RoleRepository;
import com.devaware.userservice.user.User;
import com.devaware.userservice.user.UserFilter;
import com.devaware.userservice.user.UserRepository;

import ma.glasnost.orika.MapperFacade;

@RestController
@RequestMapping("/users")
public class UserRestController {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private MapperFacade mapper;

    @PostMapping
    public ResponseEntity<UserResource> create(@Valid @RequestBody UserResource body) {
        User entity = userRepository.save(mapper.map(body, User.class));
        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri()
        		.path("/{id}").buildAndExpand(entity.getId()).toUri();
        return ResponseEntity.created(location).body(mapper.map(entity, UserResource.class));
    }

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(required = false) String name, 
    		@RequestParam(required = false) String username, @RequestParam(required = false) Boolean enabled) {
        List<User> users = userRepository.search(new UserFilter(name, username, enabled));
        return ResponseEntity.ok().body(mapper.mapAsList(users, UserResource.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResource> findById(@PathVariable Long id) {
    	return userRepository.findById(id)
    			.map(user -> ResponseEntity.ok().body(mapper.map(user, UserResource.class)))
    			.orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResource> update(@PathVariable Long id, 
    		@Valid @RequestBody UserResource body) {
        if (!id.equals(body.getId())) {
            return ResponseEntity.badRequest().build();
        }
        return userRepository.findById(id)
    			.map(user -> {
    				User entity = userRepository.save(mapper.map(body, User.class));
    				return ResponseEntity.ok().body(mapper.map(entity, UserResource.class));
    			})
    			.orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/{id}/roles")
    public ResponseEntity<?> findRolesByUserId(@PathVariable Long id) {
        return userRepository.findById(id)
    			.map(user -> ResponseEntity.ok().body(getRolesDetails(user)))
    			.orElse(ResponseEntity.notFound().build());
    }
    
    private List<RoleVO> getRolesDetails(User user) {
    	return user.getUserRoles().stream()
    			.map(role -> getRoleDetails(role.getRole().getId()))
    			.collect(Collectors.toList());
    }
    
    private RoleVO getRoleDetails(Long roleId) {
    	return roleRepository.findById(roleId)
    			.map(role -> mapper.map(role, RoleVO.class)).orElse(null);
    }

}
