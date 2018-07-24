package com.example.Client.service;

import com.example.Client.dao.IMassageDao;
import com.example.Client.dao.IVisitDAO;
import com.example.Client.entity.Client;
import com.example.Client.dao.ClientDAO;
import com.example.Client.entity.Massage;
import com.example.Client.entity.Visit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class ClientService implements IClientService{
    @Autowired
    private ClientDAO clientDAO;
    @Autowired
    private IVisitDAO iVisitDAO;
    @PersistenceContext
    private EntityManager em;


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
    public void delete(Long id) {
        em.remove(findOne(id));
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


