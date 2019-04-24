package com.devaware.userservice.user.rest.validation;

import com.devaware.userservice.user.User;
import com.devaware.userservice.user.UserRepository;
import com.devaware.userservice.user.rest.UserResource;

import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserResourceValidator implements ConstraintValidator<ResourceValid, UserResource> {

    @Autowired
    private UserRepository repository;

    @Override
    public boolean isValid(UserResource resource, ConstraintValidatorContext context) {
        boolean valid = true;
        if (resource.getName() == null || resource.getName().isEmpty()) {
            context.buildConstraintViolationWithTemplate("O nome é obrigatório.").addConstraintViolation();
            valid = false;
        }
        if (resource.getUsername() == null || resource.getUsername().isEmpty()) {
            context.buildConstraintViolationWithTemplate("O login é obrigatório.").addConstraintViolation();
            valid = false;
        }
        User user = repository.findByUsername(resource.getUsername());
        if (user != null && (resource.getId() == null || !user.getId().equals(resource.getId()))) {
            context.buildConstraintViolationWithTemplate("O login informado está em uso por outro usuário.").addConstraintViolation();
            valid = false;
        }
        if (resource.getPassword() == null || resource.getPassword().isEmpty()) {
            context.buildConstraintViolationWithTemplate("A senha é obrigatória.").addConstraintViolation();
            valid = false;
        }
        if (resource.getPassword() != null && resource.getPassword().length() < 6) {
            context.buildConstraintViolationWithTemplate("A senha deve conter no mínimo 6 caracteres.").addConstraintViolation();
            valid = false;
        }
        return valid;
    }
}
