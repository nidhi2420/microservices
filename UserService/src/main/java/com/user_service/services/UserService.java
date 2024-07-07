package com.user_service.services;

import java.util.List;

import com.user_service.entities.User;

public interface UserService {
	//user operations

    //create
    User saveUser(User user);

    //get all user
    List<User> getAllUser();

    //get single user of given userId
    User getUser(Long userId);
    
    //TODO: delete
    //TODO: update

}
