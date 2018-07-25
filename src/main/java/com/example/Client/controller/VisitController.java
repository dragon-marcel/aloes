package com.example.Client.controller;

import com.example.Client.domain.EmailSender;
import com.example.Client.entity.Client;
import com.example.Client.entity.ItemVisit;
import com.example.Client.entity.Massage;
import com.example.Client.entity.Visit;
import com.example.Client.repository.ClientRepository;
import com.example.Client.repository.IMassageRepository;
import com.example.Client.repository.VisitRepository;
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

import javax.validation.Valid;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@SessionAttributes("newVisit")
public class VisitController {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private VisitRepository visitRepository;
    @Autowired
    private VisitService visitService;
    @Autowired
    private EmailSender emailSender;
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private IMassageRepository massageService;

    private final org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "visits")
    public String FindAllVisit(Model model){
        List<Visit> visitslist = visitRepository.allVisitList();
        model.addAttribute("list",visitslist);
        model.addAttribute("title","List all visits");
        return "visit/listVisit";
    }

    @GetMapping(value ="visit/{id}")
    public String visitDetails(@PathVariable("id")Long id,
                               Model model,RedirectAttributes flash){

        Visit visit = visitRepository.findVisitById(id);

        if (visit == null){
            flash.addFlashAttribute("danger","Visit not exist");
            return "redirect:/";
        }
        List<ItemVisit> listItem = visit.getItems();
        model.addAttribute("title","Edit visit");
        model.addAttribute("Visit",visit);
        model.addAttribute("item",listItem);
        return "visit/detailsVisit";

    }

    @GetMapping(value = "formVisit/{id}")
    public String newVisit(@PathVariable("id") Long id,
                              Model model,
                              RedirectAttributes flash
                              ) {
        Client client = clientRepository.findOneClientById(id);
        if (client == null) {
            flash.addFlashAttribute("danger", "Client not exist");
            return "redirect:/clients";
        }
        Visit visit = new Visit();
        visit.setClient(client);
        model.addAttribute("newVisit", visit);
        model.addAttribute("title", "NEW VISIT");
        return "visit/formVisit";
    }
    @PostMapping(value = "formVisit")
    public String saveVisit(@Valid @ModelAttribute("newVisit") Visit newVisit,
                            BindingResult result,
                            RedirectAttributes flash,
                            SessionStatus sessionStatus,
                            @RequestParam(name="item_id[]",required = false) Long[] ItemId,
                            @RequestParam(name = "quantity[]",required = false) Integer[] quantity)
    {
        DateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat formatTime = new SimpleDateFormat("kk");

        Date visitTime =newVisit.getVisitTime();
        String visitFormatDate = formatDate.format(newVisit.getVisitDate());
        String visitFormatTime = formatTime.format(visitTime);

        if (visitService.checkIfBusyDataAndTime(visitFormatDate,visitTime)== false){
            flash.addFlashAttribute("danger",visitFormatDate + " at hour "+ visitFormatTime + " it'is busy,please chose different time.");
            return "redirect:/formVisit/"+ newVisit.getClient().getId();

        }else {

            if (result.hasErrors()) {
                flash.addFlashAttribute("danger","ERROR");
                return "redirect:/formVisit/"+ newVisit.getClient().getId();

            }

            for(int a = 0; a < ItemId.length; a++){
                Massage massage = massageService.findMassageById(ItemId[a]);
                ItemVisit itemVisit = new ItemVisit();
                itemVisit.setMassage(massage);
                itemVisit.setQuantity(quantity[a]);
                newVisit.addItems(itemVisit);
                log.info("id"+ ItemId[a].toString() + "quantity:"+ quantity[a].toString());
            }
            visitRepository.saveVisit(newVisit);
            flash.addFlashAttribute("success","Add new Visit" + " \""+ newVisit.getDescription()+ "\"");
            sessionStatus.setComplete();
            return "redirect:/client/" + newVisit.getClient().getId();
        }
    }

    @GetMapping("/visit/delete/{id}")
    public String deleteVisit(@PathVariable("id")Long id,
                              RedirectAttributes  flash){
        Visit visit = visitRepository.findVisitById(id);
        if (visit != null){
            visitRepository.deleteVisitById(id);
            flash.addFlashAttribute("success","Delete visit");
            return "redirect:/client/" + visit.getClient().getId();

        }
        flash.addFlashAttribute("danger","error delete visit");
        return "redirect:/client/" + visit.getClient().getId();
    }

    @RequestMapping(value = "/visitsOpen")
    public String listOpenVisit(Model model){
        List<Visit>openVisit = visitRepository.getVisitOpen();
        model.addAttribute("title","Open visits");
        model.addAttribute("openVisit",openVisit);
        return "visit/openVisit";
    }

    @RequestMapping(value = "/visitsClose")
    public String listCloseVisit(Model model){
        List<Visit>closeVisit = visitRepository.getVisitClose();
        model.addAttribute("title","Close visits");
        model.addAttribute("closeVisit",closeVisit);
        return "visit/closeVisit";
    }
    @GetMapping(value = "/load-massage/{term}",produces = {"application/json"})
    public @ResponseBody List<Massage>searchMassage(@PathVariable String term){
        return massageService.findAllMassageByName(term);
    }

    @PostMapping(value = "visit/edit")
    public String closeVisit(Visit visit,@RequestParam("massage[]")Long[] id,
                             @RequestParam("quantity[]")Integer[]quantity,
                             RedirectAttributes flash){

        for(int a = 0; a < id.length; a++) {
            Massage massage = massageService.findMassageById(id[a]);
            ItemVisit list = new ItemVisit();
            list.setMassage(massage);
            list.setQuantity(quantity[a]);
            visit.addItems(list);
        }
        visitRepository.saveVisit(visit);
        flash.addFlashAttribute("success","You changed status");
     return "redirect:/visit/"+visit.getId();
    }

    @GetMapping(value ="visit/{id}/sendEmail")
    public String sendEmail(RedirectAttributes flash,
                            @PathVariable("id") Long id){
        Visit visit = visitRepository.findVisitById(id);

        Client client = visit.getClient();
        String  email = client.getEmail();
        String title = "Aloes-gabinet odnowy reminder about visit " + visit.getDescription()+ "";

        Context context = new Context();
        context.setVariable("massageDetails",visit.getItems());
        context.setVariable("header", "Aloes-gabinet odnowy");
        context.setVariable("description", visit.getDescription());
        context.setVariable("name", visit.getClient().getName() + ' ' + visit.getClient().getSurname());
        context.setVariable("dateVisit", visit.getVisitDate().toString());
        context.setVariable("timeVisit", visit.getVisitTime().toString());

        String body = templateEngine.process("layout/email", context);

        if (emailSender.sendEmail(email,title,body)){
            visit.setSendEmail(true);
            visitRepository.saveVisit(visit);
        }
        flash.addFlashAttribute("success","Success,email send");
        return "redirect:/visit/"+id;
    }
}
