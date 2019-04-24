package com.devaware.profileservice.profile.rest;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devaware.profileservice.profile.Profile;
import com.devaware.profileservice.profile.ProfileRepository;

import ma.glasnost.orika.MapperFacade;

@RestController
@RequestMapping("/profiles")
public class ProfileRestController {
	
	@Autowired
	private ProfileRepository repository;
	
	@Autowired
	private MapperFacade mapper;
	
	@PostMapping
	public ResponseEntity<ProfileResource> create(@Valid @RequestBody ProfileResource resource) {
		Profile profile = repository.save(mapper.map(resource, Profile.class));
		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/profiles/{id}").buildAndExpand(profile.getId()).toUri();
		return ResponseEntity.created(location).body(mapper.map(profile, ProfileResource.class));
	}
	
	@GetMapping
	public ResponseEntity<?> findAll() {
		Iterable<Profile> profiles = repository.findAll();		
		return ResponseEntity.ok().body(mapper.mapAsList(profiles, ProfileResource.class));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ProfileResource> findById(@PathVariable Long id) {
		Optional<Profile> profile = repository.findById(id);
		if (!profile.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(mapper.map(profile.get(), ProfileResource.class));
	}
	
	@PutMapping("/{id}")
    public ResponseEntity<ProfileResource> update(@PathVariable Long id, @Valid @RequestBody ProfileResource resource) {
        if (!id.equals(resource.getId())) {
            return ResponseEntity.badRequest().build();
        }
        Optional<Profile> profile = repository.findById(id);
        if (!profile.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Profile entity = repository.save(mapper.map(resource, Profile.class));
        return ResponseEntity.ok().body(mapper.map(entity, ProfileResource.class));
    }

}
