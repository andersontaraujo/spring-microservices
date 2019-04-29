package com.devaware.userservice.user.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.devaware.userservice.mapping.IMapperConfigurer;
import com.devaware.userservice.user.User;
import com.devaware.userservice.user.UserRepository;
import com.devaware.userservice.user.UserRole;

import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;

@Component
public class UserResourceMapper extends CustomMapper<User, UserResource> implements IMapperConfigurer {	
	
	@Autowired
	private UserRepository repository;
			
	@Override
    public void mapBtoA(UserResource b, User a, MappingContext context) {
		a.getRoles().clear();
		if (a.getId() != null) {
			repository.save(a);			
		}
    	for (RoleVO role : b.getRoles()) {
    		a.addRole(UserRole.builder().roleId(role.getId()).build());
    	}
    }
	@Override
	public void mapAtoB(User a, UserResource b, MappingContext context) {
		b.setPassword(null);
		List<RoleVO> roles = new ArrayList<>();
		for (UserRole role : a.getRoles()) {			
			roles.add(RoleVO.builder().id(role.getRoleId()).build());
		}
		b.setRoles(roles);
	}

    @Override
	public void configure(MapperFactory factory) {
        factory.classMap(User.class, UserResource.class)
                .customize(this)
                .byDefault()
                .register();
    }
}
