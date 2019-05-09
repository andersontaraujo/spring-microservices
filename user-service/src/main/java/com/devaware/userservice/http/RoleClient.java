package com.devaware.userservice.http;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.devaware.userservice.user.rest.RoleVO;

@FeignClient("role-service")
public interface RoleClient {
	
	@GetMapping("/roles/{id}")
	ResponseEntity<RoleVO> getRole(@PathVariable("id") Long id);

}
