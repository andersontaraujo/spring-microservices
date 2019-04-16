package com.devaware.user.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResource {
    private Long id;
    private String name;
    private String username;
    private String password;
    private String email;
}
