package com.example.Client.dao;


import com.example.Client.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

@Repository
public interface IClientDAO extends CrudRepository<Client,Long>{
}