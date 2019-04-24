package com.devaware.roleservice.role.rest.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.devaware.roleservice.role.Role;
import com.devaware.roleservice.role.RoleRepository;
import com.devaware.roleservice.role.rest.RoleResource;

public class RoleResourceValidator implements ConstraintValidator<ResourceValid, RoleResource> {
	
	@Autowired
	private RoleRepository repository;
	
	@Override
    public boolean isValid(RoleResource resource, ConstraintValidatorContext context) {
        boolean valid = true;
        if (resource.getName() == null || resource.getName().isEmpty()) {
            context.buildConstraintViolationWithTemplate("O nome é obrigatório.").addConstraintViolation();
            valid = false;
        }
        Role profile = repository.findByName(resource.getName());
        if (profile != null && (resource.getId() == null || !profile.getId().equals(resource.getId()))) {
            context.buildConstraintViolationWithTemplate("O nome informado já existe.").addConstraintViolation();
            valid = false;
        }
        return valid;
    }

}
