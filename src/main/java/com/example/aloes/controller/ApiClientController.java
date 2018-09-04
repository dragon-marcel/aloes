package com.example.aloes.controller;

import com.example.aloes.entity.Client;
import com.example.aloes.repository.ClientR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api")
public class ApiClientController {
@Autowired
private ClientR clientR;

    @RequestMapping(value = "/client",method = RequestMethod.GET)
    private @ResponseBody List<Client> findAllClient(){
        return clientR.findAllClient();
    }

    @RequestMapping(value = "/client/{id}",method = RequestMethod.GET)
    private @ResponseBody Client findOneClient(@PathVariable("id") Long id){
        return clientR.findOneClientById(id);
    }

    @RequestMapping(value = "/client",method = RequestMethod.POST)
    private @ResponseBody String saveClient(Client client){
        clientR.saveClient(client);
        return "Add new client";
    }
    @RequestMapping(value = "/client/{id}",method = RequestMethod.DELETE)
    private @ResponseBody String deleteClientById(@PathVariable("id") Long id){
        clientR.deleteClientById(id);
        return "Delete client";
    }



}
