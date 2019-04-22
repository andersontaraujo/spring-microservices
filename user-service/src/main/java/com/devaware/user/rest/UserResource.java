package com.devaware.user.rest;

import com.devaware.user.rest.validation.ResourceValid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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
    private Boolean isEnabled;
    private Date createdAt;
    private Date updatedAt;
}
