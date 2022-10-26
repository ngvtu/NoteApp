package vietmobi.net.noteapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import vietmobi.net.noteapp.R;

public class AllNoteActivity extends AppCompatActivity {
    ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_note);
        initViews();
        addEvents();
    }

    private void addEvents() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AllNoteActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
    }
}