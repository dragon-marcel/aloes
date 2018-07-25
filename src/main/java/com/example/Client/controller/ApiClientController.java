package com.example.Client.controller;

import com.example.Client.entity.Client;
import com.example.Client.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api")
public class ApiClientController {
@Autowired
private ClientRepository clientRepository;

    @RequestMapping(value = "/client",method = RequestMethod.GET)
    private @ResponseBody List<Client> findAllClient(){
        return clientRepository.findAllClient();
    }

    @RequestMapping(value = "/client/{id}",method = RequestMethod.GET)
    private @ResponseBody Client findOneClient(@PathVariable("id") Long id){
        return clientRepository.findOneClientById(id);
    }
    @RequestMapping(value = "/client",method = RequestMethod.POST)
    private @ResponseBody String saveClient(Client client){
        clientRepository.saveClient(client);
        return "Add new client";
    }
    @RequestMapping(value = "/client/{id}",method = RequestMethod.DELETE)
    private @ResponseBody String deleteClientById(@PathVariable("id") Long id){
        clientRepository.deleteClientById(id);
        return "Delete client";
    }



}
