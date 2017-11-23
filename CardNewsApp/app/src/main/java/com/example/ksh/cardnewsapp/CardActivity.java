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

    //Adapter and ViewPager
    private CardPagerAdapter cpa_main;
    private UltraViewPager uvp_main;

    //Views
    private TextView tv_order;
    private Button bt_image, bt_text, bt_temp;
    private LinearLayout ll_text, ll_temp;

    //ll_text
    private EditText et_text, et_title;
    private Button bt_confirm_text;

    //ll_temp
    private Button bt_template1; //For prototype
    private Button bt_confirm_temp;

    private Project project;

    private Uri target = null;
    private int position = 0;

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_card);

        initVar();
        initView();
    }

    /**
     * Initialize Variables and Objects.
     */
    private void initVar(){
        //initialize from intent
        project = (Project) getIntent().getSerializableExtra(INTENT_DATA);

        //initialize view variables from Layout
        uvp_main = findViewById(R.id.card_uvp_main);

        tv_order = findViewById(R.id.card_tv_order);

        bt_image = findViewById(R.id.card_bt_image);
        bt_text = findViewById(R.id.card_bt_text);
        bt_temp = findViewById(R.id.card_bt_template);

        ll_text = findViewById(R.id.card_ll_control_text);
        ll_temp = findViewById(R.id.card_ll_control_template);

        et_text = findViewById(R.id.card_et_text);
        et_title = findViewById(R.id.card_et_title);

        bt_template1 = findViewById(R.id.card_bt_temp1);

        bt_confirm_temp = findViewById(R.id.card_bt_confirm_temp);
        bt_confirm_text = findViewById(R.id.card_bt_confirm_text);

        //initialize adapter
        cpa_main = new CardPagerAdapter(getApplicationContext(), project.getCards());
    }

    /**
     * Initialize View Settings.
     */
    private void initView(){
        //Setting uvp_main
        uvp_main.setAdapter(cpa_main);
        uvp_main.setOnPageChangeListener(this);

        //Set to use one screen
        uvp_main.setMultiScreen(1.0f);

        //Set aspect ratio as 1:1
        uvp_main.setItemRatio(1.0f);
        uvp_main.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        uvp_main.setAutoMeasureHeight(true);

        //initialize text
        tv_order.setText(getString(R.string.card_count, position+1));

        //initialize buttons
        bt_image.setOnClickListener(this);
        bt_text.setOnClickListener(this);
        bt_temp.setOnClickListener(this);

        bt_template1.setOnClickListener(this);

        bt_confirm_text.setOnClickListener(this);
        bt_confirm_temp.setOnClickListener(this);

        getSupportActionBar().setTitle(project.getProjectName());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode == RESULT_OK){
            if (requestCode == UCrop.REQUEST_CROP) {
                Uri resultUri = UCrop.getOutput(data);
                if(resultUri != null) {
                    cpa_main.getCard(position).setFileDir(resultUri.getPath());
                    cpa_main.notifyDataSetChanged();
                }
            } else if(requestCode == REQUEST_PICK_PICTURE){
                if(data.getData() != null) {
                    target = data.getData();
                    Log.d(TAG, data.getData().getPath());
                    requestCrop(target);
                }
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
            if (cropError != null) {
                cropError.printStackTrace();
            }
        }
    }

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
                cpa_main.notifyDataSetChanged();
                break;
        }
    }

    /**
     * Delete a Card to Project in current position.
     */
    private void deleteCard(){
        cpa_main.removeCard(position);
        cpa_main.notifyDataSetChanged();
    }

    /**
     * Add a Card to Project in current position.
     */
    private void addCard(){
        cpa_main.addCard(position);
        cpa_main.notifyDataSetChanged();
    }

    /**
     * Request to pick and crop image to add to current card.
     */
    private void requestImage(){
        requestPick();
    }

    /**
     * Open Text Control which changes card Texts.
     */
    private void openTextControl(){
        ll_text.setVisibility(View.VISIBLE);

        et_title.setText(cpa_main.getCard(position).getTitle());
        et_text.setText(cpa_main.getCard(position).getText());
    }

    /**
     * Open Template Control which changes Template status.
     */
    private void openTemplateControl(){
        ll_temp.setVisibility(View.VISIBLE);
    }

    /**
     * Close Text Control which changes card Texts.
     */
    private void closeTextControl() {
        ll_text.setVisibility(View.GONE);

        Card c = cpa_main.getCards().get(position);
        c.setTitle(et_title.getText().toString());
        c.setText(et_text.getText().toString());

        et_title.setText("");
        et_text.setText("");

        cpa_main.notifyDataSetChanged();
    }

    /**
     * Close Template Control which changes Template status.
     */
    private void closeTemplateControl(){
        ll_temp.setVisibility(View.GONE);
        cpa_main.notifyDataSetChanged();
    }

    /**
     * Request to save current project status.
     */
    private void requestSave(){
        project.setCards(cpa_main.getCards());
        saveProject(project);
    }

    /**
     * Request to share cards.
     */
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
            Log.d(TAG, i+" : "+v.getHeight() +"x" + v.getWidth());
            files.add(saveBitmap(loadBitmapFromView(v), i));
        }

        share.putParcelableArrayListExtra(Intent.EXTRA_STREAM, files);
        startActivity(share);
    }

    /**
     * Request to pick an image to add in card from gallery.
     */
    private void requestPick(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "Choose Image"), REQUEST_PICK_PICTURE);
    }

    /**
     * Request a crop given image via uCrop
     * @param uri is Uri of image file to crop.
     */
    private void requestCrop(Uri uri){

        String destinationFileName = "image"+position+".png";

        File dir = new File(getExternalFilesDir(null).getAbsolutePath() + "/" + project.getProjectName() + "/images");
        File file = new File(dir, destinationFileName);
        if(!dir.exists()) {
            dir.mkdirs();
        }

        if(file.exists()) {
            file.delete();
        }

        Log.d(TAG, uri.getPath());

        UCrop uCrop = UCrop.of(uri, Uri.fromFile(file));

        //Set aspect ratio 1:1
        uCrop = uCrop.withAspectRatio(1, 1);
        uCrop.start(CardActivity.this);
    }

    /**
     * Save a card data(Bitmap) from project with its position.
     * @param bitmap is a Bitmap Object which is created by loadBitmapFromView(View).
     * @param i is position of that card.
     * @return an Uri Object has saved file data.
     */
    private Uri saveBitmap(Bitmap bitmap, int i){
        String file_path = getExternalFilesDir(null).getAbsolutePath() +
                "/" + project.getProjectName() + "/cards";
        Log.d(TAG, file_path);

        File dir = new File(file_path);
        if(!dir.exists()) {
            dir.mkdirs();
        }

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

    /**
     * Convert View(v) to Bitmap by Canvas
     * @param v is View Object to convert
     * @return a Bitmap Object same as v.
     */
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
        tv_order.setText(getString(R.string.card_count, position+1));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
