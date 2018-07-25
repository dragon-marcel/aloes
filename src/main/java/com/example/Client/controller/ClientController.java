package com.example.Client.controller;

import com.example.Client.entity.Client;

import com.example.Client.repository.IClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@SessionAttributes("ClientNew")
public class ClientController {

    @Autowired
    IClientRepository clientService;

    @RequestMapping(value = "/clients")
    public String findAllClient(@RequestParam(name = "search", required = false) String search,
                                Model model) {

        List<Client> listClients = clientService.findAllClient();
        List<Client> searchList = clientService.searchClientByName(search);

        model.addAttribute("list", searchList);
        if (search != null) {
            model.addAttribute("list", searchList);
        } else {
            model.addAttribute("list", listClients);
        }
        model.addAttribute("title", "List clients");

        return "client/listClient";
    }

    @RequestMapping(value = "formClient")
    public String newClient(Model model) {
        model.addAttribute("NewClient", new Client());
        model.addAttribute("title", "New client");
        return "/client/formClient";
    }

    @RequestMapping("formClient/{id}")
    public String editClient(@PathVariable("id") Long id, RedirectAttributes flash, Model model) {
        Client client = null;
        if (id > 0) {
            client = clientService.findOneClientById(id);

        } else {
            flash.addFlashAttribute("danger", "client Id not exist!");
            return "redirect:clients";
        }
        model.addAttribute("NewClient", client);
        return "/client/formClient";
    }

    @RequestMapping("client/{id}")
    public String clientDetails(@PathVariable("id") Long id, Model model, RedirectAttributes flash) {

        model.addAttribute("title", "Client details:");
        model.addAttribute("visit", "Visit");


        Client client = clientService.findOneClientById(id);
        if (client == null) {
            flash.addFlashAttribute("danger", "Client not exist");
            return "redirect:/clients";
        }
        model.addAttribute("clientDetails", client);
        return "/client/detailsClient";
    }

    @RequestMapping("client/delete/{id}")
    public String deleteClient(@PathVariable("id") Long id, RedirectAttributes flash) {
        Client client = clientService.findOneClientById(id);

        if (client == null) {
            flash.addFlashAttribute("danger", "Error delete,Client not exist!");
            return "redirect:/clients";
        }
        clientService.deleteClientById(id);
        flash.addFlashAttribute("success", "Delete client");
        return "redirect:/clients";
    }


    @RequestMapping(value = "formClient", method = RequestMethod.POST)
    public String saveClient(@Valid @ModelAttribute(value = "NewClient") Client NewClient,
                             BindingResult result, RedirectAttributes flash,
                             SessionStatus sessionStatus) {

        if (result.hasErrors()) {
            return "/client/formClient";
        }

        String messageAlert;

        if (NewClient.getId() == null) {
            messageAlert = "Created new client!";
        } else {
            messageAlert = "Edited client!" + NewClient.getName();
        }
        clientService.saveClient(NewClient);
        sessionStatus.setComplete();
        flash.addFlashAttribute("success", messageAlert);
        return "redirect:clients";
    }
}
