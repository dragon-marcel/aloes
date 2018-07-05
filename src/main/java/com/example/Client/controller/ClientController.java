package com.example.Client.controller;

import com.example.Client.entity.Client;

import com.example.Client.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    IClientService clientService;

    @RequestMapping(value = "/list")
    public String listClient(@RequestParam(name = "search", required = false)String search,
                             Model model){
        List<Client> client = clientService.findAll();
        model.addAttribute("title","List clients");
        List<Client>searchList = clientService.searchClient(search);

        model.addAttribute("list",searchList);
        if (search !=null){
            model.addAttribute("list",searchList);
        }else {
            model.addAttribute("list",client);
        }
        return "listClient" ;
    }

    @RequestMapping("form")
    public String form(Model model){
        model.addAttribute("ClientNew",new Client());
        model.addAttribute("title","New client");
        return "form";
    }

    @RequestMapping("form/{id}")
    public String edit(@PathVariable("id") Long id,RedirectAttributes flash, Model model){
        Client client  = null;
        if(id>0){
            client = clientService.findOne(id);

        }else{
            flash.addFlashAttribute("danger","client Id not exist!");
            return "redirect:list";
        }
        model.addAttribute("ClientNew",client);
        return "form";
    }
@RequestMapping("client/{id}")
public String clientDetails(@PathVariable("id")Long id,Model model,RedirectAttributes flash){
            model.addAttribute("title","client details");
            Client client = clientService.findOne(id);
            if(client == null){
                flash.addFlashAttribute("danger","Client not exist");
                return "redirect:/list";
            }
            model.addAttribute("clientDetails",client);
        return "clientDetails";
}
    @RequestMapping("client/delate/{id}")
    public String deleate(@PathVariable("id")Long id,RedirectAttributes flash){
        Client client = clientService.findOne(id);

        if(client == null){
            flash.addFlashAttribute("danger","Error delate,Client not exist!");
            return "redirect:/list";
        }
        clientService.delate(id);
        flash.addFlashAttribute("success","Delate client");
        return "redirect:/list";
    }


    @RequestMapping(value = "form",method = RequestMethod.POST)
    public String saveClient(@Valid @ModelAttribute(value="ClientNew")Client ClientNew, BindingResult result, RedirectAttributes flash, SessionStatus sessionStatus){

        if (result.hasErrors()) {
            return "form";
        }
        String messageAlert;
       if(ClientNew.getId() == null){
           messageAlert = "Created new client!";
       }else{
           messageAlert = "Edited client!" + ClientNew.getName();
       }


            clientService.save(ClientNew);

            sessionStatus.setComplete();
            flash.addFlashAttribute("success",messageAlert);
            return"redirect:list";


    }}