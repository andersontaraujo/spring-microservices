package com.devaware.profileservice.profile.rest;

import org.springframework.stereotype.Component;

import com.devaware.profileservice.profile.Profile;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;

@Component
public class ProfileResourceMapper extends ConfigurableMapper {
	
	@Override
    protected void configure(MapperFactory factory) {
        factory.classMap(Profile.class, ProfileResource.class).byDefault().register();
    }

}
