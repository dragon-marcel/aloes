package com.example.Client.service;

import com.example.Client.entity.Massage;
import java.util.List;

public interface IMassageService {
     List<Massage> getAllMassage();
     void saveMassage(Massage massage);
     void deleteMassage(Long id);
     Massage getMassage(Long id);
     List<Massage>findMassageByName(String term);
     Massage findMassageById(Long id);
}
