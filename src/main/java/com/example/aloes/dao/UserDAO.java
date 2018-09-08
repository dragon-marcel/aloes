package com.example.aloes.dao;

import com.example.aloes.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDAO extends CrudRepository<User,Long> {

}
