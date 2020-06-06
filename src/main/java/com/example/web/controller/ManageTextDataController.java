package com.example.web.controller;

import com.example.web.config.DateOfPostConfig;
import com.example.web.models.TextData;
import com.example.web.models.User;
import com.example.web.repo.postRepo;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public class ManageTextDataController {

    public static boolean add(@AuthenticationPrincipal User author,
                              @RequestParam String title,
                              @RequestParam String anons,
                              @RequestParam String raw_data,
                              // дата создания
                              @RequestParam long date,
                              Model model,
                              postRepo postRepo,
                              TextData textData) {
        if (title.isEmpty() || anons.isEmpty() || raw_data.isEmpty()) {
            // добавить в модель ошибки
            return false;
        }
        String date_of_create = DateOfPostConfig.getDate(date);
        // to store as html
        String full_text = StringEscapeUtils.escapeHtml4(raw_data);
        //универсаотный конструктор нужен или какая то ООПшная фича
//        Post post = new Post(title, anons, full_text, date_of_create, author);
//        postRepo.save(post);
        return true;
    }

    public static boolean update(@PathVariable(value = "id") long id,
                                 @RequestParam() String title,
                                 @RequestParam() String anons,
                                 @RequestParam() String raw_data,
                                 // дата обновления
                                 @RequestParam() long date,
                                 Model model,
                                 postRepo postRepo){

        return true;

    }

}
