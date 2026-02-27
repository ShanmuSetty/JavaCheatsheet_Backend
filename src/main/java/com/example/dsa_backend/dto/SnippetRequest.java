package com.example.dsa_backend.dto;

public class SnippetRequest {

    private String title;
    private String tag;
    private String code;
    private String desc;

    public String getTitle() { return title; }
    public String getTag()   { return tag;   }
    public String getCode()  { return code;  }
    public String getDesc()  { return desc;  }

    public void setTitle(String title) { this.title = title; }
    public void setTag(String tag)     { this.tag   = tag;   }
    public void setCode(String code)   { this.code  = code;  }
    public void setDesc(String desc)   { this.desc  = desc;  }
}