package com.devaware.user;

import java.util.List;

public interface UserCustomRepository {
    List<User> search(UserFilter filter);
}
