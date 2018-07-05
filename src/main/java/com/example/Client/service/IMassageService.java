package com.example.Client.service;

import com.example.Client.entity.Massage;
import org.springframework.data.repository.CrudRepository;

import java.io.IOException;
import java.util.List;

public interface IMassageService {
    public List<Massage> getAllMassage();
    public void saveMassage(Massage massage);
    public void delateMassage(Long id);
    public Massage getMassage(Long id);
}
