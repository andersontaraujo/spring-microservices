package com.devaware.roleservice.role;

import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long>, RoleCustomRepository {
	
	Role findByName(String name);

}
