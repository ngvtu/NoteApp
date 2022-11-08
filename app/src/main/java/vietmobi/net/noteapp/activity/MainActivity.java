package vietmobi.net.noteapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.concurrent.Executor;

import vietmobi.net.noteapp.Dialog;
import vietmobi.net.noteapp.R;
import vietmobi.net.noteapp.adapter.ViewPagerAdapter;
import vietmobi.net.noteapp.database.NoteDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    BottomNavigationView bottomNavigation;
    ViewPager viewPager;
    ImageView btnSettings;
    FloatingActionButton btnAdd;
    Dialog dialog = new Dialog();
    Context context;
    TextView tvAuthentication;
    private boolean showed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        addEvents();
        setUpViewPager();
    }

    public void showAuthentication(Context context) {
        Executor executor = ContextCompat.getMainExecutor(context);

        BiometricPrompt biometricPrompt = new BiometricPrompt(this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(), errString, Toast.LENGTH_SHORT).show();
                MainActivity.this.finish();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(), "xac thuc sinh trac thanh cong", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "Xac thuc sinh trac khong thanh cong, vui long thu lai", Toast.LENGTH_SHORT).show();
            }
        });

        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Xác thực người dùng")
                .setDescription("Quét vân tay để xác thực danh tính")
                .setNegativeButtonText("Thoát")
                .build();

        biometricPrompt.authenticate(promptInfo);
    }

    private void addEvents() {
        btnSettings.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
//        tvAuthentication.setOnClickListener(this);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_favorite:
                        viewPager.setCurrentItem(1);
                        showBtnAdd();

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
                        viewPager.setCurrentItem(0);
                        showBtnAdd();
                        btnAdd.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                                startActivity(intent);
                            }
                        });
                        return true;
                    case R.id.navigation_folder:
                        viewPager.setCurrentItem(2);
                        showBtnAdd();
                        btnAdd.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Dialog dialog = new Dialog();
                                dialog.showDialogCreateFolder(MainActivity.this);
                            }
                        });
                        return true;
                    case R.id.navigation_find:
                        viewPager.setCurrentItem(3);
                        hideBtnAdd();
                        return true;
                }
                return false;
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
            case R.id.tvAuthentication:
                showAuthentication(this);
                break;
        }
    }

    private void setUpViewPager(){
        showBtnAdd();
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        bottomNavigation.getMenu().findItem(R.id.navigation_all_note).setChecked(true);
                        break;
                    case 1:
                        bottomNavigation.getMenu().findItem(R.id.navigation_favorite).setChecked(true);
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

//    public void loadFragment(Fragment fragment) {
//        showBtnAdd();
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.viewPager, fragment);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
//    }

//    private void hiddenKeyboard() {
//        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
//        inputMethodManager.toggleSoftInput(InputMethodManager.RESULT_HIDDEN, 0);
//    }

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