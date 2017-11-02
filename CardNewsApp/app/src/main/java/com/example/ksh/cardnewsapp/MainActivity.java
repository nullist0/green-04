package com.example.ksh.cardnewsapp;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.ksh.cardnewsapp.R.id.add;
import static com.example.ksh.cardnewsapp.R.id.delete;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //리스트 뷰 코드

        // 빈 데이터 리스트 생성.
        final ArrayList<String> items = new ArrayList<String>() ;



        // ArrayAdapter 생성. 아이템 View를 선택(single choice)가능하도록 만듦.
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, items) ;

        // listview 생성 및 adapter 지정.
        final ListView listview = (ListView) findViewById(R.id.mainlistview) ;
        listview.setAdapter(adapter) ;

//        // add button에 대한 이벤트 처리.
//        Button addButton = (Button)findViewById(R.id.add) ;
//        addButton.setOnClickListener(new Button.OnClickListener() {
//            public void onClick(View v) {
//                int count;
//                count = adapter.getCount();
//
//                // 아이템 추가.
//                items.add("LIST" + Integer.toString(count + 1));
//
//                // listview 갱신
//                adapter.notifyDataSetChanged();
//            }
//        }) ;
//
//        // delete button에 대한 이벤트 처리.
//        Button deleteButton = (Button)findViewById(delete) ;
//        deleteButton.setOnClickListener(new Button.OnClickListener() {
//            public void onClick(View v) {
//                int count, checked ;
//                count = adapter.getCount() ;
//
//                if (count > 0) {
//                    // 현재 선택된 아이템의 position 획득.
//                    checked = listview.getCheckedItemPosition();
//
//                    if (checked > -1 && checked < count) {
//                        // 아이템 삭제
//                        items.remove(checked) ;
//
//                        // listview 선택 초기화.
//                        listview.clearChoices();
//
//                        // listview 갱신.
//                        adapter.notifyDataSetChanged();
//                    }
//                }
//            }
//        }) ;

        //액션바 타이틀 변경하기
        getSupportActionBar().setTitle("CARDNEWS");
        //액션바 배경색 변경
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF339999));
        //홈버튼 표시
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        //액션바 숨기기
        //hideActionBar();
    }

    //액션버튼 메뉴 액션바에 집어 넣기
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1, menu);
        return true;
    }

    //액션버튼을 클릭했을때의 동작
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
//        //or switch문을 이용하면 될듯 하다.
//        if (id == android.R.id.home) {
//            Toast.makeText(this, "홈아이콘 클릭", Toast.LENGTH_SHORT).show();
//            return true;
//        }

        if (id == add) {
            Toast.makeText(this, "프로젝트 추가", Toast.LENGTH_SHORT).show();

            return true;
        }
        if (id == delete) {
            Toast.makeText(this, "프로젝트 추가", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.menu1) {
            Toast.makeText(this, "개발자 정보 클릭", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.menu2) {
            Toast.makeText(this, "라이센스 정보 클릭", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //액션바 숨기기
    private void hideActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
            actionBar.hide();
    }


    @Override
    public void onClick(View view) {

    }
}
