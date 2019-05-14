package com.devaware.userservice.role.rest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.devaware.userservice.common.ResourceValid;
import com.devaware.userservice.role.Role;
import com.devaware.userservice.role.RoleRepository;

public class RoleResourceValidator implements ConstraintValidator<ResourceValid, RoleResource> {
	
	@Autowired
	private RoleRepository repository;
	
	@Override
    public boolean isValid(RoleResource resource, ConstraintValidatorContext context) {
        boolean valid = true;
        if (resource.getName() == null || resource.getName().isEmpty()) {
            context.buildConstraintViolationWithTemplate("O nome é obrigatório.").addConstraintViolation();
            valid = false;
        } else {        	
        	Role profile = repository.findByName(resource.getName());
        	if (profile != null && (resource.getId() == null || !profile.getId().equals(resource.getId()))) {
        		context.buildConstraintViolationWithTemplate("O nome informado já existe.").addConstraintViolation();
        		valid = false;
        	}
        }
        if (resource.getVoterName() == null || resource.getVoterName().isEmpty()) {
            context.buildConstraintViolationWithTemplate("O nome técnico é obrigatório.").addConstraintViolation();
            valid = false;
        } else {        	
        	Role profile = repository.findByVoterName(resource.getVoterName());
        	if (profile != null && (resource.getId() == null || !profile.getId().equals(resource.getId()))) {
        		context.buildConstraintViolationWithTemplate("O nome técnico informado já existe.").addConstraintViolation();
        		valid = false;
        	}
        }
        return valid;
    }

}
