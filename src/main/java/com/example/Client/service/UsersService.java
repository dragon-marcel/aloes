package com.example.Client.service;

import com.example.Client.dao.IUsers;
import com.example.Client.entity.User;
import com.example.Client.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService implements IUsersService {

@Autowired
private IUsers iUsers;

@Autowired
private  BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public List<User> findAll() {
        return (List<User>) iUsers.findAll();
    }

    @Override
    public void deleteUserbyId(Long id) {
        iUsers.deleteById(id);
    }

    @Override
    public void saveUser(User user) {

        String pass = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(pass);

        iUsers.save(user);
    }

}
