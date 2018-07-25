package com.example.Client.controller;

import com.example.Client.repository.IClientRepository;
import com.example.Client.repository.IUsersRepository;
import com.example.Client.repository.IVisitRepository;
import com.example.Client.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StatisticController {

    @Autowired
    private IVisitRepository iVisitRepository;

    @Autowired
    private IClientRepository iClientRepository;
    @Autowired
    private IUsersRepository iUsersRepository;
    @Autowired
    private  StatisticService statisticService;
    @RequestMapping(value = "/statistic")
    public String statistic(Model model){
        model.addAttribute("title","Statistic Aloes-Gabinet Odnowy");
        model.addAttribute("quantityVisits", iVisitRepository.allVisitList().size());
        model.addAttribute("quantityVisitsOpen", iVisitRepository.getVisitClose().size());
        model.addAttribute("quantityVisitsClose", iVisitRepository.getVisitOpen().size());
        model.addAttribute("quantityClient", iClientRepository.findAllClient().size());
        model.addAttribute("quantityUser", iUsersRepository.findAllUser().size());
        model.addAttribute("quantityTotalValue",statisticService.getTotalValue());
        model.addAttribute("quantityCloseValue",statisticService.getCloseValue());
        return "statistic";
    }
}
