package com.example.Client.dao;

import com.example.Client.entity.Visit;
import org.springframework.data.repository.CrudRepository;

public interface IVisitDAO extends CrudRepository<Visit,Long> {

}
