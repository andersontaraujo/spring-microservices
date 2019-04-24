package com.devaware.profileservice.profile;

import org.springframework.data.repository.CrudRepository;

public interface ProfileRepository extends CrudRepository<Profile, Long> {
	
	Profile findByName(String name);

}
