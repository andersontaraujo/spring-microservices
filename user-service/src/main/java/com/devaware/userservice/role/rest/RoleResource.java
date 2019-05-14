package com.devaware.userservice.role.rest;

import java.util.Date;

import com.devaware.userservice.common.ResourceValid;

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
    private String voterName;
    private boolean isEnabled;
    private Date createdAt;
    private Date updatedAt;
}
