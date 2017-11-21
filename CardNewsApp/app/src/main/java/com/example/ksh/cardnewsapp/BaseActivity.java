package com.example.ksh.cardnewsapp;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.example.ksh.cardnewsapp.data.Project;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class BaseActivity extends AppCompatActivity {

    protected static final String TAG = "CardNewsDEBUG";
    protected static final String INTENT_DATA = "data";

    private static final String SP_NAME = "projects";
    private static final String SP_PROJECTNAMESET = "names";

    protected ArrayList<Project> projects;

    protected void loadProjects(){
        projects = new ArrayList<>();

        SharedPreferences sp = getSharedPreferences(SP_NAME, MODE_PRIVATE);
        Set<String> names = sp.getStringSet(SP_PROJECTNAMESET, null);
        if(names != null){
            for(String s : names){
                String json = sp.getString(s, null);
                if(json != null) {
                    projects.add(JSON.parseObject(json, Project.class));
                    Log.d(TAG, json);
                }
            }
        }
    }

    protected void saveProjects(){
        SharedPreferences sp = getSharedPreferences(SP_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Set<String> names = new HashSet<>();

        for(Project p : projects) {
            String s = JSON.toJSONString(p);
            names.add(p.getProjectName());

            editor.putString(p.getProjectName(), s);
        }
        editor.putStringSet(SP_PROJECTNAMESET, names);
        editor.apply();
    }

    protected void saveProject(Project p){
        SharedPreferences sp = getSharedPreferences(SP_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        editor.putString(p.getProjectName(), JSON.toJSONString(p));

        editor.apply();
    }
}
