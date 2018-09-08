package com.example.aloes.controller;

import com.example.aloes.entity.User;
import com.example.aloes.repository.UsersRepository;
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
public class UsersController {

    @Autowired
    private UsersRepository usersService;
    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = "users")
    public String findAllUsers(Model model,
                               Locale locale){

        List<User> users = usersService.findAllUser();

        model.addAttribute("title",messageSource.getMessage("text.user.listUser.title",null,locale));
        model.addAttribute("users",users);

        return "user/listUser";
    }

    @GetMapping(value = "user/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id,
                             RedirectAttributes flash,
                             Locale locale){

        usersService.deleteUserbyId(id);

        flash.addFlashAttribute("danger",messageSource.getMessage("text.user.listUser.danger.delete",null,locale));

        return "redirect:/users";
    }

   @RequestMapping("formUser")
   public String newUser(Model model,
                         Locale locale){

        User user = new User();

        model.addAttribute("title",messageSource.getMessage("text.user.listUser.formUser.new.title",null,locale));
        model.addAttribute("newUser",user);

        return "user/formUser";
   }


   @PostMapping("formUser")
   public String saveUser(@Valid @ModelAttribute("newUser") User user,
                          BindingResult result,
                          RedirectAttributes flash){

            if (result.hasErrors()){
                return "/user/formUser";
            }
            usersService.saveUser(user);

            flash.addFlashAttribute("success","Created new user");

            return "redirect:/users";
   }
}
