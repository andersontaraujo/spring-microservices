package com.devaware.userservice.role;

import java.util.List;

public interface RoleCustomRepository {
	
	List<Role> search(RoleFilter filter);

}
