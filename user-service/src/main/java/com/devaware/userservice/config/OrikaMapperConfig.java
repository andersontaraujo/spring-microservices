package com.devaware.userservice.config;

import java.util.Iterator;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.devaware.userservice.util.IMapperConfigurer;
import com.devaware.userservice.util.LocalDateTimeConverter;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;

@Component
public class OrikaMapperConfig extends ConfigurableMapper implements ApplicationContextAware {
	
	private ApplicationContext applicationContext;
	
	private MapperFactory mapperFactory;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
		configureMappers();
	}

	@Override
	protected void configure(MapperFactory factory) {
		this.mapperFactory = factory;
		this.mapperFactory.getConverterFactory().registerConverter(new LocalDateTimeConverter());
	}
	
	public void configureMappers() {
		Map<String, IMapperConfigurer> mapeadores = this.applicationContext.getBeansOfType(IMapperConfigurer.class);
		Iterator<IMapperConfigurer> iterator = mapeadores.values().iterator();
		
		while(iterator.hasNext()) {
			IMapperConfigurer mapeador = iterator.next();
			mapeador.configure(this.mapperFactory);
		}
	}

}
