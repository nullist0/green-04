package com.example.ksh.cardnewsapp;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
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

    private boolean isDeleting;

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(R.string.app_name);

        setContentView(R.layout.activity_main);

        permissionCheck();
        initVar();
        initView();
    }

    /**
     * Initialize Variables and Objects.
     */
    private void initVar(){
        loadProjects();
        items = new ArrayList<>();

        isDeleting = false;

        for(Project p : projects)
            items.add(p.getProjectName());
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, items);

        lv_main = findViewById(R.id.mainlistview);
    }

    /**
     * Initialize View Settings.
     */
    private void initView(){
        lv_main.setAdapter(adapter);
        lv_main.setOnItemClickListener(this);
    }

    /**
     * This method check permissions and request users to allow permissions.\n
     * Request Permissions : READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE.
     */
    private void permissionCheck(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                        PERMISSIONS_STORAGE,
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mm_add) {
            Toast.makeText(this, R.string.menu_main_add, Toast.LENGTH_SHORT).show();
            addProject();
            return true;
        }
        else if (id == R.id.mm_delete) {
            if(isDeleting) {
                Toast.makeText(this, R.string.menu_main_delete, Toast.LENGTH_SHORT).show();
                deleteProject();
                return true;
            }
            else{
                Toast.makeText(this, "Choose a project to delete.", Toast.LENGTH_SHORT).show();
            }
            isDeleting = !isDeleting;
            return true;
        }
        else if (id == R.id.mm_developer) {
            Intent intent = new Intent(this, DeveloperActivity.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.mm_license) {
            Intent intent = new Intent(this, LicenseActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Adds a Card News Project by AlertDialog
     */
    public void addProject() {
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);

        alert.setTitle("Title");
        alert.setMessage("Enter the name of project.");

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
        alert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                items.add(String.valueOf(name.getText()));
                projects.add(new Project(name.getText().toString()));

                saveProjects();

                adapter.notifyDataSetChanged();
            }
        });
        alert.show();
    }

    /**
     * Delete a Card News Project by RadioButton in ListView
     */
    public void deleteProject() {
        int count, checked;
        count = adapter.getCount();

        if (count > 0) {
            checked = lv_main.getCheckedItemPosition();

            if (checked > -1 && checked < count) {
                projects.remove(checked);
                items.remove(checked);

                lv_main.clearChoices();

                saveProjects();

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
        if(!isDeleting) {
            Intent intent = new Intent(MainActivity.this, CardActivity.class);
            intent.putExtra(INTENT_DATA, projects.get(position));
            startActivity(intent);
        }
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
