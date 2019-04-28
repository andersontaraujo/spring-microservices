package com.devaware.userservice.user.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.devaware.userservice.user.User;
import com.devaware.userservice.user.UserRole;
import com.devaware.userservice.util.IMapperConfigurer;

import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;

@Component
public class UserResourceMapper extends CustomMapper<User, UserResource> implements IMapperConfigurer {
			
	@Override
    public void mapBtoA(UserResource b, User a, MappingContext context) {
		a.setRoles(new ArrayList<>());
    	List<RoleVO> roles = b.getRoles();
    	for (RoleVO role : roles) {
    		a.addRole(UserRole.builder().roleId(role.getId()).build());
    	}
//    	for (UserRole roleToAdd : roles) {
//    		if (roleToAdd.getId() == null) {    			
//    			a.addRole(roleToAdd);
//    		}
//    	}
//    	roles = new ArrayList<>(a.getRoles());
//    	for (UserRole roleToRemove : roles) {
//    		if (!roles.contains(roleToRemove)) {
//    			a.removeRole(roleToRemove);
//    		}
//    	}
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
