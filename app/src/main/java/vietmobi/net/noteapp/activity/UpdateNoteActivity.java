package vietmobi.net.noteapp.activity;

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

import androidx.appcompat.app.AppCompatActivity;

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
        note = (Note) bundle.get("note");
        edtTitle.setText(note.getTitle());
        edtContent.setText(note.getContent());
        edtTitle.requestFocus(edtContent.length());

//        showKeyboard();
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(UpdateNoteActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

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
            finish();
        }
    }

    private void checkNoteToBack(Intent intent) {
        title = edtTitle.getText().toString();
        content = edtContent.getText().toString();
        if ((title.equals("") && content.equals("")) || (title.equals(edtTitle.getText().toString()) && content.equals(edtContent.getText().toString()))) {
            startActivity(intent);
            finish();
        } else {
            new SweetAlertDialog(UpdateNoteActivity.this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("X??c nh???n")
                    .setContentText("B???n c?? mu???n l??u thay ?????i?")
                    .setConfirmText("C??")
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
                                finish();
                                sweetAlertDialog.dismissWithAnimation();
                            }
                        }
                    })
                    .setCancelButton("Kh??ng", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                            startActivity(intent);
                            finish();
//                            hiddenKeyboard();
                        }
                    })
                    .show();
        }
    }

    private void upDateNoteToDatabase() {
        //Update Note
        note.setContent(edtContent.getText().toString());
        note.setTitle(edtTitle.getText().toString());
        note.setTime(java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()));

        NoteDatabase.getInstance(this).noteDAO().updateNote(note);
        Toast.makeText(this, "Update note successfully", Toast.LENGTH_SHORT).show();

        Intent intentResult = new Intent();
//        hiddenKeyboard();
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
}