package com.lavkesh.services;

import com.lavkesh.entity.User;
import java.util.Collection;

public interface UserService {

  User getUser(Long userId);

  Collection<User> uploadUserData();
}
