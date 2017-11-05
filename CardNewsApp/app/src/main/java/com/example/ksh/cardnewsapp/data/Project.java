package com.example.ksh.cardnewsapp.data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by leepyoungwon on 17. 11. 1.
 */

public class Project implements Serializable{
    private String projectName;
    private ArrayList<Card> cards;

    public Project(){

    }

    public Project(String projectName){
        this.projectName = projectName;
        this.cards = new ArrayList<>();
    }

    public void setProjectName(String projectName){
        this.projectName = projectName;
    }

    public void setCards(ArrayList<Card> cards){
        this.cards = cards;
    }

    public String getProjectName(){
        return projectName;
    }

    public ArrayList<Card> getCards(){
        return cards;
    }
}
