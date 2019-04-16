package com.devaware.user;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long>, UserCustomRepository {

    User findByUsername(String username);

}
