package com.lavkesh.services;

import com.lavkesh.entity.User;
import java.util.List;

public interface UserService {

  User getUser(Long userId);

  List<User> uploadUserData();
}
