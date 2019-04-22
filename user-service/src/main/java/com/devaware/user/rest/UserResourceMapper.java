package com.devaware.user.rest;

import com.devaware.user.User;
import com.devaware.util.LocalDateTimeConverter;
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
