package com.example.web.controller;

import com.example.web.config.DateOfPostConfig;
import com.example.web.models.Post;
import com.example.web.repo.postRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@Controller

public class NewsController {

    @Autowired
    private postRepo postRepo;

    @GetMapping("/news")
    public String main(Model model) {
       Iterable<Post> posts = postRepo.findAll(Sort.by("id"));
       model.addAttribute("posts",posts);
       return "news";
    }

    @GetMapping("/news/{id}")
    public String NewsDetails(@PathVariable(value = "id") long id, Model model) {
        if(!postRepo.existsById(id)){
            return "redirect:/news";
        }

        Optional<Post> post = postRepo.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post",res);
        return "news-detalis";
    }

    @GetMapping("/news/{id}/edit")
    public String NewsEdit(@PathVariable(value = "id") long id, Model model) {
        if(!postRepo.existsById(id)){
            return "redirect:/news";
        }

        Optional<Post> post = postRepo.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post",res);
        return "news-edit";
    }

    @PostMapping("/news/{id}/edit")
    public String NewsUpdate(
            @PathVariable(value = "id") long id,
            @RequestParam() String title,
            @RequestParam() String anons,
            @RequestParam() String full_text,
            @RequestParam() long update_date,
            Model model) {
        String date_of_update = DateOfPostConfig.getDate(update_date);

        Post post = postRepo.findById(id).orElseThrow(IllegalStateException::new);
        post.setTitle(title);
        post.setAnons(anons);
        post.setFull_text(full_text);
        post.setUpdate_date(date_of_update);
        postRepo.save(post);
        return "redirect:/news";
    }
    @PostMapping("/news/{id}/remove")
    public String NewsDelete(@PathVariable(value = "id") long id,Model model) {
        Post post = postRepo.findById(id).orElseThrow(IllegalStateException::new);
        postRepo.delete(post);
        return "redirect:/news";
    }


}
