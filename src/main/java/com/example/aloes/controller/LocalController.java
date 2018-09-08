package com.example.aloes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LocalController {

    @GetMapping("/local")
    public String local(HttpServletRequest request){

        String url = request.getHeader("referer");

        return "redirect:".concat(url);
    }
}
