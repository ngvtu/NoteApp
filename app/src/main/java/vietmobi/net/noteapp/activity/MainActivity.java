package vietmobi.net.noteapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import vietmobi.net.noteapp.R;
import vietmobi.net.noteapp.fragment.AddNoteFragment;
import vietmobi.net.noteapp.fragment.AllNoteFragment;
import vietmobi.net.noteapp.fragment.FavoriteFragment;
import vietmobi.net.noteapp.fragment.FindNoteFragment;
import vietmobi.net.noteapp.fragment.FolderNoteFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    BottomNavigationView bottomNavigation;
    ImageView btnSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        loadFragment(new AllNoteFragment());
        addEvents();
    }

    private void addEvents() {
        btnSettings.setOnClickListener(this);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.navigation_favorite:
                        fragment = new FavoriteFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.navigation_all_note:
                        fragment = new AllNoteFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.navigation_add:
                        fragment = new AddNoteFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.navigation_folder:
                        fragment = new FolderNoteFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.navigation_find:
                        fragment = new FindNoteFragment();
                        loadFragment(fragment);
                        return true;

                }
                return false;
            }
        });
    }

    private void initViews() {
        bottomNavigation = findViewById(R.id.bottomNavigation);
        btnSettings = findViewById(R.id.btnSettings);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSettings:
                goToSettings();
                break;
        }
    }

    private void goToSettings() {
        Toast.makeText(this, "Go to settings", Toast.LENGTH_SHORT).show();
    }
    private void loadFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}