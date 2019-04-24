package com.devaware.profileservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.devaware.profileservice.profile.Profile;
import com.devaware.profileservice.profile.ProfileRepository;

@EnableDiscoveryClient
@SpringBootApplication
public class ProfileServiceApplication implements ApplicationRunner {
	
	@Autowired
	private ProfileRepository profileRepository;

	public static void main(String[] args) {
		SpringApplication.run(ProfileServiceApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		profileRepository.save(Profile.builder().name("Admin").build());
	}

}
