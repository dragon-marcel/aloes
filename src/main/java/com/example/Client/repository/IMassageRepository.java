package com.example.Client.repository;

import com.example.Client.entity.Massage;
import java.util.List;

public interface IMassageRepository {
     List<Massage> getAllMassage();
     void saveMassage(Massage massage);
     void deleteMassageById(Long id);
     Massage getMassageById(Long id);
     List<Massage>findAllMassageByName(String term);
     Massage findMassageById(Long id);
}
