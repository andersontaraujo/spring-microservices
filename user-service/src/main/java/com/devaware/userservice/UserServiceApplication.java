package com.devaware.userservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.devaware.userservice.user.User;
import com.devaware.userservice.user.UserRepository;

@EnableDiscoveryClient
@SpringBootApplication
public class UserServiceApplication implements ApplicationRunner {

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		userRepository.save(User.builder().name("Anderson Araújo").username("andersontaraujo").password("1234").build());
	}

}
