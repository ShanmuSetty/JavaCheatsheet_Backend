package com.example.dsa_backend;

import jakarta.persistence.*;

@Entity
@Table(name = "custom_snippets")
public class CustomSnippet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_email", nullable = false)
    private String userEmail;

    // These fields match exactly what the frontend sends in the POST body
    // { title, tag, code, desc }
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String tag;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String code;

    @Column(name = "description", columnDefinition = "TEXT")
    private String desc;

    public CustomSnippet() {}

    public CustomSnippet(String userEmail, String title, String tag, String code, String desc) {
        this.userEmail = userEmail;
        this.title     = title;
        this.tag       = tag;
        this.code      = code;
        this.desc      = desc;
    }

    public Long getId()          { return id; }
    public String getUserEmail() { return userEmail; }
    public String getTitle()     { return title; }
    public String getTag()       { return tag; }
    public String getCode()      { return code; }
    public String getDesc()      { return desc; }

    public void setTitle(String title) { this.title = title; }
    public void setTag(String tag)     { this.tag   = tag;   }
    public void setCode(String code)   { this.code  = code;  }
    public void setDesc(String desc)   { this.desc  = desc;  }
}