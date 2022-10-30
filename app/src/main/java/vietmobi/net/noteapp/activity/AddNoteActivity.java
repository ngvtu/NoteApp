package vietmobi.net.noteapp.activity;

import static vietmobi.net.noteapp.adapter.NoteAdapter.MY_REQUEST_CODE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.Calendar;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import vietmobi.net.noteapp.R;
import vietmobi.net.noteapp.adapter.NoteAdapter;
import vietmobi.net.noteapp.database.NoteDatabase;
import vietmobi.net.noteapp.model.Note;

public class AddNoteActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView btnBack;
    Button btnSave;
    EditText edtTitle, edtContent;
    String title, content;
    NoteAdapter noteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        initViews();
        addEvents();
    }

    private void addEvents() {
        edtTitle.requestFocus();
        showKeyboard();
        btnBack.setOnClickListener(this);
        btnSave.setOnClickListener(this);
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
            edtTitle.requestFocus();
            showKeyboard();
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
            hiddenKeyboard();
        } else {
            new SweetAlertDialog(AddNoteActivity.this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Xác nhận")
                    .setContentText("Bạn có muốn lưu thay đổi?")
                    .setConfirmText("Có")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            if (title.equals("")) {
                                Toast.makeText(AddNoteActivity.this, "Title not null", Toast.LENGTH_SHORT).show();
                                sweetAlertDialog.dismissWithAnimation();
                                edtTitle.requestFocus();
                                showKeyboard();
                            } else {
                                saveNoteToDB();
                                startActivity(intent);
                                sweetAlertDialog.dismissWithAnimation();
                                hiddenKeyboard();
                            }
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
        String title = edtTitle.getText().toString();
        String content = edtContent.getText().toString();
        String time = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        Note note = new Note(title, content, time);

        // Insert Note to Database
        NoteDatabase.getInstance(this).noteDAO().insertNote(note);
        Toast.makeText(this, "Add Note successfully", Toast.LENGTH_SHORT).show();

        edtTitle.setText("");
        edtContent.setText("");
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        btnSave = findViewById(R.id.btnSave);
        edtTitle = findViewById(R.id.edtTitle);
        edtContent = findViewById(R.id.edtContent);
    }

    private void showKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    private void hiddenKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.RESULT_HIDDEN, 0);
    }


}