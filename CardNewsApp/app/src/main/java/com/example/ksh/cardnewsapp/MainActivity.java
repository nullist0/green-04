package com.example.ksh.cardnewsapp;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ksh.cardnewsapp.data.Project;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements AdapterView.OnItemClickListener{
    private ArrayList<String> items;
    private ArrayAdapter adapter;
    private ListView lv_main;

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //액션바 코드
        //액션바 타이틀 변경하기
        getSupportActionBar().setTitle(R.string.app_name);
        //액션바 배경색 변경
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF339999));
        //홈버튼 표시
        //getActionBar().setDisplayHomeAsUpEnabled(false);

        setContentView(R.layout.activity_main);

        permissionCheck();

        initVar();
        initView();
    }//End of onCreate

    private void initVar(){
        loadProjects();
        items = new ArrayList<>();

        for(Project p : projects)
            items.add(p.getProjectName());

        // ArrayAdapter 생성. 아이템 View를 선택(single choice)가능하도록 만듦.
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, items);

        // listview 생성 및 adapter 지정.
        lv_main = findViewById(R.id.mainlistview);

    }

    private void initView(){
        lv_main.setAdapter(adapter);
        lv_main.setOnItemClickListener(this);
    }

    private void permissionCheck(){

        //권한 체크(permission check)

        // 권한을 획득하기전에 현재 Acivity에서 지정된 권한을 사용할 수 있는지 여부 체크
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                        PERMISSIONS_STORAGE,
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        }
    }

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

        if (id == R.id.add) {
//            Toast.makeText(this, "프로젝트 추가", Toast.LENGTH_SHORT).show();
            addProject();
            return true;
        }
        else if (id == R.id.delete) {
//            Toast.makeText(this, "프로젝트 삭제", Toast.LENGTH_SHORT).show();
            deleteProject();
            return true;
        }
        else if (id == R.id.menu1) {
//            Toast.makeText(this, "개발자 정보 클릭", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, DeveloperActivity.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.menu2) {
//            Toast.makeText(this, "라이센스 정보 클릭", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LicenseActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
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
                if(keyCode == KeyEvent.KEYCODE_ENTER)
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

                // 아이템 추가.
                items.add(String.valueOf(name.getText()));
                projects.add(new Project(name.getText().toString()));

                saveProjects();

                // listview 갱신
                adapter.notifyDataSetChanged();
            }
        });
        //팝업창 보이기
        alert.show();
    }

    public void deleteProject() {
        int count, checked;
        count = adapter.getCount();

        if (count > 0) {
            // 현재 선택된 아이템의 position 획득.
            checked = lv_main.getCheckedItemPosition();

            if (checked > -1 && checked < count) {
                // 아이템 삭제
                projects.remove(checked);
                items.remove(checked);

                // listview 선택 초기화.
                lv_main.clearChoices();

                // listview 갱신.
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS:
                Log.d(TAG, grantResults.length+"");
                if (grantResults.length == 0 ||
                        (grantResults[0] == PackageManager.PERMISSION_DENIED ||
                        grantResults[1] == PackageManager.PERMISSION_DENIED)) {
                    Toast.makeText(this, "Permission Denied.", Toast.LENGTH_SHORT).show();
                    this.finish();
                }else if(grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[1] == PackageManager.PERMISSION_GRANTED)
                    Log.d(TAG, "Granted");
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Intent intent = new Intent(MainActivity.this,CardActivity.class);
        intent.putExtra(INTENT_DATA, projects.get(position));
        startActivity(intent);
    }

    @Override
    protected void onStop(){
        super.onStop();
        loadProjects();
        saveProjects();
    }

    @Override
    protected void onResume(){
        super.onResume();
        loadProjects();
    }
}
