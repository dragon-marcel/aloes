package com.example.Client.dao;

import com.example.Client.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface IUsers  extends CrudRepository<User,Long> {

}
