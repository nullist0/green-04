package com.example.ksh.cardnewsapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import com.example.ksh.cardnewsapp.adapter.CardPagerAdapter;
import com.example.ksh.cardnewsapp.data.Card;
import com.tmall.ultraviewpager.UltraViewPager;

import java.util.ArrayList;

/**
 * Created by leepyoungwon on 17. 11. 1.
 */

public class CardActivity extends Activity implements View.OnClickListener{

    private PagerAdapter pa_main;
    private UltraViewPager uvp_main;

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_card);
    }

    private void initView(){
        //initialize view variables
        uvp_main = (UltraViewPager) findViewById(R.id.card_uvp_main);

        //initialize adapter
        pa_main = new CardPagerAdapter(new ArrayList<Card>());

        //Setting uvp_main
        uvp_main.setAdapter(pa_main);

        //uvp_main.initIndicator();

        uvp_main.setMultiScreen(1.0f);//single screen
        uvp_main.setItemRatio(1.0f);//the aspect ratio of child view equals to 1.0f
        uvp_main.setAutoMeasureHeight(false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        //TODO

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View view) {
        int curid = view.getId();
        switch (curid){}
    }
}
