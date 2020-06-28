package com.example.web.controller;

import com.example.web.models.Role;
import com.example.web.models.User;
import com.example.web.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.awt.print.Pageable;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Controller
//@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepo userRepo;

    private int userRecordsNum = 10;

    @GetMapping("/user")
    public String userList(Model model) {
        // второя аргумент - количество извлекаемых записей из бд
        PageRequest pageable = PageRequest.of(0, userRecordsNum, Sort.by(Sort.Direction.DESC, "id"));
        Page<User> lastUsers = userRepo.findAll(pageable);
        model.addAttribute("users", lastUsers);

        return "userList";
    }

    @GetMapping("user/{user}")
    public String userEditForm(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());

        return "userEdit";
    }

    @PostMapping("filter")
    public String findUsers(@RequestParam String firstname, @RequestParam String lastname, Model model) {
        firstname = firstname.trim();
        lastname = lastname.trim();
        Iterable<User> users = userRepo.findByFirstnameContainingAndLastnameContaining(firstname, lastname);
        model.addAttribute("users", users);

        return "userList";
    }

    @PostMapping("/user")
    @PreAuthorize("isAuthenticated()")
    public String userSave(
            @Valid String role,
            @RequestParam Map<String, String> form,
            @RequestParam("userId") User user,
            Model model) {
        // если есть недочоеты в введенной форме
//        SaveUserController suc = new SaveUserController();
        if (!SaveUserController.checking4Correctness(form, user, model, userRepo)) {
            // если есть в форме поле old_password, то это ввод обычного user
            if (form.containsKey("new_password")) {
                return "account";
            } else {
                // иначе это ошибка из-под админа
                return "userEdit";
            }
        }
        // если все ок, то сохраняем имена, почту и пароль
        user = SaveUserController.setData(form, user);
        //для обычного пользователя, который не может менять себе роль
        if (!form.containsKey("role")) {
            userRepo.save(user);
            return "redirect:/";
        }
        // устанавливаем роль если под админом вход

        user.getRoles().clear();
        user.getRoles().add(Role.valueOf(role));
        userRepo.save(user);

        return "redirect:/user";
    }


}
