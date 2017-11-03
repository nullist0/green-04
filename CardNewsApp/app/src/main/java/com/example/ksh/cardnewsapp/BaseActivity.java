package com.example.ksh.cardnewsapp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.util.ArraySet;

import com.alibaba.fastjson.JSON;
import com.example.ksh.cardnewsapp.data.Project;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by leepyoungwon on 17. 11. 3.
 */

public class BaseActivity extends Activity {

    private static final String SP_NAME = "projects";
    private static final String SP_PROJECTNAMESET = "names";
    protected ArrayList<Project> projects;

    protected void loadProjects(){
        projects = new ArrayList<>();

        SharedPreferences sp = getSharedPreferences(SP_NAME, MODE_PRIVATE);
        Set<String> names = sp.getStringSet(SP_PROJECTNAMESET, null);
        if(names != null){
            Iterator<String> iter = names.iterator();
            while(iter.hasNext()){
                String s = iter.next();
                String json = sp.getString(s, null);
                if(json != null)
                    projects.add(JSON.parseObject(json, Project.class));
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
