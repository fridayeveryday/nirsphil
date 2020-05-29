package com.example.web.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Action {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String anons;
    @Lob
    private String full_text;
    private int vievs;

    @ElementCollection
    private List<Long> list_id = new ArrayList<Long>();
    private String create_date;
    private String update_date;



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    //private long user_id;
    private User author;

    public Action(){

    }

    public Action(String title, String anons, String full_text, String create_date, User author, ArrayList<Long> list_id) {
        this.title = title;
        this.anons = anons;
        this.full_text = full_text;
        this.create_date = create_date;
        this.author = author;
        this.list_id = list_id;
    }


    public List<Long> getList_id() {
        return list_id;
    }

    public void setList_id(List<Long> list_id) {
        this.list_id = list_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnons() {
        return anons;
    }

    public void setAnons(String anons) {
        this.anons = anons;
    }

    public String getFull_text() {
        return full_text;
    }

    public void setFull_text(String full_text) {
        this.full_text = full_text;
    }

    public int getVievs() {
        return vievs;
    }

    public void setVievs(int vievs) {
        this.vievs = vievs;
    }



    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(String update_date) {
        this.update_date = update_date;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }


}
