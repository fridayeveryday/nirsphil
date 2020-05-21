package com.example.web.controller;
import com.example.web.config.DateOfPostConfig;
import com.example.web.models.Post;
import com.example.web.models.User;
import com.example.web.repo.postRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
