package vietmobi.net.noteapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import cn.pedant.SweetAlert.SweetAlertDialog;
import vietmobi.net.noteapp.R;
import vietmobi.net.noteapp.database.NoteDatabase;
import vietmobi.net.noteapp.model.Note;

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
        title = edtTitle.getText().toString().trim();
        content = edtContent.getText().toString().trim();
        if (title.length() == 0) {
            Toast.makeText(AddNoteActivity.this, "Title not null", Toast.LENGTH_SHORT).show();
            edtTitle.requestFocus();
//            showKeyboard();
        } else {
            saveNoteToDB();
//            hiddenKeyboard();
            startActivity(intent);
        }
    }

    private void checkNoteToBack(Intent intent) {
        title = edtTitle.getText().toString().trim();
        content = edtContent.getText().toString().trim();
        if (title.equals("") && content.equals("")) {
            hiddenKeyboard();
            startActivity(intent);
        } else {
            new SweetAlertDialog(AddNoteActivity.this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Confirm?")
                    .setContentText("Do you want to save the changes?")
                    .setConfirmText("Yes")
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
                    .setCancelButton("No", new SweetAlertDialog.OnSweetClickListener() {
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
        String title = edtTitle.getText().toString().trim();
        String content = edtContent.getText().toString().trim();
        String time = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        Note note = new Note(title, content, time);

        // Insert Note to Database
        NoteDatabase.getInstance(this).noteDAO().insertNote(note);
        Toast.makeText(this, "Add Note successfully", Toast.LENGTH_SHORT).show();
        edtTitle.setText("");
        edtContent.setText("");
        hiddenKeyboard();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        btnSave = findViewById(R.id.btnSave);
        edtTitle = findViewById(R.id.edtTitle);
        edtContent = findViewById(R.id.edtContent);
    }

    public void showKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    private void hiddenKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.RESULT_HIDDEN, 0);
    }


}