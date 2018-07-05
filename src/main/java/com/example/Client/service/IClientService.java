package com.example.Client.service;

import com.example.Client.entity.Client;
import com.example.Client.entity.Massage;
import com.example.Client.entity.Visit;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IClientService {
    public List<Client> findAll();
    public void save(Client client);
    public Client findOne(Long Id);
    public void  delate(Long id);
    public List<Massage>findMassageByName(String term);
    public void saveVisit(Visit visit);
    public Massage findMassageById(Long id);
    public Visit findVisitById(Long id);
    public void delateVisit(Long id);
    public List<Client> searchClient(String name);
}
