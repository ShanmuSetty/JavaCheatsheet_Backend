package com.example.dsa_backend;

import jakarta.persistence.*;

@Entity
@Table(name = "favorites",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_email", "snippet_id"}))
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_email", nullable = false)
    private String userEmail;

    @Column(name = "snippet_id", nullable = false)
    private String snippetId;   // e.g. "s1", "a3", "g5" — matches the ids in the JS

    public Favorite() {}

    public Favorite(String userEmail, String snippetId) {
        this.userEmail  = userEmail;
        this.snippetId  = snippetId;
    }

    public Long getId()            { return id; }
    public String getUserEmail()   { return userEmail; }
    public String getSnippetId()   { return snippetId; }
}