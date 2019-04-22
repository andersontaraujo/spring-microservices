package com.userservice.user.rest;

import com.userservice.user.User;
import com.userservice.util.LocalDateTimeConverter;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class UserResourceMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {
        factory.getConverterFactory().registerConverter(new LocalDateTimeConverter());
        factory.classMap(User.class, UserResource.class)
                .exclude("password")
                .byDefault()
                .register();
    }
}
