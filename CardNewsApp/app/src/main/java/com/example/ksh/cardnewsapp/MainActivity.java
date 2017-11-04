package com.example.ksh.cardnewsapp;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.ksh.cardnewsapp.R.id.add;
import static com.example.ksh.cardnewsapp.R.id.delete;


public class MainActivity extends AppCompatActivity {
    public ArrayList<String> items;
    public ArrayAdapter adapter;
    public ListView mainlistview;

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    }; //출처: http://appsnuri.tistory.com/128 [이야기앱 세상] - 출처o, 변경o, 상업적사용x


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 권한을 획득하기전에 현재 Acivity에서 지정된 권한을 사용할 수 있는지 여부 체크
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // 권한 획득에 대한 설명 보여주기
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // 사용자에게 권한 획득에 대한 설명을 보여준 후 권한 요청을 수행

            } else {

                // 권한 획득의 필요성을 설명할 필요가 없을 때는 아래 코드를
                //수행해서 권한 획득 여부를 요청한다.

                ActivityCompat.requestPermissions(this,
                        PERMISSIONS_STORAGE,
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

            }
        }

        // 권한을 획득하기전에 현재 Acivity에서 지정된 권한을 사용할 수 있는지 여부 체크
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // 권한 획득에 대한 설명 보여주기
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // 사용자에게 권한 획득에 대한 설명을 보여준 후 권한 요청을 수행

            } else {

                // 권한 획득의 필요성을 설명할 필요가 없을 때는 아래 코드를
                //수행해서 권한 획득 여부를 요청한다.

                ActivityCompat.requestPermissions(this,
                        PERMISSIONS_STORAGE,
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

            }
        }


        출처: http://appsnuri.tistory.com/128 [이야기앱 세상]

        //리스트 뷰 코드

        // 빈 데이터 리스트 생성.
        items = new ArrayList<String>();
        // ArrayAdapter 생성. 아이템 View를 선택(single choice)가능하도록 만듦.
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, items);

        // listview 생성 및 adapter 지정.
        mainlistview = (ListView) findViewById(R.id.mainlistview);
        mainlistview.setAdapter(adapter);

        //리스트 뷰 코드 끝


        //액션바 코드

        //액션바 타이틀 변경하기
        getSupportActionBar().setTitle("CARDNEWS");
        //액션바 배경색 변경
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF339999));
        //홈버튼 표시
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        //액션바 숨기기
        //hideActionBar();

        //액션바 코드 끝
    }//End of onCreate

    //액션버튼 메뉴 액션바에 집어 넣기
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1, menu);
        return true;
    }

    //액션버튼을 클릭했을때의 동작
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
//        //or switch문을 이용하면 될듯 하다.
//        if (id == android.R.id.home) {
//            Toast.makeText(this, "홈아이콘 클릭", Toast.LENGTH_SHORT).show();
//            return true;
//        }

        if (id == add) {
            Toast.makeText(this, "프로젝트 추가", Toast.LENGTH_SHORT).show();
            addProject();
            return true;
        }
        if (id == delete) {
            Toast.makeText(this, "프로젝트 추가", Toast.LENGTH_SHORT).show();
            deleteProject();
            return true;
        }
        if (id == R.id.menu1) {
            Toast.makeText(this, "개발자 정보 클릭", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, DeveloperActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.menu2) {
            Toast.makeText(this, "라이센스 정보 클릭", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LicenseActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //액션바 숨기기
    private void hideActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.hide();
    }

    public void addProject() {
        //팝업창->이름결정
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);

        alert.setTitle("제목");
        alert.setMessage("카드뉴스 제목을 입력하세요");

        final EditText name = new EditText(this);

        name.setOnKeyListener(new EditText.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == event.KEYCODE_ENTER)
                {
                    return true;
                }
                return false;
            }
        });

        alert.setView(name);
        //팝업창 클릭버튼
        alert.setPositiveButton("결정", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String newsname = name.getText().toString();
                Intent intent = new Intent(MainActivity.this,CardActivity.class);
                intent.putExtra("news_name", newsname);
                startActivity(intent);

                // 아이템 추가.
                items.add(String.valueOf(name.getText()));

                // listview 갱신
                adapter.notifyDataSetChanged();
            }
        });
        //팝업창 보이기
        alert.show();
//        // 아이템 추가.
//        items.add(String.valueOf(name.getText()));
//
//        // listview 갱신
//        adapter.notifyDataSetChanged();
    }

    public void deleteProject() {
        int count, checked;
        count = adapter.getCount();

        if (count > 0) {
            // 현재 선택된 아이템의 position 획득.
            checked = mainlistview.getCheckedItemPosition();

            if (checked > -1 && checked < count) {
                // 아이템 삭제
                items.remove(checked);

                // listview 선택 초기화.
                mainlistview.clearChoices();

                // listview 갱신.
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                //권한 획득이 거부되면 결과 배열은 비어있게 됨
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    //권한 획득이 허용되면 수행해야 할 작업이 표시됨
                    //일반적으로 작업을 처리할 메서드를 호출

                } else {

                    //권한 획득이 거부되면 수행해야 할 적업이 표시됨
                    //일반적으로 작업을 처리할 메서드를 호출
                }
                return;
            }
        }
    }
}
