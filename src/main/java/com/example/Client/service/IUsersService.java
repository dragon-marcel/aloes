package com.example.Client.service;
import com.example.Client.entity.User;
import java.util.List;

public interface IUsersService {
     List<User> findAll();
     void delateUserbyId(Long id);
     void saveUser(User users);

}
