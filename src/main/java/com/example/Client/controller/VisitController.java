package com.example.Client.controller;

import com.example.Client.domain.EmailSender;
import com.example.Client.entity.Client;
import com.example.Client.entity.ItemVisit;
import com.example.Client.entity.Massage;
import com.example.Client.entity.Visit;
import com.example.Client.service.ClientService;
import com.example.Client.service.VisitService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Controller
@RequestMapping(value = "/visit")
@SessionAttributes("newVisit")
public class VisitController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private VisitService visitService;

    @Autowired
    private EmailSender emailSender;
    @Autowired
    private TemplateEngine templateEngine;

    private final org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping(value ="visitDetails/{idVisit}")
    public String visitDetails(@PathVariable("idVisit")Long id,
                               Model model,RedirectAttributes flash){
        Visit visit =clientService.findVisitById(id);

        if (visit == null){
            flash.addFlashAttribute("danger","Visit not exist");
            return "redirect:/";
        }
        List<ItemVisit> listItem = visit.getItems();
        model.addAttribute("title","Edit Visit");
        model.addAttribute("Visit",visit);
        model.addAttribute("item",listItem);
        return "visit/visitDetails";

    }
    @GetMapping(value ="visitDetails/{idVisit}/sendEmail")
    public String sendEmail(Model model,RedirectAttributes flash,
                            @PathVariable("idVisit") Long id){
       Visit visit = clientService.findVisitById(id);

       Client client = visit.getClient();
       String  email = client.getEmail();
       String title = "Aloes-gabinet odnowy reminder about visit " + visit.getDescription()+ "";

        Context context = new Context();
        context.setVariable("header", "Aloes-gabinet odnowy");
        context.setVariable("title", "Aloes-gabinet odnowy reminder about visit \"" + visit.getDescription()+ "\"");
        context.setVariable("description", visit.getItems());

        String body = templateEngine.process("layout/email", context);
        if (emailSender.sendEmail(email,title,body)){
            visit.setSendEmail(true);
            clientService.saveVisit(visit);

        }

        flash.addFlashAttribute("success","Success,email send");

        return "redirect:/visit/visitDetails/"+id;
    }

    @GetMapping(value = "/form/{clientId}")
    public String createVisit(@PathVariable("clientId") Long id,
                              Model model,
                              RedirectAttributes flash,
                              SessionStatus sessionStatus) {
        Client client = clientService.findOne(id);
        if (client == null) {
            flash.addFlashAttribute("danger", "Client not exist");
            return "redirect:/list";
        }
        Visit visit = new Visit();
        visit.setClient(client);
        sessionStatus.setComplete();
        model.addAttribute("newVisit", visit);
        model.addAttribute("title", "NEW VISIT");
        return "visit/form";
    }
@GetMapping(value = "/load-massage/{term}",produces = {"application/json"})

public @ResponseBody List<Massage>searchMassage(@PathVariable String term){
        return clientService.findMassageByName( term);
}

@PostMapping(value = "/form")
    public String saveVisit(@Valid Visit visit,
                            BindingResult result,
                            RedirectAttributes flash,
                            SessionStatus sessionStatus,
                            @RequestParam(name="item_id[]",required = false) Long[] ItemId,
                            @RequestParam(name = "quantity[]",required = false) Integer[] quantity)
    {
        DateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat formatTime = new SimpleDateFormat("hh:mm:ss");

        String visitDate = formatDate.format(visit.getVisitDate());
        String visitTime = formatTime.format(visit.getVisitTime());

     if (visitService.checkifBusyDataandTime(visitDate,visitTime)== false){
         flash.addFlashAttribute("danger","Date and time visit it is busy,please chose oder time.");
         return "redirect:/visit/form/"+ visit.getClient().getId();

     }else {

        if (result.hasErrors()) {
            flash.addFlashAttribute("danger","ERROR");
            return "redirect:/visit/form/"+ visit.getClient().getId();

        }

        for(int a = 0; a < ItemId.length; a++){
        Massage massage = clientService.findMassageById(ItemId[a]);
            ItemVisit itemVisit = new ItemVisit();
            itemVisit.setMassage(massage);
            itemVisit.setQuantity(quantity[a]);
            visit.addItems(itemVisit);
            log.info("id"+ ItemId[a].toString() + "quantity:"+ quantity[a].toString());
        }
        clientService.saveVisit(visit);
        flash.addFlashAttribute("success","Add new Visit" + " \""+ visit.getDescription()+ "\"");
        sessionStatus.setComplete();
        return "redirect:/client/" + visit.getClient().getId();
    }}
    @GetMapping("/delate/{id}")
    public String delateVisit(@PathVariable("id")Long id,
                              RedirectAttributes  flash){
        Visit visit = clientService.findVisitById(id);
        if (visit != null){
            clientService.delateVisit(id);
            flash.addFlashAttribute("success","Delate visit");
            return "redirect:/client/" + visit.getClient().getId();

        }
        flash.addFlashAttribute("danger","error delate visit");
        return "redirect:/client/" + visit.getClient().getId();
    }
    @RequestMapping(value = "/list")
    public String AllVisitlist(Model model){
        List<Visit> visitslist = visitService.allVisitList();
        model.addAttribute("list",visitslist);
        model.addAttribute("title","List all visits");
        return "visit/list";
    }
    @RequestMapping(value = "/open")
    public String listOpenVisit(Model model){
        List<Visit>openVisit = visitService.getVisitOpen();
        model.addAttribute("title","Open visits");
        model.addAttribute("openVisit",openVisit);
        return "visit/openVisit";
    }
    @RequestMapping(value = "/close")
    public String listCloseVisit(Model model){
        List<Visit>closeVisit = visitService.getVisitClose();
        model.addAttribute("title","Close visits");
        model.addAttribute("closeVisit",closeVisit);
        return "visit/closeVisit";
    }


    @PostMapping(value = "edit")
    public String closeVisit(Visit visit,@RequestParam("massage[]")Long[] id,
                             @RequestParam("quantity[]")Integer[]quantity,
                             RedirectAttributes flash){

        for(int a = 0; a < id.length; a++) {
            Massage massage = clientService.findMassageById(id[a]);
            ItemVisit list = new ItemVisit();
            list.setMassage(massage);
            list.setQuantity(quantity[a]);
            visit.addItems(list);
        }
        clientService.saveVisit(visit);
        flash.addFlashAttribute("success","You changed status");
     return "redirect:/visit/visitDetails/"+visit.getId();
    }
}
