package com.devaware.userservice.user.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import com.devaware.userservice.user.rest.validation.ResourceValid;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ResourceValid
public class UserResource {
    private Long id;
    private String name;
    private String username;
    private String password;
    private ProfileVO profile;
    private boolean isEnabled;
    private Date createdAt;
    private Date updatedAt;
}
