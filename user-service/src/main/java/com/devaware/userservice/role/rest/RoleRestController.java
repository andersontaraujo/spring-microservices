package com.devaware.userservice.role.rest;

import java.net.URI;

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

import com.devaware.userservice.role.Role;
import com.devaware.userservice.role.RoleFilter;
import com.devaware.userservice.role.RoleRepository;

import ma.glasnost.orika.MapperFacade;

@RestController
@RequestMapping("/roles")
public class RoleRestController {
	
	@Autowired
	private RoleRepository repository;
	
	@Autowired
	private MapperFacade mapper;
	
	@PostMapping
	public ResponseEntity<RoleResource> create(@Valid @RequestBody RoleResource resource) {
		Role role = repository.save(mapper.map(resource, Role.class));
		URI location = ServletUriComponentsBuilder.fromCurrentRequestUri()
				.path("/{id}").buildAndExpand(role.getId()).toUri();
		return ResponseEntity.created(location).body(mapper.map(role, RoleResource.class));
	}
	
	@GetMapping
	public ResponseEntity<?> findAll(@RequestParam(required = false) String name,
			@RequestParam(required = false) Boolean enabled) {
		Iterable<Role> roles = repository.search(new RoleFilter(name, enabled));		
		return ResponseEntity.ok().body(mapper.mapAsList(roles, RoleResource.class));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<RoleResource> findById(@PathVariable Long id) {
		return repository.findById(id)
				.map(role -> ResponseEntity.ok().body(mapper.map(role, RoleResource.class)))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@PutMapping("/{id}")
    public ResponseEntity<RoleResource> update(@PathVariable Long id, @Valid @RequestBody RoleResource resource) {
        if (!id.equals(resource.getId())) {
            return ResponseEntity.badRequest().build();
        }
        return repository.findById(id)
				.map(role -> {
					Role entity = repository.save(mapper.map(resource, Role.class));
					return ResponseEntity.ok().body(mapper.map(entity, RoleResource.class));
				})
				.orElse(ResponseEntity.notFound().build());
    }

}
