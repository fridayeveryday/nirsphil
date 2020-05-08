package com.example.web.models;

import javax.persistence.*;

@Entity
public class MetaPost {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    private int views;
    private String create_date;
    private String update_date;
//    private long author_id;
    @OneToOne (mappedBy = "metaPost", cascade = CascadeType.ALL)
    private Post post;
//    private User author;

    public MetaPost(){

    }

    public MetaPost(  String create_date,   User user) {

//        this.views = views;
        this.create_date = create_date;
        this.user = user;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
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

//    public long getAuthor_id() {
//        return author_id;
//    }
//
//    public void setAuthor_id(long author_id) {
//        this.author_id = author_id;
//    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getAuthor() {
        return user;
    }

    public void setAuthor(User user) {
        this.user = user;
    }
//    private User updater;

}
