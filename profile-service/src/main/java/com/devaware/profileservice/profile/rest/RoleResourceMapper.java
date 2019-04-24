package com.devaware.profileservice.profile.rest;

import org.springframework.stereotype.Component;

import com.devaware.profileservice.profile.Role;
import com.devaware.profileservice.util.LocalDateTimeConverter;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;

@Component
public class RoleResourceMapper extends ConfigurableMapper {
	
	@Override
    protected void configure(MapperFactory factory) {
		factory.getConverterFactory().registerConverter(new LocalDateTimeConverter());
        factory.classMap(Role.class, RoleResource.class).byDefault().register();
    }

}
