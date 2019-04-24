package com.devaware.profileservice.profile.rest.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.devaware.profileservice.profile.Profile;
import com.devaware.profileservice.profile.ProfileRepository;
import com.devaware.profileservice.profile.rest.ProfileResource;

public class ProfileResourceValidator implements ConstraintValidator<ResourceValid, ProfileResource> {
	
	@Autowired
	private ProfileRepository repository;
	
	@Override
    public boolean isValid(ProfileResource resource, ConstraintValidatorContext context) {
        boolean valid = true;
        if (resource.getName() == null || resource.getName().isEmpty()) {
            context.buildConstraintViolationWithTemplate("O nome é obrigatório.").addConstraintViolation();
            valid = false;
        }
        Profile profile = repository.findByName(resource.getName());
        if (profile != null && (resource.getId() == null || !profile.getId().equals(resource.getId()))) {
            context.buildConstraintViolationWithTemplate("O nome informado já existe.").addConstraintViolation();
            valid = false;
        }
        return valid;
    }

}
