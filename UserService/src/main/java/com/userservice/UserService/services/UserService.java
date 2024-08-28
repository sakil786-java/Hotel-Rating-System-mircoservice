package com.userservice.UserService.services;


import com.userservice.UserService.entities.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    User saveUSer(User user);

    User updateUserIfPresent(String userId, Map<String, Object> updates);

    List<User> getAllUser();

    User getUser(String userId);

    String deleteUser(String userId);


}
