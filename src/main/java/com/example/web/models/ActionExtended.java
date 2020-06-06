//package com.example.web.models;
//
//import javax.persistence.*;
//import java.util.ArrayList;
//import java.util.List;
//
//
//@Entity
//public class ActionExtended extends TextData{
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//
//    @ElementCollection
//    private List<Long> list_id = new ArrayList<Long>();
//
//    public ActionExtended(String title, String anons, String full_text, String create_date, User author, ArrayList<Long> list_id) {
//        this.title = title;
//        this.anons = anons;
//        this.full_text = full_text;
//        this.create_date = create_date;
//        this.author = author;
//        this.list_id = list_id;
//    }
//    public ActionExtended(){}
//
//    public List<Long> getList_id() {
//        return list_id;
//    }
//
//    public void setList_id(List<Long> list_id) {
//        this.list_id = list_id;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//}
