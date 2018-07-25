package com.example.Client.controller;

import com.example.Client.entity.Massage;
import com.example.Client.repository.IMassageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class MassageController {

    @Autowired
    private IMassageRepository iMassageRepository;

    @RequestMapping(value = "massages")
    public String findAllMassages(Model model){

        List<Massage> listMassage = iMassageRepository.getAllMassage();
        model.addAttribute("listMassage",listMassage);
        model.addAttribute("title","List massages");
        return "massage/listMassage";
    }

    @RequestMapping(value = "formMassage")
    public String newMassage(Model model){

        Massage newMassage = new Massage();

        model.addAttribute("newMassage",newMassage);
        model.addAttribute("title","New massage");
        return "massage/formMassage";
    }
    @PostMapping(value = "formMassage")
    public String saveMassage(@Valid@ ModelAttribute("newMassage") Massage massage,
                              BindingResult result, RedirectAttributes flash,
                              @RequestParam("price")Double price){
        if (result.hasErrors()){
            return "massage/formMassage";
        }
        if (price == null) {
            massage.setPrice(0.0);
        }
        String messageSucces = null;
        if (massage.getId() != null){
            messageSucces = "Massage update";
        }else {
            messageSucces = "Add new massage";
        }
        iMassageRepository.saveMassage(massage);
        flash.addFlashAttribute("success",messageSucces);

        return "redirect:/massages";
    }
    @RequestMapping(value = "formMassage/{id}")
    public String updateMassage(Model model,@PathVariable("id")Long id,
                                RedirectAttributes flash){
        Massage massage = iMassageRepository.getMassageById(id);
        if (massage == null){
            flash.addFlashAttribute("danger","Massage not exist");
        }
        model.addAttribute("newMassage",massage);
        return "massage/formMassage";
    }
    @RequestMapping(value = "massage/delete/{id}")
    public String deleteMassage(Model model,
                                @PathVariable ("id")Long id,
                                RedirectAttributes flash) {

        Massage massage = iMassageRepository.getMassageById(id);

        if (massage == null) {
            flash.addFlashAttribute("danger", "Error delete,Massage not exist");
            return "redirect:/massages";
        } else {
            try {
                iMassageRepository.deleteMassageById(id);

            } catch (Exception e) {
                flash.addFlashAttribute("danger", "Error,massage is used");
                return "redirect:/massages";
            }

        }
        flash.addFlashAttribute("success","Delete massage");
        return "redirect:/massages";
    }
    @RequestMapping(value = "massage/{id}")
    public String getMassage(Model model,@PathVariable("id")Long id){

       Massage massage =  iMassageRepository.getMassageById(id);

       model.addAttribute("massage",massage);
       model.addAttribute("title","Massage details");

       return "massage/detailsMassage";
    }
}