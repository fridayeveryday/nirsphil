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
    public String userList(Model model){
        model.addAttribute("users", userRepo.findAll());

        return "userList";
    }
    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model){
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());

        return "userEdit";
    }

    // The commented lines if use in html a select-option mode to choose a role.
    // But the current version can work with both the select-option mode and a checkbox, if use "form.values()" instead "form.keySet()"
    // If use the select-option mode one needs delete "Set<String>...",  whole "for..."
    @PostMapping
    public String userSave(
            @Valid String role,
            @RequestParam String username,
            @RequestParam Map<String, String> form,
            @RequestParam("userId") User user){
        user.setUsername(username);

//       Set<String> roles = Arrays.stream(Role.values()).map(Role::name).collect(Collectors.toSet());

       user.getRoles().clear();
//
//       for (String key : form.keySet()){
//            if (roles.contains(key)){
//                user.getRoles().add(Role.valueOf(key));
//            }
//       }
        user.getRoles().add(Role.valueOf(role));
        userRepo.save(user);
        return "redirect:/user";
    }
}
