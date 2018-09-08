package com.example.aloes.repository;
import com.example.aloes.entity.User;
import java.util.List;

public interface UsersR {
     List<User> findAllUser();
     void deleteUserbyId(Long id);
     void saveUser(User users);

}
