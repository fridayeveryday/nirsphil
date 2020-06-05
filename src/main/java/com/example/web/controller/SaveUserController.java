package com.example.web.controller;

import com.example.web.models.User;
import com.example.web.repo.UserRepo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;

import java.util.Map;

// класс, в который вынесена общая логика проверки личных данных при изменении их самим пользователем или админом
public class SaveUserController {

    // проверка на равенство введенного пароля и пароля из бд
    private static Boolean checkPassword(String passFromView, String passFromDb) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(passFromView, passFromDb);
    }


    public static Boolean checking4Correctness(Map<String, String> form,
                                               User user, Model model, UserRepo userRepo) {
        // корректность пароля и почты
        // в случае ошибок принимает false и в модель добавляются сообщения об ошибках
        boolean correctData = true;
        // в форме есть поле new_password (ввод нового пароля) и поля старого пароля пустое (значит пользователь решил его не менять)
        if (!form.get("password").isEmpty() && form.containsKey("new_password") &&  !checkPassword(form.get("password"), user.getPassword())) {
            model.addAttribute("password_error", "Старый пароль введен неверно, пожалуйста проверьте его корректность");
            correctData = false;
        }
        //введен пустой email
        if (form.get("email").isEmpty()) {
            model.addAttribute("error", "email не может быть пустым");
            model.addAttribute("user", user);
            correctData = false;
        } else {
            //введена новая почта, не такая же, как текущая (поле осталось неизменным)
            if (!form.get("email").equals(user.getEmail())) {
                //если такая почта уже существует в бд, то внутрь if
                if (userRepo.findByEmail(form.get("email")) != null) {
                    model.addAttribute("error", "Пользователь с таким email уже существует");
                    user.setEmail(form.get("email"));
                    model.addAttribute("user", user);
                    correctData = false;
                }
            }
        }
        model.addAttribute("user", user);
        return correctData;
    }

    public static User setData(Map<String, String> form) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        user.setFirstname(form.get("firstname"));
        user.setLastname(form.get("lastname"));
        user.setPatronymic(form.get("patronymic"));
        user.setEmail(form.get("email"));
        //поле password не пустое, если уставновлен новый пароль в new_password
        //если true-пароль от обычного пользователя
        //иначе пароль поставил админ
        if (!form.get("password").equals("") && form.containsKey("new_password")) {
            user.setPassword(new BCryptPasswordEncoder().encode(form.get("new_password")));
        }else {
            user.setPassword(new BCryptPasswordEncoder().encode(form.get("password")));
        }

        return user;
    }

}

