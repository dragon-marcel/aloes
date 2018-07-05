package com.example.Client.controller;

import com.example.Client.entity.User;
import com.example.Client.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;


@Controller
public class UsersController {
    @Autowired
    private UsersService usersService;

    @RequestMapping(value = "/user")
    public String user(Model model){
        model.addAttribute("title","User");
        return "users/user";
    }
    @RequestMapping(value = "/users")
    public String findUsers(Model model){
        model.addAttribute("title","List users");
        List<User> user = usersService.findAll();
        model.addAttribute("users",user);
        return "users/users";
    }

    @GetMapping(value = "users/delate/{id}")
    public String delateUser(@PathVariable("id") Long id,RedirectAttributes flash){
        usersService.delateUserbyId(id);
        flash.addFlashAttribute("danger","Delate user");
        return "redirect:/users";
    }

   @RequestMapping("users/form")
   public String newUser(Model model){
        User users = new User();
        model.addAttribute("title","New user");
        model.addAttribute("newUser",users);
        return "users/form";
   }


   @PostMapping("users/form")
   public String saveUser(@Valid @ModelAttribute("newUser") User user, BindingResult result, RedirectAttributes flash){
            if (result.hasErrors()){
                return "/users/form";
            }
            usersService.saveUser(user);
            flash.addFlashAttribute("success","Created new User");
            return "redirect:/users";

   }
}
