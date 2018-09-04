package com.example.aloes.dao;


import com.example.aloes.entity.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientDAO extends CrudRepository<Client,Long>{
}