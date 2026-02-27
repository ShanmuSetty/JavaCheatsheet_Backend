package com.example.dsa_backend.dto;

import com.example.dsa_backend.CustomSnippet;

public class SnippetResponse {

    private String id;     // "custom_5" — frontend uses this as the card id
    private String title;
    private String tag;
    private String code;
    private String desc;

    public SnippetResponse(CustomSnippet s) {
        this.id    = "custom_" + s.getId();   // prefix so it never clashes with built-in ids
        this.title = s.getTitle();
        this.tag   = s.getTag();
        this.code  = s.getCode();
        this.desc  = s.getDesc() != null ? s.getDesc() : "";
    }

    public String getId()    { return id;    }
    public String getTitle() { return title; }
    public String getTag()   { return tag;   }
    public String getCode()  { return code;  }
    public String getDesc()  { return desc;  }
}