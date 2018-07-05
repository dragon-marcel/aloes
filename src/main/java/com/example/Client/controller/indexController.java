package com.example.Client.controller;

import com.example.Client.domain.TimeDate;
import com.example.Client.domain.TimeSession;
import com.example.Client.entity.Visit;
import com.example.Client.service.IVisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class indexController {
    @Autowired
    IVisitService iVisitService;
    @Autowired
    private TimeDate timeDate;
    @Autowired
    private TimeSession timeSession;
    @RequestMapping("/")
    public String index(Model model){
        List<Visit> listVisit = iVisitService.allVisitList();
        List<Visit> listData = iVisitService.getVisitbyData();
        model.addAttribute("date",timeDate.getDatewithoutTime());
        model.addAttribute("title","Aloes Gabinet Odnowy");
        model.addAttribute("list",listData);
        model.addAttribute("time",timeSession.getTime());
        return "index";
    }
}
