package com.example.aloes.dao;

import com.example.aloes.entity.Massage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MassageDAO extends CrudRepository<Massage,Long> {

   @Query(value = "select m from Massage m where m.name like %?1%")
    List<Massage>findMassageByName(String term);

}
