package com.example.aloes.controller;

import com.example.aloes.domain.TimeDate;
import com.example.aloes.domain.TimeSession;
import com.example.aloes.entity.Visit;
import com.example.aloes.repository.VisitR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    VisitR visitR;
    @Autowired
    private TimeDate timeDate;
    @Autowired
    private TimeSession timeSession;

    @RequestMapping("/")
    public String index(Model model){

        List<Visit> visitsToday = visitR.getVisitbyData();

        model.addAttribute("date",timeDate.getDatewithoutTime());
        model.addAttribute("title","Aloes Gabinet Odnowy");
        model.addAttribute("list",visitsToday);
        model.addAttribute("time",timeSession.getTime());

        return "index";
    }
}
