package com.example.Client.repository;
import com.example.Client.entity.User;
import java.util.List;

public interface IUsersRepository {
     List<User> findAllUser();
     void deleteUserbyId(Long id);
     void saveUser(User users);

}
