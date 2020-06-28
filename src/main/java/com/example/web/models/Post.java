package com.example.web.models;

import org.hibernate.annotations.Proxy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
//@Proxy(lazy = false)
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String anons;
    @Type(type = "org.hibernate.type.TextType")
    private String full_text;
    private int vievs;
    private String create_date;
    private String update_date;
    private boolean importance;



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
//    private long user_id;
    private User author;


//
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "update_user_id")
//    private User update_author;
//
//    public User getUpdate_author() {
//        return update_author;
//    }
//
//    public void setUpdate_author(User update_author) {
//        this.update_author = update_author;
//    }

    public Post() {

    }


    public Post(String title, String anons, String full_text, String create_date, User author, boolean importance) {
        this.title = title;
        this.anons = anons;
        this.full_text = full_text;
        this.create_date = create_date;
        this.author = author;
        this.importance = importance;
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


    public String getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(String update_date) {
        this.update_date = update_date;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }
    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public boolean isImportance() {
        return importance;
    }

    public void setImportance(boolean importance) {
        this.importance = importance;
    }
}
