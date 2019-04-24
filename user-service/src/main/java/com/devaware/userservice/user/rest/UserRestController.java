package com.devaware.userservice.user.rest;

import ma.glasnost.orika.MapperFacade;
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

import com.devaware.userservice.user.User;
import com.devaware.userservice.user.UserFilter;
import com.devaware.userservice.user.UserRepository;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserRestController {

    @Autowired
    private UserRepository repository;

    @Autowired
    private MapperFacade mapper;

    @PostMapping
    public ResponseEntity<UserResource> create(@Valid @RequestBody UserResource resource) {
        User entity = repository.save(mapper.map(resource, User.class));
        URI location =
                ServletUriComponentsBuilder.fromCurrentContextPath().path("/users/{id}").buildAndExpand(entity.getId()).toUri();
        return ResponseEntity.created(location).body(mapper.map(entity, UserResource.class));
    }

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(required=false) String name, @RequestParam(required=false) String username,
                                     @RequestParam(required=false) String email) {
        List<User> users = repository.search(UserFilter.builder().name(name).username(username).build());
        return ResponseEntity.ok().body(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResource> findById(@PathVariable Long id) {
        Optional<User> user = repository.findById(id);
        if (!user.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(mapper.map(user.get(), UserResource.class));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResource> update(@PathVariable Long id, @Valid @RequestBody UserResource resource) {
        if (!id.equals(resource.getId())) {
            return ResponseEntity.badRequest().build();
        }
        Optional<User> user = repository.findById(id);
        if (!user.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        User entity = repository.save(mapper.map(resource, User.class));
        return ResponseEntity.ok().body(mapper.map(entity, UserResource.class));
    }

}
