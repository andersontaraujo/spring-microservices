package com.devaware.profileservice.profile.rest;

import java.util.Date;

import com.devaware.profileservice.profile.rest.validation.ResourceValid;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ResourceValid
public class RoleResource {
	private Long id;
    private String name;
    private boolean isEnabled;
    private Date createdAt;
    private Date updatedAt;
}
