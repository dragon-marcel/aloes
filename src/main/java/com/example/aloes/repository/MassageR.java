package com.example.aloes.repository;

import com.example.aloes.entity.Massage;
import java.util.List;

public interface MassageR {
     List<Massage> getAllMassage();
     void saveMassage(Massage massage);
     void deleteMassageById(Long id);
     Massage getMassageById(Long id);
     List<Massage>findAllMassageByName(String term);
     Massage findMassageById(Long id);
}
