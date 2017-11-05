package com.example.ksh.cardnewsapp;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Created by ksh on 2017. 11. 4..
 */

public class LicenseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_license);
        //액션바 타이틀 변경하기
        getSupportActionBar().setTitle(R.string.app_name);
        //액션바 배경색 변경
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF339999));
        //홈버튼 표시
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }
}
