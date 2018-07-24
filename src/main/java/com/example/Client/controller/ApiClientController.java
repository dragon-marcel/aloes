package com.example.Client.controller;

import com.example.Client.entity.Client;
import com.example.Client.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api")
public class ApiClientController {
@Autowired
private ClientService clientService;

    @RequestMapping(value = "/client",method = RequestMethod.GET)
    private @ResponseBody List<Client> getAllClient(){
        return clientService.findAll();
    }

    @RequestMapping(value = "/client/{id}",method = RequestMethod.GET)
    private @ResponseBody Client getClient(@PathVariable("id") Long id){
        return clientService.findOne(id);
    }
    @RequestMapping(value = "/client",method = RequestMethod.POST)
    private @ResponseBody String getAllClient(Client client){
        clientService.save(client);
        return "Add new client";
    }
    @RequestMapping(value = "/client/{id}",method = RequestMethod.DELETE)
    private @ResponseBody String deleteClientbyId(@PathVariable("id") Long id){
        clientService.delete(id);
        return "Delete client";
    }



}
