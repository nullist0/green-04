package com.example.ksh.cardnewsapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.ksh.cardnewsapp.adapter.CardPagerAdapter;
import com.example.ksh.cardnewsapp.data.Card;
import com.example.ksh.cardnewsapp.data.Project;
import com.tmall.ultraviewpager.UltraViewPager;
import com.vipul.hp_hp.library.Layout_to_Image;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 * Created by leepyoungwon on 17. 11. 1.
 */

public class CardActivity extends BaseActivity implements View.OnClickListener{

    private static final int REQUEST_PICK_PICTURE = 444;

    private CardPagerAdapter cpa_main;
    private UltraViewPager uvp_main;

    private Button bt_image, bt_text, bt_temp;
    private LinearLayout ll_text, ll_temp;

    //ll_text
    private EditText et_text, et_title;
    private Button bt_confirm_text;

    //ll_temp
    private Button bt_temparary; //For prototype
    private Button bt_confirm_temp;

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
        uvp_main = findViewById(R.id.card_uvp_main);

        bt_image = findViewById(R.id.card_bt_image);
        bt_text = findViewById(R.id.card_bt_text);
        bt_temp = findViewById(R.id.card_bt_template);

        ll_text = findViewById(R.id.card_ll_control_text);
        ll_temp = findViewById(R.id.card_ll_control_template);

        et_text = findViewById(R.id.card_et_text);
        et_title = findViewById(R.id.card_et_title);

        bt_temparary = findViewById(R.id.card_bt_temp1);

        bt_confirm_temp = findViewById(R.id.card_bt_confirm_temp);
        bt_confirm_text = findViewById(R.id.card_bt_confirm_text);

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

        bt_temparary.setOnClickListener(this);

        bt_confirm_text.setOnClickListener(this);
        bt_confirm_temp.setOnClickListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK){
            if (requestCode == UCrop.REQUEST_CROP) {
                Uri resultUri = UCrop.getOutput(data);

                //TODO SOME?
                cpa_main.getCurrentCard().setFileDir(resultUri.getPath());
            }
            else if(requestCode == REQUEST_PICK_PICTURE){
                if(data != null)
                    requestCrop(data.getData());
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cm_delete:
                deleteCard();
                return true;
            case R.id.cm_add:
                addCard();
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
                openTextControl();
                break;
            case R.id.card_bt_template:
                openTemplateControl();
                break;
            case R.id.card_bt_confirm_temp:
                closeTextControl();
                break;
            case R.id.card_bt_confirm_text:
                closeTemplateControl();
                break;
            case R.id.card_bt_temp1:
                cpa_main.getCurrentCard().setTemplate(0);
                cpa_main.notifyDataSetChanged(); //TODO
                break;
        }
    }

    private void deleteCard(){
        cpa_main.removeCard();
        cpa_main.notifyDataSetChanged();
    }

    private void addCard(){
        cpa_main.addCard();
        cpa_main.notifyDataSetChanged();
    }

    private void requestImage(){
        requestPick();
    }

    private void openTextControl(){
        ll_text.setVisibility(View.VISIBLE);

        et_title.setText(cpa_main.getCurrentCard().getTitle());
        et_text.setText(cpa_main.getCurrentCard().getText());
    }

    private void openTemplateControl(){
        ll_temp.setVisibility(View.VISIBLE);
    }

    private void closeTextControl() {
        ll_text.setVisibility(View.INVISIBLE);

        Card c = cpa_main.getCurrentCard();
        c.setTitle(et_title.getText().toString());
        c.setText(et_text.getText().toString());

        et_title.setText("");
        et_text.setText("");
    }

    private void closeTemplateControl(){
        ll_temp.setVisibility(View.INVISIBLE);
    }

    private void requestSave(){
        project.setCards(cpa_main.getCards());
        saveProject(project);
    }

    private void requestShare(){
        requestSave();

        Intent share = new Intent();
        share.setAction(Intent.ACTION_SEND_MULTIPLE);
        share.putExtra(Intent.EXTRA_SUBJECT, "Here are some files.");
        share.setType("image/png");

        ArrayList<Uri> files = new ArrayList<Uri>();

        for(int i = 0; i < project.getCards().size(); i++){
            Layout_to_Image lti =
                    new Layout_to_Image(this, cpa_main.getViews().get(i));
            files.add(saveBitmap(lti.convert_layout(), i));
        }

        share.putParcelableArrayListExtra(Intent.EXTRA_STREAM, files);
        startActivity(share);
    }

    private void requestPick(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_PICK_PICTURE);
    }

    private void requestCrop(Uri uri){

        String destinationFileName = "image"+cpa_main.getCurrentPosition();

        UCrop uCrop = UCrop.of(uri,
                Uri.fromFile(
                        new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                                + "/" + project.getProjectName() + "/images", destinationFileName)));

//        uCrop = basisConfig(uCrop);
//        uCrop = advancedConfig(uCrop);
        uCrop = uCrop.withAspectRatio(1, 1);

        uCrop.start(CardActivity.this);
    }

    private Uri saveBitmap(Bitmap bitmap, int i){
        String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() +
                "/" + project.getProjectName() + "/cards";
        File dir = new File(file_path);
        if(!dir.exists())
            dir.mkdirs();
        File file = new File(dir, i+".png");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 85, fos);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (fos != null){
                try {
                    fos.flush();
                    fos.close();
                }catch (Exception e1){}
            }
        }
        return Uri.fromFile(file);
    }

}
