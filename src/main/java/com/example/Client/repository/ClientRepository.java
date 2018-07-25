package com.example.Client.repository;

import com.example.Client.entity.Client;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ClientRepository implements IClientRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public List<Client> findAllClient() {

        return em.createQuery("from Client").getResultList();
    }

    @Transactional
    @Override
    public void saveClient(Client client) {
        if(client.getId() != null && client.getId()>0) {
            em.merge(client);
        }else {
            em.persist(client);
        }}

    @Override
    @Transactional
    public Client findOneClientById(Long id) {
        Client client = em.find(Client.class ,id);
        return client;
    }

    @Override
    @Transactional
    public void deleteClientById(Long id) {
        em.remove(findOneClientById(id));
    }


    @Override
    public List<Client> searchClientByName(String name) {
        List<Client>getMassageByName = em.createQuery("from Client client where UPPER(client.name) like UPPER(:name)OR UPPER(client.surname) like UPPER(:name) " +
                "OR UPPER(client.email) like UPPER(:name)")
                .setParameter("name",'%' +name+ '%')
                .getResultList();
                return getMassageByName;
    }
}


