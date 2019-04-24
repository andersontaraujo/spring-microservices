package com.devaware.userservice.user;

import java.util.List;

public interface UserCustomRepository {
    List<User> search(UserFilter filter);
}
