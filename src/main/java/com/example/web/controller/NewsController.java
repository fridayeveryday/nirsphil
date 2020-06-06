package com.example.web.controller;

import com.example.web.config.DateOfPostConfig;
import com.example.web.models.Post;
//import com.example.web.models.PostExtended;
import com.example.web.models.User;
import com.example.web.repo.postRepo;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
        model.addAttribute("posts", posts);
        return "news";
    }

    @GetMapping("/news/add")
    public String OpenAddPage() {
        return "/news-add";
    }


    @GetMapping("/news/{id}")
    public String NewsDetails(@PathVariable(value = "id") long id, Model model) {
        if (!postRepo.existsById(id)) {
            return "redirect:/news";
        }

        Optional<Post> post = postRepo.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        // convert to html
        String raw_data = res.get(0).getFull_text();
        raw_data = StringEscapeUtils.unescapeHtml4(raw_data);
        res.get(0).setFull_text(raw_data);


        model.addAttribute("post", res);
        return "news-detalis";
    }

    //    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/news/{id}/edit")
    public String NewsEdit(@PathVariable(value = "id") long id, Model model) {
        if (!postRepo.existsById(id)) {
            return "redirect:/news";
        }

        Optional<Post> post = postRepo.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);

        String raw_data = res.get(0).getFull_text();
        raw_data = StringEscapeUtils.unescapeHtml4(raw_data);
        res.get(0).setFull_text(raw_data);

        model.addAttribute("post", res);
        return "news-edit";
    }

    @PostMapping("/news/{id}/edit")
    public String NewsUpdate(
            @PathVariable(value = "id") long id,
            @RequestParam() String title,
            @RequestParam() String anons,
            @RequestParam() String raw_data,
            // дата обновления
            @RequestParam() long date,
            Model model) {
        String date_of_update = DateOfPostConfig.getDate(date);

        Post post = postRepo.findById(id).orElseThrow(IllegalStateException::new);
        post.setTitle(title);
        post.setAnons(anons);

        String full_text = StringEscapeUtils.escapeHtml4(raw_data);

        post.setFull_text(full_text);
        post.setUpdate_date(date_of_update);
        postRepo.save(post);
        return "redirect:/news";
    }

    @PostMapping("/news/{id}/remove")
    public String NewsDelete(@PathVariable(value = "id") long id, Model model) {
        Post post = postRepo.findById(id).orElseThrow(IllegalStateException::new);
        postRepo.delete(post);
        return "redirect:/news";
    }

    @PostMapping("/news-add")
    public String postAdd(
            @AuthenticationPrincipal User author,
            @RequestParam String title,
            @RequestParam String anons,
            @RequestParam String raw_data,
            // дата создания
            @RequestParam long date,
            Model model) {
//        if (ManageTextDataController.add(author, title, anons, raw_data, date, model, postRepo)) {
//            return "redirect:/news";
//        }
        System.out.println(author.getUsername());
        String date_of_create = DateOfPostConfig.getDate(date);
        // to store as html
        String full_text = StringEscapeUtils.escapeHtml4(raw_data);
        Post post = new Post(title,anons,full_text, date_of_create, author);
        postRepo.save(post);
        //если какие ошибки вернуть че нить))
        return "redirect:/news";
    }

}
