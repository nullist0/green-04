package com.example.ksh.cardnewsapp.data;

import java.io.Serializable;

public class Card implements Serializable{
    private int template;
    private String title, text;
    private String fileDir;

    public Card(){

    }

    public Card(int template, String title, String text, String fileDir){
        this.template = template;
        this.title = title;
        this.text = text;
        this.fileDir = fileDir;
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

    public void setFileDir(String fileDir){
        this.fileDir = fileDir;
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

    public String getFileDir(){
        return fileDir;
    }
}
