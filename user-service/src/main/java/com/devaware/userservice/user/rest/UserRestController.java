package com.devaware.userservice.user.rest;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import com.devaware.userservice.user.User;
import com.devaware.userservice.user.UserFilter;
import com.devaware.userservice.user.UserRepository;

import ma.glasnost.orika.MapperFacade;

@RestController
@RequestMapping("/users")
public class UserRestController {

    @Autowired
    private UserRepository repository;

    @Autowired
    private MapperFacade mapper;
    
    @Autowired
    private RestTemplate rest;

    @PostMapping
    public ResponseEntity<UserResource> create(@Valid @RequestBody UserResource resource) {
        User entity = repository.save(mapper.map(resource, User.class));
        //TODO: implementar lógica de criação das roles chamando o endpoint do serviço
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/users/{id}").buildAndExpand(entity.getId()).toUri();
        return ResponseEntity.created(location).body(mapper.map(entity, UserResource.class));
    }

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(required = false) String name, 
    		@RequestParam(required = false) String username) {
        List<User> users = repository.search(UserFilter.builder().name(name).username(username).build());
        return ResponseEntity.ok().body(mapper.mapAsList(users, UserResource.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResource> findById(@PathVariable Long id) {
        Optional<User> user = repository.findById(id);
        if (!user.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        UserResource resource = mapper.map(user.get(), UserResource.class);
        UriComponentsBuilder builder = UriComponentsBuilder
        		.fromUriString("http://role-service/roles")
        		.queryParam("userId", user.get().getId())
        		.queryParam("enabled", true);
        ResponseEntity<List<RoleVO>> response = rest.exchange(
        		builder.toUriString(), 
        		HttpMethod.GET, 
        		null, 
        		new ParameterizedTypeReference<List<RoleVO>>(){});
        if (response.getStatusCode().equals(HttpStatus.OK)) {
        	resource.setRoles(response.getBody());
        }
        return ResponseEntity.ok().body(resource);
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
