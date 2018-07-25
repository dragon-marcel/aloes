package com.example.Client.repository;

import com.example.Client.dao.IUserDAO;
import com.example.Client.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class UsersRepository implements IUsersRepository {

@Autowired
private IUserDAO iUserDAO;

@Autowired
private  BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public List<User> findAllUser() {
        return (List<User>) iUserDAO.findAll();
    }

    @Override
    public void deleteUserbyId(Long id) {
        iUserDAO.deleteById(id);
    }

    @Override
    public void saveUser(User user) {

        String pass = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(pass);

        iUserDAO.save(user);
    }

}
