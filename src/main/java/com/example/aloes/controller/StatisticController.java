package com.example.aloes.controller;

import com.example.aloes.repository.ClientR;
import com.example.aloes.repository.UsersR;
import com.example.aloes.repository.VisitR;
import com.example.aloes.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StatisticController {

    @Autowired
    private VisitR visitR;

    @Autowired
    private ClientR iClientRepository;
    @Autowired
    private UsersR usersR;
    @Autowired
    private  StatisticService statisticService;
    @RequestMapping(value = "/statistic")
    public String statistic(Model model){
        model.addAttribute("title","Statistic Aloes-Gabinet Odnowy");
        model.addAttribute("quantityVisits", visitR.allVisitList().size());
        model.addAttribute("quantityVisitsOpen", statisticService.getQuantityOpernVisit());
        model.addAttribute("quantityVisitsClose", statisticService.getQuantityCloseVisit());
        model.addAttribute("quantityClient", iClientRepository.findAllClient().size());
        model.addAttribute("quantityUser", usersR.findAllUser().size());
        model.addAttribute("quantityTotalValue",statisticService.getTotalValue());
        model.addAttribute("quantityCloseValue",statisticService.getCloseValue());
        return "statistic";
    }
}
