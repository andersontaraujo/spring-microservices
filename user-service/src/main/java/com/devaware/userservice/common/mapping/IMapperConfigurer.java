package com.devaware.userservice.common.mapping;

import ma.glasnost.orika.MapperFactory;

public interface IMapperConfigurer {
	
	void configure(MapperFactory factory);

}
