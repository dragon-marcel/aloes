package com.example.Client.controller;

import com.example.Client.dao.IUsers;
import com.example.Client.dao.IVisitDAO;
import com.example.Client.entity.Visit;
import com.example.Client.service.IClientService;
import com.example.Client.service.IUsersService;
import com.example.Client.service.IVisitService;
import com.example.Client.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class StatisticController {

    @Autowired
    private IVisitService iVisitService;

    @Autowired
    private IClientService iClientService;
    @Autowired
    private IUsersService iUsersService;
    @Autowired
    private  StatisticService statisticService;
    @RequestMapping(value = "/statistic")
    public String statistic(Model model){
        model.addAttribute("title","Statistic Aloes-Gabinet Odnowy");
        model.addAttribute("quantityVisits",iVisitService.allVisitList().size());
        model.addAttribute("quantityVisitsOpen",iVisitService.getVisitClose().size());
        model.addAttribute("quantityVisitsClose",iVisitService.getVisitOpen().size());
        model.addAttribute("quantityClient",iClientService.findAll().size());
        model.addAttribute("quantityUser",iUsersService.findAll().size());
        model.addAttribute("quantityTotalValue",statisticService.getTotalValue());
        model.addAttribute("quantityCloseValue",statisticService.getCloseValue());
        return "statistic";
    }
}
