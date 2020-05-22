package com.example.web.controller;

import com.example.web.models.Role;
import com.example.web.models.User;
import com.example.web.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepo userRepo;

    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userRepo.findAll());

        return "userList";
    }

    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());

        return "userEdit";
    }


    @PostMapping
    public String userSave(
            @Valid String role,

//            @RequestParam String firstname,
//            @RequestParam String lastname,
//            @RequestParam String patronymic,
//            @RequestParam String email,
//            @RequestParam String password,

            @RequestParam Map<String, String> form,
            @RequestParam("userId") User user,
            Model model) {
        if (form.get("email").isEmpty()) {
            model.addAttribute("error", "email не может быть пустым");
            model.addAttribute("roles", Role.values());
            user.setEmail(form.get("email"));
            model.addAttribute("user", user);
            return "userEdit";
        }
        if (!form.get("email").equals(user.getEmail())) {
            if (userRepo.findByEmail(form.get("email")) != null) {
                model.addAttribute("error", "Пользователь с таким email уже существует");
                model.addAttribute("roles", Role.values());
                user.setEmail(form.get("email"));
                model.addAttribute("user", user);
                return "userEdit";
            }
        }
            user.setFirstname(form.get("firstname"));
            user.setLastname(form.get("lastname"));
            user.setPatronymic(form.get("patronymic"));
            user.setEmail(form.get("email"));
            user.setPassword(form.get("password"));


            //для обычного пользователя, который не может менять себе роль
            if (!form.containsKey("role")) {

                userRepo.save(user);
                return "redirect:/";
            }
            user.getRoles().clear();
            user.getRoles().add(Role.valueOf(role));
            userRepo.save(user);


        return "redirect:/user";
    }
}
