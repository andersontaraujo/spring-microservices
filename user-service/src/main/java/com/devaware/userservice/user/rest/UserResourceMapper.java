package com.devaware.userservice.user.rest;

import org.springframework.stereotype.Component;

import com.devaware.userservice.user.User;
import com.devaware.userservice.util.LocalDateTimeConverter;

import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.ConfigurableMapper;

@Component
public class UserResourceMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {
        factory.getConverterFactory().registerConverter(new LocalDateTimeConverter());
        factory.classMap(User.class, UserResource.class)
                .exclude("password")
                .customize(new CustomMapper<User, UserResource>() {
                    @Override
                    public void mapBtoA(UserResource b, User a, MappingContext context) {
                        a.setProfileId(b.getProfile().getId());
                    }
					@Override
					public void mapAtoB(User a, UserResource b, MappingContext context) {
						b.setProfile(ProfileVO.builder().id(a.getProfileId()).build());
					}
                })
                .byDefault()
                .register();
    }
}
