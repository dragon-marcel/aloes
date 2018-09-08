package com.example.aloes.repository;
import com.example.aloes.entity.Client;
import java.util.List;

public interface ClientR {

     List<Client> findAllClient();
     void saveClient(Client client);
     Client findOneClientById(Long id);
     void deleteClientById(Long id);
     List<Client> searchClientByName(String name);
}
