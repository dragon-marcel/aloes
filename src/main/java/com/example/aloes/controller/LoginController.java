package com.example.aloes.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.security.Principal;

@Controller
public class LoginController {
    @RequestMapping(value = "login")
        public String login(RedirectAttributes flash,Principal principal,
                             @RequestParam(value = "error",required = false)String error,
                             @RequestParam(value = "logout",required = false)String logout) {

        if (principal != null) {
            flash.addFlashAttribute("success", "you are Login!SUCCESS!!:)");
            return "redirect:/";
        }
        if (error != null){
            flash.addFlashAttribute("danger","ERROR");
            return "redirect:/login";
        }
        if (logout != null){
            flash.addFlashAttribute("success","You logout !");
            return "redirect:/login";
        }

        return "login/login";
    }
    }
