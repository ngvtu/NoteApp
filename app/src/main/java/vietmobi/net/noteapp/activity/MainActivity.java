package vietmobi.net.noteapp.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import vietmobi.net.noteapp.R;
import vietmobi.net.noteapp.fragment.AllNoteFragment;
import vietmobi.net.noteapp.fragment.FavoriteFragment;
import vietmobi.net.noteapp.fragment.FindNoteFragment;
import vietmobi.net.noteapp.fragment.FolderNoteFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    BottomNavigationView bottomNavigation;
    ImageView btnSettings;
    FloatingActionButton btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        addEvents();
        loadFragment(new FavoriteFragment());
    }

    private void addEvents() {
        btnSettings.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
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
        btnAdd = findViewById(R.id.btnAdd);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSettings:
                goToSettings();
                break;
            case R.id.btnAdd:
                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void goToSettings() {
        showDialogSettings();
    }

    private void showDialogSettings() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_sheet_layout);

        TextView tvChangePasswd = dialog.findViewById(R.id.tvChangePasswd);
        TextView tvDeletePasswd = dialog.findViewById(R.id.tvDeletePasswd);
        TextView tvAuthentication = dialog.findViewById(R.id.tvAuthentication);
        TextView tvPrivacyPolicy = dialog.findViewById(R.id.tvPrivacyPolicy);
        TextView tvMoreApp = dialog.findViewById(R.id.tvMoreApp);
        TextView tvShareWF = dialog.findViewById(R.id.tvShareWF);
        TextView tvRateMe5Star = dialog.findViewById(R.id.tvRateMe5Star);
        TextView tvFeedback = dialog.findViewById(R.id.tvFeedback);
        tvChangePasswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "ChangePassword", Toast.LENGTH_SHORT).show();
            }
        });
        tvDeletePasswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "DeletePassword", Toast.LENGTH_SHORT).show();
            }
        });
        tvAuthentication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Authentication", Toast.LENGTH_SHORT).show();
            }
        });
        tvPrivacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "PrivacyPolicy", Toast.LENGTH_SHORT).show();
            }
        });
        tvMoreApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "MoreApp", Toast.LENGTH_SHORT).show();
            }
        });
        tvShareWF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "ShareWithFriend", Toast.LENGTH_SHORT).show();
            }
        });
        tvRateMe5Star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "RateMe5Star", Toast.LENGTH_SHORT).show();
            }
        });
        tvFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "FeedBack", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}