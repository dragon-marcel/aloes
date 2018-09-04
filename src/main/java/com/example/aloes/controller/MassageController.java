package com.example.aloes.controller;

import com.example.aloes.entity.Massage;
import com.example.aloes.repository.MassageR;
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
    private MassageR massageR;

    @RequestMapping(value = "massages")
    public String findAllMassages(Model model){

        List<Massage> massages = massageR.getAllMassage();
        model.addAttribute("massages",massages);
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
        massageR.saveMassage(massage);
        flash.addFlashAttribute("success",messageSucces);

        return "redirect:/massages";
    }
    @RequestMapping(value = "formMassage/{id}")
    public String updateMassage(Model model,@PathVariable("id")Long id,
                                RedirectAttributes flash){
        Massage massage = massageR.getMassageById(id);
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

        Massage massage = massageR.getMassageById(id);

        if (massage == null) {
            flash.addFlashAttribute("danger", "Error delete,Massage not exist");
            return "redirect:/massages";
        } else {
            try {
                massageR.deleteMassageById(id);

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

       Massage massage =  massageR.getMassageById(id);

       model.addAttribute("massage",massage);
       model.addAttribute("title","Massage details");

       return "massage/detailsMassage";
    }
}