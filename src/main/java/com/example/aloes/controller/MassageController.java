package com.example.aloes.controller;

import com.example.aloes.entity.Massage;
import com.example.aloes.repository.MassageR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

@Controller
public class MassageController {

    @Autowired
    private MassageR massageR;
    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = "massages")
    public String findAllMassages(Model model,
                                  Locale locale){

        List<Massage> massages = massageR.getAllMassage();
        model.addAttribute("massages",massages);
        model.addAttribute("title", messageSource.getMessage("text.massage.listMassage.title",null,locale));

        return "massage/listMassage";
    }

    @RequestMapping(value = "formMassage")
    public String newMassage(Model model,
                             Locale locale){

        Massage newMassage = new Massage();

        model.addAttribute("newMassage",newMassage);
        model.addAttribute("title",messageSource.getMessage("text.massage.formMassage.new.title",null,locale));

        return "massage/formMassage";
    }

    @PostMapping(value = "formMassage")
    public String saveMassage(@Valid@ ModelAttribute("newMassage") Massage massage,
                              BindingResult result,
                              RedirectAttributes flash,
                              @RequestParam("price")Double price,
                              Locale locale){

        if (result.hasErrors()){
            return "massage/formMassage";
        }
        if (price == null) {
            massage.setPrice(0.0);
        }
        String messageSucces;
        if (massage.getId() != null){
            messageSucces = messageSource.getMessage("text.massage.formMassage.success.edit",null,locale);
        }else {
            messageSucces = messageSource.getMessage("text.massage.formMassage.success.new",null,locale);
        }
        massageR.saveMassage(massage);

        flash.addFlashAttribute("success",messageSucces);

        return "redirect:/massages";
    }

    @RequestMapping(value = "formMassage/{id}")
    public String updateMassage(Model model,@PathVariable("id")Long id,
                                RedirectAttributes flash,
                                Locale locale){

        Massage massage = massageR.getMassageById(id);
        if (massage == null){
            flash.addFlashAttribute("danger",messageSource.getMessage("text.massage.formMassage.danger.notExist",null,locale));
        }
        model.addAttribute("newMassage",massage);
        model.addAttribute("title",messageSource.getMessage("text.massage.formMassage.edit.title",null,locale));
        return "massage/formMassage";
    }

    @RequestMapping(value = "massage/delete/{id}")
    public String deleteMassage(Model model,
                                @PathVariable ("id")Long id,
                                RedirectAttributes flash,
                                Locale locale) {

        Massage massage = massageR.getMassageById(id);

        if (massage == null) {
            flash.addFlashAttribute("danger",messageSource.getMessage("text.massage.formMassage.danger.notExist",null,locale));
            return "redirect:/massages";
        } else {
            try {
                massageR.deleteMassageById(id);

            } catch (Exception e) {
                flash.addFlashAttribute("danger",messageSource.getMessage("text.massage.formMassage.danger.use",null,locale));
                return "redirect:/massages";
            }

        }
        flash.addFlashAttribute("success",messageSource.getMessage("text.massage.formMassage.success.delete",null,locale));

        return "redirect:/massages";
    }

    @RequestMapping(value = "massage/{id}")
    public String getMassage(Model model,@PathVariable("id")Long id,
                             Locale locale){

       Massage massage =  massageR.getMassageById(id);

       model.addAttribute("massage",massage);
       model.addAttribute("title",messageSource.getMessage("text.massage.detailsMassage.title",null,locale));

       return "massage/detailsMassage";
    }
}