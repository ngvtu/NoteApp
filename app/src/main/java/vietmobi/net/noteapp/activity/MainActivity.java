package vietmobi.net.noteapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import vietmobi.net.noteapp.Dialog;
import vietmobi.net.noteapp.R;
import vietmobi.net.noteapp.RecyclerViewInterface;
import vietmobi.net.noteapp.database.NoteDatabase;
import vietmobi.net.noteapp.fragment.AllNoteFragment;
import vietmobi.net.noteapp.fragment.FavoriteFragment;
import vietmobi.net.noteapp.fragment.FindNoteFragment;
import vietmobi.net.noteapp.fragment.FolderNoteFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, RecyclerViewInterface {
    BottomNavigationView bottomNavigation;
    ImageView btnSettings;
    FloatingActionButton btnAdd;
    Dialog dialog = new Dialog();
    Context context;
    private boolean showed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hiddenKeyboard();
        initViews();
        addEvents();
        loadFragment(new AllNoteFragment());
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
                        NoteDatabase.getInstance(context).noteDAO().listFavoriteNote();
                        btnAdd.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(MainActivity.this, "In favorite", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                                startActivity(intent);
                            }
                        });
                        return true;
                    case R.id.navigation_all_note:
                        fragment = new AllNoteFragment();
                        loadFragment(fragment);
                        btnAdd.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                                startActivity(intent);
                            }
                        });
                        return true;
                    case R.id.navigation_folder:
                        fragment = new FolderNoteFragment();
                        loadFragment(fragment);
                        btnAdd.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Dialog dialog = new Dialog();
                                dialog.showDialogCreateFolder(MainActivity.this);
                            }
                        });
                        return true;
                    case R.id.navigation_find:
                        fragment = new FindNoteFragment();
                        loadFragment(fragment);
                        hideBtnAdd();
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
                dialog.showDialogSettings(this);
                break;
            case R.id.btnAdd:
                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                startActivity(intent);
                break;
        }
    }

    public void loadFragment(Fragment fragment) {
        showBtnAdd();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void hiddenKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.RESULT_HIDDEN, 0);
    }

    @Override
    public void configNote() {

    }

    public void showBtnAdd(){
        if (!showed){
            btnAdd.setVisibility(View.INVISIBLE);
            TranslateAnimation animate = new TranslateAnimation(0, 0, btnAdd.getHeight(), 0);
            animate.setDuration(500);
            animate.setFillAfter(true);
            btnAdd.startAnimation(animate);
        }
    }
    public void hideBtnAdd(){
        btnAdd.setVisibility(View.INVISIBLE);
        TranslateAnimation animate = new TranslateAnimation(0, 0, 0, btnAdd.getHeight());
        animate.setDuration(0);
        btnAdd.startAnimation(animate);
    }
}