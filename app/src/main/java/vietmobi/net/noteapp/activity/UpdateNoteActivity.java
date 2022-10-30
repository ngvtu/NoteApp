package vietmobi.net.noteapp.activity;

import androidx.appcompat.app.AppCompatActivity;

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

import java.util.Calendar;

import cn.pedant.SweetAlert.SweetAlertDialog;
import vietmobi.net.noteapp.R;
import vietmobi.net.noteapp.database.NoteDatabase;
import vietmobi.net.noteapp.model.Note;

public class UpdateNoteActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView btnBack;
    Button btnSave;
    EditText edtTitle, edtContent;
    String title, content;
    Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);

        initViews();
        addEvents();
    }

    private void addEvents() {
        getNoteToEdit();
        btnBack.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }

    private void getNoteToEdit() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        Note note = (Note) bundle.get("note");
        edtTitle.setText(note.getTitle());
        edtContent.setText(note.getContent());
        edtTitle.requestFocus(edtContent.length());
        showKeyboard();
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(UpdateNoteActivity.this, MainActivity.class);
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
            Toast.makeText(UpdateNoteActivity.this, "Title not null", Toast.LENGTH_SHORT).show();
            edtTitle.requestFocus();
        } else {
            upDateNoteToDatabase();
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
            new SweetAlertDialog(UpdateNoteActivity.this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Xác nhận")
                    .setContentText("Bạn có muốn lưu thay đổi?")
                    .setConfirmText("Có")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            if (title.equals("")) {
                                Toast.makeText(UpdateNoteActivity.this, "Title not null", Toast.LENGTH_SHORT).show();
                                sweetAlertDialog.dismissWithAnimation();
                                edtTitle.requestFocus();
                            } else {
                                upDateNoteToDatabase();
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
                            hiddenKeyboard();
                        }
                    })
                    .show();
        }
    }

    private void upDateNoteToDatabase() {
        //Update Note
        note.setContent(edtContent.getText().toString());
        note.setTitle(edtContent.getText().toString());
        note.setTime(java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()));

        NoteDatabase.getInstance(this).noteDAO().updateNote(note);
        Toast.makeText(this, "Update note successfully", Toast.LENGTH_SHORT).show();

        Intent intentResult = new Intent();
        setResult(Activity.RESULT_OK, intentResult);
        finish();
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