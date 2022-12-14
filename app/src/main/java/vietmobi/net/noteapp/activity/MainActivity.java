package vietmobi.net.noteapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import vietmobi.net.noteapp.Dialog;
import vietmobi.net.noteapp.R;
import vietmobi.net.noteapp.adapter.NoteAdapter;
import vietmobi.net.noteapp.adapter.ViewPagerAdapter;
import vietmobi.net.noteapp.database.NoteDatabase;
import vietmobi.net.noteapp.fragment.AllNoteFragment;
import vietmobi.net.noteapp.fragment.FavoriteFragment;
import vietmobi.net.noteapp.fragment.FolderNoteFragment;
import vietmobi.net.noteapp.model.Note;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    BottomNavigationView bottomNavigation;
    ViewPager viewPager;
    ImageView btnSettings;
    FloatingActionButton btnAdd;
    Dialog dialog = new Dialog();
    Context context;
    TextView tvAuthentication;
    List<Note> listNote;
    private boolean showed;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        addEvents();
//        setUpViewPager();
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    private void addEvents() {
        btnSettings.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_favorite:
                        viewPager.setCurrentItem(1);
                        FavoriteFragment favoriteFragment = (FavoriteFragment) viewPager.getAdapter().instantiateItem(viewPager, 1);
                        favoriteFragment.addData();
                        break;
                    case R.id.navigation_all_note:
                        viewPager.setCurrentItem(0);
                        AllNoteFragment allNoteFragment = (AllNoteFragment) viewPager.getAdapter().instantiateItem(viewPager, 0);
                        allNoteFragment.configNote();
                        break;
                    case R.id.navigation_folder:
                        viewPager.setCurrentItem(2);
                        FolderNoteFragment folderNoteFragment = (FolderNoteFragment) viewPager.getAdapter().instantiateItem(viewPager, 2);
                        folderNoteFragment.addData();
                        break;
                    case R.id.navigation_find:
                        viewPager.setCurrentItem(3);
                        break;
                }
                return true;
            }
        });
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOffscreenPageLimit(3);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottomNavigation.getMenu().findItem(R.id.navigation_all_note).setChecked(true);
                        listNote = NoteDatabase.getInstance(context).noteDAO().getListNote();
                        NoteAdapter noteAdapter = new NoteAdapter(listNote, context);
                        noteAdapter.setData(listNote);
                        break;
                    case 1:
                        bottomNavigation.getMenu().findItem(R.id.navigation_favorite).setChecked(true);
                        listNote = NoteDatabase.getInstance(context).noteDAO().listFavoriteNote();
                        NoteAdapter noteAdapter2 = new NoteAdapter(listNote, context);
                        noteAdapter2.setData(listNote);
                        break;
                    case 2:
                        bottomNavigation.getMenu().findItem(R.id.navigation_folder).setChecked(true);
                        break;
                    case 3:
                        bottomNavigation.getMenu().findItem(R.id.navigation_find).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void initViews() {
        tvAuthentication = findViewById(R.id.tvAuthentication);
        bottomNavigation = findViewById(R.id.bottomNavigation);
        btnSettings = findViewById(R.id.btnSettings);
        btnAdd = findViewById(R.id.btnAdd);
        viewPager = findViewById(R.id.viewPager);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSettings:
                dialog.showDialogSettings(this);
                break;
            case R.id.btnAdd:
                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                startActivity(intent);
                break;
        }
    }


    public void showBtnAdd() {
        if (!showed) {
            btnAdd.setVisibility(View.INVISIBLE);
            TranslateAnimation animate = new TranslateAnimation(0, 0, btnAdd.getHeight(), 0);
            animate.setDuration(500);
            animate.setFillAfter(true);
            btnAdd.startAnimation(animate);
        }
    }

    public void hideBtnAdd() {
        btnAdd.setVisibility(View.INVISIBLE);
        TranslateAnimation animate = new TranslateAnimation(0, 0, 0, btnAdd.getHeight());
        animate.setDuration(0);
        btnAdd.startAnimation(animate);
    }
}