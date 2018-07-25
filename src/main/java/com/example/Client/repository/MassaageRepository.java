package com.example.Client.repository;

import com.example.Client.dao.IMassageDAO;
import com.example.Client.entity.Massage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MassaageRepository implements IMassageRepository {

    @Autowired
    IMassageDAO iMassageDAO;

    @Override
    public List<Massage> getAllMassage() {
        List<Massage>listMassage = (List<Massage>) iMassageDAO.findAll();
        return listMassage;
    }

    @Override
    public void saveMassage(Massage massage) {
        iMassageDAO.save(massage);
    }

    @Override
    public void deleteMassageById(Long id)  {
        iMassageDAO.deleteById(id);
    }

    @Override
    public Massage getMassageById(Long id) {
        return iMassageDAO.findById(id).orElse(null);
    }
    @Override
    public List<Massage> findAllMassageByName(String term) {
        return iMassageDAO.findMassageByName(term);
    }

    @Override
    public Massage findMassageById(Long id) {
        return iMassageDAO.findById(id).orElse(null);
    }

}
