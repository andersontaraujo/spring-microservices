package com.devaware.user.rest;

import com.devaware.user.User;
import com.devaware.user.UserFilter;
import com.devaware.user.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserRestController {

    @Autowired
    private UserRepository repository;

    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(required=false) String name, @RequestParam(required=false) String username,
                                     @RequestParam(required=false) String email) {
        List<User> users = repository.search(UserFilter.builder().name(name).username(username).email(email).build());
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

}
