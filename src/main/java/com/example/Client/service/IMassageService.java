package com.example.Client.service;

import com.example.Client.entity.Massage;
import java.util.List;

public interface IMassageService {
     List<Massage> getAllMassage();
     void saveMassage(Massage massage);
     void delateMassage(Long id);
     Massage getMassage(Long id);
}
