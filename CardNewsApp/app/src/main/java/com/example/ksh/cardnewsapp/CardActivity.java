package com.example.ksh.cardnewsapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.ksh.cardnewsapp.adapter.CardPagerAdapter;
import com.example.ksh.cardnewsapp.data.Card;
import com.example.ksh.cardnewsapp.data.Project;
import com.tmall.ultraviewpager.UltraViewPager;

import java.util.ArrayList;

/**
 * Created by leepyoungwon on 17. 11. 1.
 */

public class CardActivity extends Activity implements View.OnClickListener{

    private CardPagerAdapter cpa_main;
    private UltraViewPager uvp_main;

    private Button bt_image, bt_text, bt_temp;
    private View view_text, view_temp;

    private Project project;

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_card);

        initVar();
        initView();
    }

    private void initVar(){
        //initialize from intent
        project = (Project) getIntent().getSerializableExtra("data");

        //initialize view variables
        uvp_main = (UltraViewPager) findViewById(R.id.card_uvp_main);

        bt_image = (Button) findViewById(R.id.card_bt_image);
        bt_text = (Button) findViewById(R.id.card_bt_text);
        bt_temp = (Button) findViewById(R.id.card_bt_template);

        //initialize adapter
        cpa_main = new CardPagerAdapter(getApplicationContext(), new ArrayList<Card>());
    }

    private void initView(){
        //Setting uvp_main
        uvp_main.setAdapter(cpa_main);

        //uvp_main.initIndicator();

        uvp_main.setMultiScreen(1.0f);//single screen
        uvp_main.setItemRatio(1.0f);//the aspect ratio of child view equals to 1.0f
        uvp_main.setAutoMeasureHeight(false);

        //initialize buttons
        bt_image.setOnClickListener(this);
        bt_text.setOnClickListener(this);
        bt_temp.setOnClickListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        //TODO

        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cm_delete:
                return true;
            case R.id.cm_add:
                return true;
            case R.id.cm_save:
                requestSave();
                return true;
            case R.id.cm_share:
                requestShare();
                return true;
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        int curid = view.getId();
        switch (curid){
            case R.id.card_bt_image:
                requestImage();
                break;
            case R.id.card_bt_text:
                requestText();
                break;
            case R.id.card_bt_template:
                requestTemplate();
                break;

        }
    }

    public void requestImage(){

    }

    public void requestText(){

    }

    public void requestTemplate(){

    }

    public void requestSave(){
        project.setCards(cpa_main.getCards());
        //...TODO
    }

    public void requestShare(){
        requestSave();
        
    }
}
