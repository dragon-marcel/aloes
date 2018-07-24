package com.example.Client.controller;

import com.example.Client.entity.Massage;
import com.example.Client.service.IMassageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping(value = "massage")
public class MassageController {

    @Autowired
    private IMassageService iMassageService;

    @RequestMapping(value = "/list")
    public String getListMassage(Model model){
        List<Massage> listMassage =iMassageService.getAllMassage();
        model.addAttribute("listMassage",listMassage);
        model.addAttribute("title","List massages");
        return "massage/list";
    }
    @RequestMapping(value = "form")
    public String fomMassage(Model model){
        Massage newMassage = new Massage();
        model.addAttribute("newMassage",newMassage);
        model.addAttribute("title","New massage");
        return "massage/form";
    }
    @PostMapping(value = "form")
    public String saveMassage(@Valid@ ModelAttribute("newMassage") Massage massage, BindingResult result, RedirectAttributes flash,
                              @RequestParam("price")Double price){
        if (result.hasErrors()){
            return "/massage/form";
        }
        if (price == null) {
            massage.setPrice(0.0);
        }
        String messageSucces = null;
        if (massage.getId() != null){
            messageSucces = "Massae update";
        }else {
            messageSucces = "Add new massage";
        }
        iMassageService.saveMassage(massage);
        flash.addFlashAttribute("success",messageSucces);
        return "redirect:/massage/list";
    }
    @RequestMapping(value = "form/{id}")
    public String updateMassage(Model model,@PathVariable("id")Long id,
                                RedirectAttributes flash){
        Massage massage = iMassageService.getMassage(id);
        if (massage == null){
            flash.addFlashAttribute("danger","Massage not exist");
        }
        model.addAttribute("newMassage",massage);
        return "massage/form";
    }
    @RequestMapping(value = "delate/{id}")
    public String delateMassage(Model model,
                                @PathVariable ("id")Long id,
                                RedirectAttributes flash) {

        Massage massage = iMassageService.getMassage(id);

        if (massage == null) {
            flash.addFlashAttribute("danger", "Error delate,Massage not exist");
            return "redirect:/massage/list";
        } else {
            try {
                iMassageService.deleteMassage(id);

            } catch (Exception e) {
                flash.addFlashAttribute("danger", "Error,massage is used");
                return "redirect:/massage/list";
            }

        }
        flash.addFlashAttribute("success","Delate massage");
        return "redirect:/massage/list";
    }
    @RequestMapping(value = "details/{id}")
    public String getMassage(Model model,@PathVariable("id")Long id){
       Massage massage =  iMassageService.getMassage(id);
       model.addAttribute("massage",massage);
       model.addAttribute("title","Massage details");
       return "massage/massageDetails";
    }
}