package com.example.ksh.cardnewsapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ksh.cardnewsapp.adapter.CardPagerAdapter;
import com.example.ksh.cardnewsapp.data.Card;
import com.example.ksh.cardnewsapp.data.Project;
import com.tmall.ultraviewpager.UltraViewPager;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class CardActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener{

    private static final int REQUEST_PICK_PICTURE = 444;

    private CardPagerAdapter cpa_main;
    private UltraViewPager uvp_main;

    private TextView tv_order;
    private Button bt_image, bt_text, bt_temp;
    private LinearLayout ll_text, ll_temp;

    //ll_text
    private EditText et_text, et_title;
    private Button bt_confirm_text;

    //ll_temp
    private Button bt_temparary; //For prototype
    private Button bt_confirm_temp;

    private Project project;

    private int position = 0;

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_card);

        initVar();
        initView();
    }

    private void initVar(){
        //initialize from intent
        project = (Project) getIntent().getSerializableExtra(INTENT_DATA);

        //initialize view variables
        uvp_main = findViewById(R.id.card_uvp_main);

        tv_order = findViewById(R.id.card_tv_order);

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
        cpa_main = new CardPagerAdapter(getApplicationContext(), project.getCards());
    }

    private void initView(){
        //Setting uvp_main
        uvp_main.setAdapter(cpa_main);
        uvp_main.setOnPageChangeListener(this);

        //uvp_main.initIndicator();

        uvp_main.setMultiScreen(1.0f);//single screen
        uvp_main.setItemRatio(1.0f);//the aspect ratio of child view equals to 1.0f
        uvp_main.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        uvp_main.setAutoMeasureHeight(true);

        //initialize text
        tv_order.setText((position+1)+ "번째 카드");

        //initialize buttons
        bt_image.setOnClickListener(this);
        bt_text.setOnClickListener(this);
        bt_temp.setOnClickListener(this);

        bt_temparary.setOnClickListener(this);

        bt_confirm_text.setOnClickListener(this);
        bt_confirm_temp.setOnClickListener(this);

        getSupportActionBar().setTitle(project.getProjectName());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode == RESULT_OK){
            if (requestCode == UCrop.REQUEST_CROP) {
                Uri resultUri = UCrop.getOutput(data);

                cpa_main.getCard(position).setFileDir(resultUri.getPath());
                cpa_main.notifyDataSetChanged();
            }
            else if(requestCode == REQUEST_PICK_PICTURE){
                if(data != null)
                    requestCrop(data.getData());
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
            cropError.printStackTrace();
        }
    }

    //액션버튼 메뉴 액션바에 집어 넣기
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.card_menu, menu);
        return true;
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
                finish();
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
            case R.id.card_bt_confirm_text :
                closeTextControl();
                break;
            case R.id.card_bt_confirm_temp:
                closeTemplateControl();
                break;
            case R.id.card_bt_temp1:
                cpa_main.getCard(position).setTemplate(0);
                cpa_main.notifyDataSetChanged(); //TODO
                break;
        }
    }

    private void deleteCard(){
        cpa_main.removeCard(position);
        cpa_main.notifyDataSetChanged();
    }

    private void addCard(){
        cpa_main.addCard(position);
        cpa_main.notifyDataSetChanged();
    }

    private void requestImage(){
        requestPick();
    }

    private void openTextControl(){
        ll_text.setVisibility(View.VISIBLE);

        et_title.setText(cpa_main.getCard(position).getTitle());
        et_text.setText(cpa_main.getCard(position).getText());
    }

    private void openTemplateControl(){
        ll_temp.setVisibility(View.VISIBLE);
    }

    private void closeTextControl() {
        ll_text.setVisibility(View.GONE);

        Card c = cpa_main.getCards().get(position);
        c.setTitle(et_title.getText().toString());
        c.setText(et_text.getText().toString());

        et_title.setText("");
        et_text.setText("");

        cpa_main.notifyDataSetChanged();
    }

    private void closeTemplateControl(){
        ll_temp.setVisibility(View.GONE);
        cpa_main.notifyDataSetChanged();
    }

    private void requestSave(){
        project.setCards(cpa_main.getCards());
        saveProject(project);
    }

    private void requestShare(){
        requestSave();

        Intent share = new Intent();
        share.setAction(Intent.ACTION_SEND_MULTIPLE);
        share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        share.putExtra(Intent.EXTRA_SUBJECT, "Here are some files.");
        share.setType("image/png");

        ArrayList<Uri> files = new ArrayList<>();

        for(int i = 0; i < project.getCards().size(); i++){
            View v = cpa_main.getView(i);
            //Layout_to_Image lti = new Layout_to_Image(this, v);
            files.add(saveBitmap(loadBitmapFromView(v), i));
        }

        share.putParcelableArrayListExtra(Intent.EXTRA_STREAM, files);
        startActivity(share);
    }

    private void requestPick(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_PICK_PICTURE);
    }

    private void requestCrop(Uri uri){

        String destinationFileName = "image"+position;

        File dir = new File(getExternalFilesDir(null).getAbsolutePath() + "/" + project.getProjectName() + "/images");
        if(!dir.exists())
            dir.mkdir();

        UCrop uCrop = UCrop.of(uri, Uri.fromFile(new File(dir, destinationFileName)));

//        uCrop = basisConfig(uCrop);
//        uCrop = advancedConfig(uCrop);
        uCrop = uCrop.withAspectRatio(1, 1);

        uCrop.start(CardActivity.this);
    }

    private Uri saveBitmap(Bitmap bitmap, int i){
        String file_path = getExternalFilesDir(null).getAbsolutePath() +
                "/" + project.getProjectName() + "/cards";
        Log.d(TAG, file_path);
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

        return FileProvider.getUriForFile(this,
                this.getApplicationContext().getPackageName()
                        + ".my.package.name.provider", file);
    }

    private Bitmap loadBitmapFromView(View v){
        Bitmap b = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas c = new Canvas(b);
        v.layout(0, 0, v.getLayoutParams().width, v.getLayoutParams().height);
        v.draw(c);
        return b;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        this.position = position;
        tv_order.setText((position+1)+"번째 카드");
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
