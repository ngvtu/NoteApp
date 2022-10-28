package vietmobi.net.noteapp;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class DialogSettings {
    String PASSWORD = "1612";

    public void showDialogChangePassWord(Context context) {
        final Dialog dialog = new Dialog(context/*, android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen*/);
        Window window = dialog.getWindow();
        dialog.setContentView(R.layout.dialog_change_passwd);
        TextInputLayout textInputLayout = dialog.findViewById(R.id.textInputLayout);
        TextInputLayout textInputLayout2 = dialog.findViewById(R.id.textInputLayout2);
        TextInputEditText edtOldPassWord = dialog.findViewById(R.id.edtOldPassWord);
        TextInputEditText edtNewPassWord = dialog.findViewById(R.id.edtNewPassWord);
        TextView btnCancel = dialog.findViewById(R.id.btnCancel);
        TextView btnAccept = dialog.findViewById(R.id.btnAccept);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pass = String.valueOf(edtOldPassWord.getText());
                String newPass = String.valueOf(edtNewPassWord.getText());
                if (pass.equals(PASSWORD)) {
                    if (newPass.length() >= 4){
                        Toast.makeText(context, "Change Password Complete!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    } else{
                        textInputLayout2.setError("Password at least 4 numbers");
                        textInputLayout2.requestFocus();
                    }
                } else {
                    textInputLayout.setError("Invalid password");
                    textInputLayout.requestFocus();
                }
            }
        });
        dialog.show();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void showDialogDeletePassword(Context context) {
        final Dialog dialog = new Dialog(context/*, android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen*/);
        Window window = dialog.getWindow();
        dialog.setContentView(R.layout.dialog_delete_passwd);
        TextInputLayout textInputLayout = dialog.findViewById(R.id.textInputLayout2);
        TextInputEditText edtNewPassWorld = dialog.findViewById(R.id.edtPassWorld);
        TextView btnCancel = dialog.findViewById(R.id.btnCancel);
        TextView btnAccept = dialog.findViewById(R.id.btnAccept);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pass = String.valueOf(edtNewPassWorld.getText());
                if (pass.equals(PASSWORD)){
                    Toast.makeText(context, "Delete Password", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else{
                    textInputLayout.setError("Invalid password");
                    textInputLayout.requestFocus();
                }
            }
        });
        dialog.show();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
    public void showDialogSettings(Context context){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_sheet_settings);

        TextView tvChangePasswd = dialog.findViewById(R.id.tvChangePasswd);
        TextView tvDeletePasswd = dialog.findViewById(R.id.tvDeletePasswd);
        TextView tvAuthentication = dialog.findViewById(R.id.tvAuthentication);
        TextView tvPrivacyPolicy = dialog.findViewById(R.id.tvPrivacyPolicy);
        TextView tvMoreApp = dialog.findViewById(R.id.tvMoreApp);
        TextView tvShareWF = dialog.findViewById(R.id.tvShareWF);
        TextView tvRateMe5Star = dialog.findViewById(R.id.tvRateMe5Star);
        TextView tvFeedback = dialog.findViewById(R.id.tvFeedback);
        tvChangePasswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            showDialogChangePassWord(context);
            }
        });
        tvDeletePasswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            showDialogDeletePassword(context);
            }
        });
        tvAuthentication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Authentication", Toast.LENGTH_SHORT).show();
            }
        });
        tvPrivacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "PrivacyPolicy", Toast.LENGTH_SHORT).show();
            }
        });
        tvMoreApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "MoreApp", Toast.LENGTH_SHORT).show();
            }
        });
        tvShareWF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "ShareWithFriend", Toast.LENGTH_SHORT).show();
            }
        });
        tvRateMe5Star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "RateMe5Star", Toast.LENGTH_SHORT).show();
            }
        });
        tvFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "FeedBack", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }
}
