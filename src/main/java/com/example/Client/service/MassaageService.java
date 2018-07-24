package com.example.Client.service;

import com.example.Client.dao.IMassageDao;
import com.example.Client.entity.Massage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
@Service
public class MassaageService implements IMassageService {
    @Autowired
    IMassageDao iMassageDao;

    @Override
    public List<Massage> getAllMassage() {
        List<Massage>listMassage = (List<Massage>) iMassageDao.findAll();
        return listMassage;
    }

    @Override
    public void saveMassage(Massage massage) {
        iMassageDao.save(massage);
    }

    @Override
    public void deleteMassage(Long id)  {
        iMassageDao.deleteById(id);
    }

    @Override
    public Massage getMassage(Long id) {
        return iMassageDao.findById(id).orElse(null);
    }
    @Override
    public List<Massage> findMassageByName(String term) {
        return iMassageDao.findMassageByName(term);
    }



    @Override
    public Massage findMassageById(Long id) {
        return iMassageDao.findById(id).orElse(null);
    }

}
