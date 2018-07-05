package com.example.Client.service;

import com.example.Client.entity.User;

import java.util.List;

public interface IUsersService {
    public List<User> findAll();
    public void delateUserbyId(Long id);
    public void saveUser(User users);
    public User finduserbyId(Long id);

}
