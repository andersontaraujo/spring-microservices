package com.devaware.userservice.role.rest;

import org.springframework.stereotype.Component;

import com.devaware.userservice.common.mapping.IMapperConfigurer;
import com.devaware.userservice.role.Role;

import ma.glasnost.orika.MapperFactory;

@Component
public class RoleResourceMapper implements IMapperConfigurer {
	
	@Override
    public void configure(MapperFactory factory) {
        factory.classMap(Role.class, RoleResource.class).byDefault().register();
    }

}
