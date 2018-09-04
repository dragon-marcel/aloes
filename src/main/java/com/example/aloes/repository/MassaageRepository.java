package com.example.aloes.repository;

import com.example.aloes.dao.MassageDAO;
import com.example.aloes.entity.Massage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MassaageRepository implements MassageR {

    @Autowired
    MassageDAO massageDAO;

    @Override
    public List<Massage> getAllMassage() {
        List<Massage>listMassage = (List<Massage>) massageDAO.findAll();
        return listMassage;
    }

    @Override
    public void saveMassage(Massage massage) {
        massageDAO.save(massage);
    }

    @Override
    public void deleteMassageById(Long id)  {
        massageDAO.deleteById(id);
    }

    @Override
    public Massage getMassageById(Long id) {
        return massageDAO.findById(id).orElse(null);
    }
    @Override
    public List<Massage> findAllMassageByName(String term) {
        return massageDAO.findMassageByName(term);
    }

    @Override
    public Massage findMassageById(Long id) {
        return massageDAO.findById(id).orElse(null);
    }

}
