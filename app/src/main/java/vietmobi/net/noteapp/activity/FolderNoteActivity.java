package vietmobi.net.noteapp.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;
import vietmobi.net.noteapp.R;

public class FolderNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder_note);


    }

    public void BasicMessage() {
        new SweetAlertDialog(this).setTitleText("Here's a message!").show();
    }

    public void titleWithText() {
        new SweetAlertDialog(this).setTitleText("Here's a message!").setContentText("It's pretty, isn't it?").show();
    }

    public void errorMessage() {
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE).setTitleText("Oops...").setContentText("Something went wrong!").show();
    }

    public void warningMessage() {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE).setTitleText("Are you sure?").setContentText("Won't be able to recover this file!").setConfirmText("Yes,delete it!").show();
    }

    public void successMessage() {
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE).setTitleText("Good job!").setContentText("You clicked the button!").show();

    }

    public void withCustomIcon() {
        new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE).setTitleText("Sweet!").setContentText("Here's a custom image.").setCustomImage(R.drawable.ic_launcher_foreground).show();

    }

    public void withConfirmButton() {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE).setTitleText("Are you sure?").setContentText("Won't be able to recover this file!").setConfirmText("Yes,delete it!").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sDialog) {
                // Showing simple toast message to user
                Toast.makeText(FolderNoteActivity.this, " You Clicked me ", Toast.LENGTH_SHORT).show();

                sDialog.dismissWithAnimation();
            }
        }).show();
    }

    public void withCancelButtonListener() {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("Won't be able to recover this file!")
                .setCancelText("No,cancel plx!")
                .setConfirmText("Yes,delete it!")
                .showCancelButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        // Showing simple toast message to user
                        Toast.makeText(FolderNoteActivity.this, " You clicked Cancel ", Toast.LENGTH_SHORT).show();
                        sDialog.cancel();
                    }
                }).show();
    }

    public void changeOnConfirm() {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("Won't be able to recover this file!")
                .setConfirmText("Yes,delete it!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.setTitleText("Deleted!")
                                .setContentText("Your imaginary file has been deleted!")
                                .setConfirmText("OK")
                                .setConfirmClickListener(null)
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                    }
                }).show();
    }
}