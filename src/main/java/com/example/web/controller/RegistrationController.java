package com.example.web.controller;

import com.example.web.models.Role;
import com.example.web.models.User;
import com.example.web.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepo userRepo;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Map<String, Object> model, @RequestParam Map<String, String> form) {
        user.setPassword(form.get("new_password"));
        User userFromDb = userRepo.findByEmail(user.getEmail());
//          Если введены не все данные вернуть на дозаполнение
//        if(user)
        if (userFromDb != null) {
            String error_string = "Пользователь с таким e-mail уже существует";
            model.put("message", error_string);
            model.put("user",user);
            return "registration";
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepo.save(user);

        return "redirect:/login";
    }
}