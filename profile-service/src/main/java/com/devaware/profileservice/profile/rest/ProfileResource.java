package com.devaware.profileservice.profile.rest;

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
public class ProfileResource {
	private Long id;
    private String name;
    private boolean isEnabled;
}
