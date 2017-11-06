package com.example.ksh.cardnewsapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by ksh on 2017. 11. 4..
 */

public class DeveloperActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_developers);
        //액션바 타이틀 변경하기
        getSupportActionBar().setTitle("개발자 정보");
        //홈버튼 표시
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }
}
