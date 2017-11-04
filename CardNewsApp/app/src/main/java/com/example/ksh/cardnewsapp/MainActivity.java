package com.example.ksh.cardnewsapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}
