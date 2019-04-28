package com.devaware.userservice.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.devaware.userservice.user.User;
import com.devaware.userservice.user.rest.RoleVO;

@Component
public class HttpRequest {
	
	@Autowired
    private RestTemplate rest;
	
	public ResponseEntity<List<RoleVO>> getRoles(User user) {
		UriComponentsBuilder builder = UriComponentsBuilder
        		.fromUriString("http://role-service/roles")
        		.queryParam("userId", user.getId())
        		.queryParam("enabled", true);
		return rest.exchange(
        		builder.toUriString(), 
        		HttpMethod.GET, 
        		null, 
        		new ParameterizedTypeReference<List<RoleVO>>(){});
	}
	
	public ResponseEntity<RoleVO> getRole(Long roleId) {
		UriComponentsBuilder builder = UriComponentsBuilder
        		.fromUriString("http://role-service/roles")
        		.pathSegment(roleId.toString());
		return rest.getForEntity(builder.toUriString(), RoleVO.class);
	}

}
