package com.example.ksh.cardnewsapp.data;

/**
 * Created by leepyoungwon on 17. 11. 1.
 */

public class Card {
    private int template;
    private String title, text;

    public Card(int template, String title, String text){
        this.template = template;
        this.title = title;
        this.text = text;
    }

    public void setTemplate(int template){
        this.template = template;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setText(String text){
        this.text = text;
    }

    public int getTemplate(){
        return template;
    }

    public String getTitle(){
        return title;
    }

    public String getText(){
        return text;
    }
}
