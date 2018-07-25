package com.example.Client.controller;

import com.example.Client.domain.TimeDate;
import com.example.Client.domain.TimeSession;
import com.example.Client.entity.Visit;
import com.example.Client.repository.IVisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class indexController {
    @Autowired
    IVisitRepository iVisitRepository;
    @Autowired
    private TimeDate timeDate;
    @Autowired
    private TimeSession timeSession;
    @RequestMapping("/")
    public String index(Model model){

        List<Visit> listData = iVisitRepository.getVisitbyData();

        model.addAttribute("date",timeDate.getDatewithoutTime());
        model.addAttribute("title","Aloes Gabinet Odnowy");
        model.addAttribute("list",listData);
        model.addAttribute("time",timeSession.getTime());
        return "index";
    }
}
