package com.example.web.controller;

import com.example.web.models.User;
import com.example.web.repo.postRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AccountController {

//    @Autowired
//    private postRepo postRepo;


    @GetMapping("/account")
    public String Account(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("user", user);
        return "account";
    }


   
//      для мероприятий
//    @GetMapping("/change-password/{id}")
//    public String Account(@PathVariable(value = "id") long id, Model model) {
//        return "change-password";
//    }
}
