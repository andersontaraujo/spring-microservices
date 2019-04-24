package com.devaware.profileservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.devaware.profileservice.profile.Role;
import com.devaware.profileservice.profile.RoleRepository;

@EnableDiscoveryClient
@SpringBootApplication
public class RoleServiceApplication implements ApplicationRunner {
	
	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(RoleServiceApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		roleRepository.save(Role.builder().name("ROLE_ADMIN").build());
	}

}
