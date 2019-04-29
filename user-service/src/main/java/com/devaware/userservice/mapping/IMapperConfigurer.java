package com.devaware.userservice.mapping;

import ma.glasnost.orika.MapperFactory;

public interface IMapperConfigurer {
	
	void configure(MapperFactory factory);

}
