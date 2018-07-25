package com.example.Client.repository;

import com.example.Client.entity.Client;

import java.util.List;

public interface IClientRepository {
     List<Client> findAllClient();
     void saveClient(Client client);
     Client findOneClientById(Long Id);
     void  deleteClientById(Long id);
     List<Client> searchClientByName(String name);

}
