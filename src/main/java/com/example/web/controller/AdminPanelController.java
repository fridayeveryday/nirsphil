package com.example.web.controller;
import com.example.web.repo.postRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminPanelController {
    @GetMapping("/adminPanel")
    public String adminPanel(Model model) {

        return "adminPanel";
    }

    @GetMapping("/adminPanel/add")
    public String CreateNewPost(Model model) {

        return "news-add";
    }

    @Autowired
    private postRepo postRepo;

}
