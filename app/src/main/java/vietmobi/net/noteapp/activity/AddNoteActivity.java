package vietmobi.net.noteapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;
import vietmobi.net.noteapp.R;

public class AddNoteActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView btnBack;
    Button btnSave;
    EditText edtTitle, edtContent;

    String title, content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        initViews();
        addEvents();
    }

    private void addEvents() {
        edtTitle.setSelection(0);
        btnBack.setOnClickListener(this);
        btnSave.setOnClickListener(this);

    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        btnSave = findViewById(R.id.btnSave);
        edtTitle = findViewById(R.id.edtTitle);
        edtContent = findViewById(R.id.edtContent);

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(AddNoteActivity.this, MainActivity.class);
        switch (view.getId()) {
            case R.id.btnBack:
                checkNoteToBack(intent);
                break;
            case R.id.btnSave:
                checkNoteToSave(intent);
                break;
        }

    }

    private void checkNoteToSave(Intent intent) {
        title = edtTitle.getText().toString();
        content = edtContent.getText().toString();
        if (title.equals("")) {
            Toast.makeText(AddNoteActivity.this, "Title not null", Toast.LENGTH_SHORT).show();
            edtTitle.setSelection(0);
        } else {
            saveNoteToDB();
            startActivity(intent);
        }
    }

    private void checkNoteToBack(Intent intent) {
        title = edtTitle.getText().toString();
        content = edtContent.getText().toString();
        if (title.equals("") && content.equals("")) {
            startActivity(intent);
        } else if (title.equals("")) {
            Toast.makeText(this, "Title not null", Toast.LENGTH_SHORT).show();
            edtTitle.setSelection(title.length());
        } else {
            new SweetAlertDialog(AddNoteActivity.this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Xác nhận")
                    .setContentText("Bạn có muốn lưu thay đổi?")
                    .setConfirmText("Có")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                saveNoteToDB();
                                startActivity(intent);
                                sweetAlertDialog.dismissWithAnimation();
                        }
                    })
                    .setCancelButton("Không", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                            startActivity(intent);
                        }
                    })
                    .show();
        }
    }

    private void saveNoteToDB() {
        Toast.makeText(this, "Save in database", Toast.LENGTH_SHORT).show();
    }
}