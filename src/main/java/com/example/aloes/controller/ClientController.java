package com.example.aloes.controller;

import com.example.aloes.entity.Client;

import com.example.aloes.repository.ClientR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

@Controller
@SessionAttributes("ClientNew")
public class ClientController {

    @Autowired
    ClientR clientR;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = "/clients")
    public String findAllClient(@RequestParam(name = "search", required = false) String search,
                                Model model,
                                Locale locale) {

        List<Client> Clients = clientR.findAllClient();
        List<Client> searchClients = clientR.searchClientByName(search);

        model.addAttribute("clients", searchClients);
        if (search != null) {
            model.addAttribute("clients", searchClients);
        } else {
            model.addAttribute("clients", Clients);
        }
        model.addAttribute("title", messageSource.getMessage("text.client.title",null,locale));

        return "client/listClient";
    }

    @RequestMapping(value = "formClient")
    public String newClient(Model model,
                            Locale locale) {

        model.addAttribute("NewClient", new Client());
        model.addAttribute("title", messageSource.getMessage("text.client.formClient.title",null,locale));
        return "/client/formClient";
    }

    @RequestMapping("formClient/{id}")
    public String editClient(@PathVariable("id") Long id,
                             RedirectAttributes flash,
                             Model model,
                             Locale locale) {

        Client client;
        if (id > 0) {
            client = clientR.findOneClientById(id);

        } else {
            flash.addFlashAttribute("danger", messageSource
                    .getMessage("text.client.danger.notExist",null,locale));
            return "redirect:clients";
        }
        model.addAttribute("NewClient", client);

        return "/client/formClient";
    }

    @RequestMapping("client/{id}")
    public String clientDetails(@PathVariable("id") Long id,
                                Model model,
                                RedirectAttributes flash,
                                Locale locale) {
     model.addAttribute("title", messageSource.getMessage("text.client.detailsClient.title",null,locale));

        Client client = clientR.findOneClientById(id);
        if (client == null) {
            flash.addFlashAttribute("danger",  messageSource
                    .getMessage("text.client.danger.notExist",null,locale));
            return "redirect:/clients";
        }
        model.addAttribute("clientDetails", client);

        return "/client/detailsClient";
    }

    @RequestMapping("client/delete/{id}")
    public String deleteClient(@PathVariable("id") Long id,
                               RedirectAttributes flash,
                               Locale locale) {

        Client client = clientR.findOneClientById(id);

        if (client == null) {
            flash.addFlashAttribute("danger",  messageSource
                    .getMessage("text.client.danger.notExist",null,locale));

            return "redirect:/clients";
        }
        clientR.deleteClientById(id);
        flash.addFlashAttribute("success",  messageSource
                .getMessage("text.client.success.delete",null,locale));

        return "redirect:/clients";
    }


    @RequestMapping(value = "formClient", method = RequestMethod.POST)
    public String saveClient(@Valid @ModelAttribute(value = "NewClient") Client newClient,
                             BindingResult result,
                             RedirectAttributes flash,
                             SessionStatus sessionStatus,
                             Locale locale) {

        if (result.hasErrors()) {
            return "/client/formClient";
        }

        String messageAlert;

        if (newClient.getId() == null) {
            messageAlert = messageSource.getMessage("text.client.success.newClient",null,locale);
        } else {
            messageAlert = messageSource.getMessage("text.client.success.edit",null,locale) + newClient.getName();
        }
        clientR.saveClient(newClient);
        sessionStatus.setComplete();
        flash.addFlashAttribute("success", messageAlert);

        return "redirect:clients";
    }
}
