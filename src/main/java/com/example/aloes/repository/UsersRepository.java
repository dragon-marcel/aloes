package com.example.aloes.repository;

import com.example.aloes.dao.UserDAO;
import com.example.aloes.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class UsersRepository implements UsersR {

@Autowired
private UserDAO userDAO;

@Autowired
private  BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public List<User> findAllUser() {
        return (List<User>) userDAO.findAll();
    }

    @Override
    public void deleteUserbyId(Long id) {
        userDAO.deleteById(id);
    }

    @Override
    public void saveUser(User user) {

        String pass = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(pass);

        userDAO.save(user);
    }

}
