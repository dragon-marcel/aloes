package com.example.aloes.controller;

import com.example.aloes.domain.EmailSender;
import com.example.aloes.entity.Client;
import com.example.aloes.entity.ItemVisit;
import com.example.aloes.entity.Massage;
import com.example.aloes.entity.Visit;
import com.example.aloes.repository.ClientR;
import com.example.aloes.repository.MassageR;
import com.example.aloes.repository.VisitRepository;
import com.example.aloes.service.VisitService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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
import java.util.Locale;

@Controller
@SessionAttributes("newVisit")
public class VisitController {

    @Autowired
    private ClientR clientR;
    @Autowired
    private VisitRepository visitRepository;
    @Autowired
    private VisitService visitService;
    @Autowired
    private EmailSender emailSender;
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private MassageR massageService;
    @Autowired
    private MessageSource messageSource;

    private final org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "visits")
    public String findAllVisit(Model model,
                               Locale locale){

        List<Visit> visits = visitRepository.allVisitList();
        model.addAttribute("list",visits);
        model.addAttribute("title",messageSource.getMessage("text.visit.listVisit.title",null,locale));

        return "visit/listVisit";
    }

    @GetMapping(value ="visit/{id}")
    public String visitDetails(@PathVariable("id")Long id,
                               Model model,
                               RedirectAttributes flash,
                               Locale locale){

        Visit visit = visitRepository.findVisitById(id);

        if (visit == null){
            flash.addFlashAttribute("danger",messageSource.getMessage("text.visit.detailsVisit.danger.notExist",null,locale));
            return "redirect:/";
        }
        List<ItemVisit> Items = visit.getItems();
        model.addAttribute("title",messageSource.getMessage("text.visit.detailsVisit.title",null,locale));
        model.addAttribute("Visit",visit);
        model.addAttribute("item",Items);

        return "visit/detailsVisit";

    }

    @GetMapping(value = "formVisit/{id}")
    public String newVisit(@PathVariable("id") Long id,
                              Model model,
                           RedirectAttributes flash,
                           Locale locale) {

        Client client = clientR.findOneClientById(id);

        if (client == null) {
            flash.addFlashAttribute("danger",messageSource.getMessage("text.visit.detailsVisit.danger.notExist",null,locale));
            return "redirect:/clients";
        }
        Visit visit = new Visit();
        visit.setClient(client);
        model.addAttribute("newVisit", visit);
        model.addAttribute("title", messageSource.getMessage("text.visit.formVisit.title",null,locale));

        return "visit/formVisit";
    }

    @PostMapping(value = "formVisit")
    public String saveVisit(@Valid @ModelAttribute("newVisit") Visit newVisit,
                            BindingResult result,
                            RedirectAttributes flash,
                            SessionStatus sessionStatus,
                            Locale locale,
                            @RequestParam(name="item_id[]",required = false) Long[] itemId,
                            @RequestParam(name = "quantity[]",required = false) Integer[] quantity)
    {
        DateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat formatTime = new SimpleDateFormat("kk");

        Date visitTime =newVisit.getVisitTime();
        String visitFormatDate = formatDate.format(newVisit.getVisitDate());
        String visitFormatTime = formatTime.format(visitTime);

        if (!visitService.ifBusy(visitFormatDate,visitTime)){
            flash.addFlashAttribute("danger",visitFormatDate +" "+
                    messageSource.getMessage( "text.visit.formVisit.danger.hour",null,locale)
                    + visitFormatTime +" "+ messageSource.getMessage("text.visit.formVisit.danger.info",null,locale));
            return "redirect:/formVisit/"+ newVisit.getClient().getId();

        }else {

            if (result.hasErrors()) {
                flash.addFlashAttribute("danger","ERROR");
                return "redirect:/formVisit/"+ newVisit.getClient().getId();

            }

            for(int a = 0; a < itemId.length; a++){
                Massage massage = massageService.findMassageById(itemId[a]);
                ItemVisit itemVisit = new ItemVisit();
                itemVisit.setMassage(massage);
                itemVisit.setQuantity(quantity[a]);
                newVisit.addItems(itemVisit);
                log.info("id"+ itemId[a].toString() + "quantity:"+ quantity[a].toString());
            }
            visitRepository.saveVisit(newVisit);
            flash.addFlashAttribute("success",messageSource.getMessage("text.visit.detailsVisit.success.new",null,locale)+
                    " \""+ newVisit.getDescription()+ "\"");
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
    public String openVisits(Model model,
                             Locale locale){

        List<Visit> openVisits = visitRepository.getVisitOpen();

        model.addAttribute("title",messageSource.getMessage("text.visit.openVisit.title",null,locale));
        model.addAttribute("openVisit",openVisits);

        return "visit/openVisit";
    }

    @RequestMapping(value = "/visitsClose")
    public String closeVisits(Model model,
                              Locale locale){

        List<Visit> closeVisits = visitRepository.getVisitClose();

        model.addAttribute("title",messageSource.getMessage("text.visit.closeVisit.title",null,locale));
        model.addAttribute("closeVisit",closeVisits);

        return "visit/closeVisit";
    }
    @GetMapping(value = "/load-massage/{term}",produces = {"application/json"})
    public @ResponseBody List<Massage> searchMassage(@PathVariable String term){

        return massageService.findAllMassageByName(term);
    }

    @PostMapping(value = "visit/edit")
    public String closeVisit(Visit visit,@RequestParam("massage[]")Long[] id,
                             @RequestParam("quantity[]")Integer[]quantity,
                             RedirectAttributes flash,
                             Locale locale){

        for(int a = 0; a < id.length; a++) {
            Massage massage = massageService.findMassageById(id[a]);
            ItemVisit list = new ItemVisit();
            list.setMassage(massage);
            list.setQuantity(quantity[a]);
            visit.addItems(list);
        }
        visitRepository.saveVisit(visit);
        flash.addFlashAttribute("success",messageSource.getMessage("text.visit.visitDetails.changed",null,locale));

     return "redirect:/visit/"+visit.getId();
    }

    @GetMapping(value ="visit/{id}/sendEmail")
    public String sendEmail(RedirectAttributes flash,
                            @PathVariable("id") Long id,
                            Locale locale){

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
        flash.addFlashAttribute("success",messageSource.getMessage("text.visit.detailsVisit.sendedMail",null,locale));

        return "redirect:/visit/"+id;
    }
}
