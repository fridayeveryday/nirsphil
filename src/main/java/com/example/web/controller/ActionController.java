package com.example.web.controller;



import com.example.web.config.DateOfPostConfig;
import com.example.web.models.Action;
import com.example.web.models.User;
import com.example.web.repo.UserRepo;
import com.example.web.repo.actionRepo;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Controller
public class ActionController {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private actionRepo actionRepo;

    @GetMapping("/action")
    public String main(Model model) {
        Iterable<Action> actions = actionRepo.findAll(Sort.by("id"));
        model.addAttribute("actions",actions);
        return "action";
    }

    @GetMapping("/action-add")
    public String createNewAction(Model model) {
        List<User> a = userRepo.findAll();

        return "action-add";
    }

    @PostMapping("/action/{id}/action-users")
    public String ActionOfUsers(@PathVariable(value = "id") long id,@AuthenticationPrincipal User user,Model model) {
        Action action = actionRepo.findById(id).orElseThrow(IllegalStateException::new);
        List<Long> list_users_id = action.getList_id();
        List<User> list_user =  userRepo.findAll();
        ArrayList<User> res = new ArrayList<>();
        for (int i = 0; i < list_users_id.size(); i++) {
            Long a = list_users_id.get(i);
            for (int j = 0; j < list_user.size() ; j++) {
                Long b = list_user.get(j).getId();
                if(a.equals(b)){
                    res.add(list_user.get(j));
                }
            }
        }


        model.addAttribute("users",res);
        return "action-users";
    }


    @GetMapping("/user-actions")
    public String UserACtion(@AuthenticationPrincipal User user,Model model) {
        List<Action> actions = (List<Action>) actionRepo.findAll();
        Long id = user.getId();
        User user1 = userRepo.findById(id).orElseThrow(IllegalStateException::new);
        List<Long> list_action_id = user1.getList_action_id();

        ArrayList<Action> res = new ArrayList<>();
        for (int i = 0; i < list_action_id.size(); i++) {
            Long a = list_action_id.get(i);
            for (int j = 0; j < actions.size() ; j++) {
                Long b = actions.get(j).getId();
                if(a.equals(b)){
                    res.add(actions.get(j));
                }
            }
        }

        model.addAttribute("actions",res);
        return "user-actions";
    }




    @GetMapping("/action/{id}")
    public String ActionsDetails(@PathVariable(value = "id") long id,@AuthenticationPrincipal User user, Model model) {
        if(!actionRepo.existsById(id)){
            return "redirect:/action";
        }

        Action action_user = actionRepo.findById(id).orElseThrow(IllegalStateException::new);
        Long id_user = user.getId();
        boolean chek = action_user.getList_id().contains(id_user);
        model.addAttribute("chek",chek);

        Optional<Action> action = actionRepo.findById(id);
        ArrayList<Action> res = new ArrayList<>();
        action.ifPresent(res::add);
        // convert to html
        String raw_data = res.get(0).getFull_text();
        raw_data = StringEscapeUtils.unescapeHtml4(raw_data);
        res.get(0).setFull_text(raw_data);

        model.addAttribute("action",res);

        return "action-detalis";
    }





    @PostMapping("/action/{id}/edit")
    public String actionUpdate(
            @PathVariable(value = "id") long id,
            @RequestParam() String title,
            @RequestParam() String anons,
            @RequestParam() String raw_data,
            @RequestParam() long date,
            Model model) {
        String date_of_update = DateOfPostConfig.getDate(date);

        Action action = actionRepo.findById(id).orElseThrow(IllegalStateException::new);
        action.setTitle(title);
        action.setAnons(anons);

        String full_text = StringEscapeUtils.escapeHtml4(raw_data);

        action.setFull_text(full_text);
        action.setUpdate_date(date_of_update);
        actionRepo.save(action);
        return "redirect:/action";
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/action/{id}/edit")
    public String NewsEdit(@PathVariable(value = "id") long id, Model model) {
        if(!actionRepo.existsById(id)){
            return "redirect:/action";
        }

        Optional<Action> action = actionRepo.findById(id);
        ArrayList<Action> res = new ArrayList<>();
        action.ifPresent(res::add);

        String raw_data = res.get(0).getFull_text();
        raw_data = StringEscapeUtils.unescapeHtml4(raw_data);
        res.get(0).setFull_text(raw_data);

        model.addAttribute("action",res);
        return "action-edit";
    }

    @PostMapping("/action/{id}/remove")
    public String NewsDelete(@PathVariable(value = "id") long id,Model model) {
        Action action = actionRepo.findById(id).orElseThrow(IllegalStateException::new);
        actionRepo.delete(action);
        return "redirect:/action";
    }




    @PostMapping("/action/{id}/reg")
    public String Reg(@PathVariable(value = "id") long id, @AuthenticationPrincipal User user,Model model) {
        Action action = actionRepo.findById(id).orElseThrow(IllegalStateException::new);
        Long id_user = user.getId();
        User user1 = userRepo.findById(id_user).orElseThrow(IllegalStateException::new);

        boolean chek = action.getList_id().contains(id_user);
        if(chek){
            user1.getList_action_id().remove(id);
            userRepo.save(user1);
            action.getList_id().remove(id_user);
            actionRepo.save(action);

        }
        else {
            user1.getList_action_id().add(id);
            userRepo.save(user1);
            action.getList_id().add(id_user);
            actionRepo.save(action);
        }

        return "redirect:/action/{id}";
    }



    @PostMapping("/action-add")
    public String actionAdd(
            @AuthenticationPrincipal User author,
            @RequestParam String title,
            @RequestParam String anons,
            @RequestParam String raw_data,
            @RequestParam long date,
            Model model) {
        System.out.println(author.getUsername());
        String date_of_create = DateOfPostConfig.getDate(date);
        // to store as html
        List<Long> list_id = new ArrayList<>();
        String full_text = StringEscapeUtils.escapeHtml4(raw_data);
        Action action = new Action(title,
                anons,
                full_text,
                date_of_create,
                author,
                (ArrayList<Long>) list_id
        );
        actionRepo.save(action);
        return "redirect:/action";
    }
}
