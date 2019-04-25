package com.devaware.roleservice.role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleFilter {
	private String name;
	private Long userId;
	private Boolean enabled;
}
