package com.example.Client.service;

import com.example.Client.dao.IMassageDao;
import com.example.Client.dao.IVisitDAO;
import com.example.Client.entity.Client;
import com.example.Client.dao.ClientDAO;
import com.example.Client.entity.Massage;
import com.example.Client.entity.Visit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService implements IClientService{
    @Autowired
    private ClientDAO clientDAO;
    @Autowired
    private IMassageDao massageDao;
    @Autowired
    private IVisitDAO iVisitDAO;
    @PersistenceContext
    private EntityManager em;


    public long countRow(){
        long rows =clientDAO.count();
        return rows;
    }

    @Transactional
    public List<Client> findAll() {

        return em.createQuery("from Client").getResultList();
    }


    @Transactional
    @Override
    public void save(Client client) {
        if(client.getId() != null && client.getId()>0) {
            em.merge(client);
        }else {
            em.persist(client);
        }}

    @Override
@Transactional
    public Client findOne(Long id) {
        Client client = em.find(Client.class ,id);
        return client;
    }

    @Override
@Transactional
    public void delate(Long id) {
        em.remove(findOne(id));
    }

    @Override
    public List<Massage> findMassageByName(String term) {
        return massageDao.findMassageByName(term);
    }

    @Override
    public void saveVisit(Visit visit) {
        iVisitDAO.save(visit);
    }


    @Override
    public Massage findMassageById(Long id) {
        return massageDao.findById(id).orElse(null);
    }

    @Override
    public Visit findVisitById(Long id) {
        return iVisitDAO.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delateVisit(Long id) {
        iVisitDAO.deleteById(id);
    }

    @Override
    public List<Client> searchClient(String name) {
        List<Client>getMassageByName = em.createQuery("from Client client where UPPER(client.name) like UPPER(:name)OR UPPER(client.surname) like UPPER(:name) " +
                "OR UPPER(client.email) like UPPER(:name)")
                .setParameter("name",'%' +name+ '%')
                .getResultList();
                return getMassageByName;
    }


}


