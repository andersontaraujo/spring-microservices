package com.userservice.user.rest;

import com.userservice.user.User;
import com.userservice.util.LocalDateTimeConverter;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserResourceMapper extends ConfigurableMapper {

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    protected void configure(MapperFactory factory) {
        factory.getConverterFactory().registerConverter(new LocalDateTimeConverter());
        factory.classMap(User.class, UserResource.class)
                .exclude("password")
                .customize(new CustomMapper<User, UserResource>() {
                    @Override
                    public void mapBtoA(UserResource b, User a, MappingContext context) {
                        a.setPassword(encoder.encode(b.getPassword()));
                    }
                })
                .byDefault()
                .register();
    }
}
