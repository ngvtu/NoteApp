package vietmobi.net.noteapp;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import vietmobi.net.noteapp.activity.MainActivity;
import vietmobi.net.noteapp.adapter.ListFolderAdapter;
import vietmobi.net.noteapp.database.FolderNoteDatabase;
import vietmobi.net.noteapp.database.NoteDatabase;
import vietmobi.net.noteapp.model.Folder;
import vietmobi.net.noteapp.model.Note;

public class Dialog implements View.OnClickListener {
    String PASSWORD = "1612";
    Folder folder;
    ListFolderAdapter listFolderAdapter;
    List<Folder> listFolder;
    Context context;

    private String filename = "internalStorage.txt";

    //Thư mục do mình đặt
    private String filepath = "ThuMucCuaToi";
    File myInternalFile;

    //    SharedPreferences sharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
    public void createFile(Note note) {
        ContextWrapper contextWrapper = new ContextWrapper(context.getApplicationContext());
        File directory = contextWrapper.getDir(filepath, Context.MODE_PRIVATE);
        myInternalFile = new File(directory, filename);
        String content = note.getContent();
        String title = note.getTitle();
        try {
            FileOutputStream fos = new FileOutputStream(myInternalFile);
            fos.write(title.getBytes());
            fos.write("/n".getBytes());
            fos.write(content.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(contextWrapper, "create file ok", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);

        intent.setType("text/*");
        intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("" + filepath));


        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(Intent.createChooser(intent, "Share file"));
        }

    }

    public void showDialogChangePassWord(Context context) {
        final android.app.Dialog dialog = new android.app.Dialog(context/*, android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen*/);
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
                SharedPreferences sharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                String PASS = sharedPreferences.getString("pass", "");
                if (pass.equals(PASS)) {
                    if (newPass.length() >= 4) {
                        Toast.makeText(context, "Change Password Complete!", Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("pass", newPass);
                        editor.apply();
                        dialog.dismiss();
                    } else {
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
        final android.app.Dialog dialog = new android.app.Dialog(context/*, android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen*/);
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
                SharedPreferences sharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                String PASS = sharedPreferences.getString("pass", "");

                if (pass.equals(PASS)) {
                    Toast.makeText(context, "Delete Password", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("pass", "");
                    editor.apply();
                    deletePasswdAllNote();
                    dialog.dismiss();
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
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

    private void deletePasswdAllNote() {
        List<Note> listNoteLocked = NoteDatabase.getInstance(context).noteDAO().listNoteIsLocked();
        for (int i = 0; i < listNoteLocked.size(); i++) {
            Note note = listNoteLocked.get(i);
            note.setLocked(false);
            NoteDatabase.getInstance(context).noteDAO().updateNote(note);
        }
    }

    public void showDialogSettings(Context context) {
        android.app.Dialog dialog = new android.app.Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_sheet_settings);
        createDialog();

        TextView tvChangePasswd = dialog.findViewById(R.id.tvChangePasswd);
        TextView tvDeletePasswd = dialog.findViewById(R.id.tvDeletePasswd);
        TextView tvSetPasswd = dialog.findViewById(R.id.tvSetPasswd);
        TextView tvAuthentication = dialog.findViewById(R.id.tvAuthentication);
        TextView tvPrivacyPolicy = dialog.findViewById(R.id.tvPrivacyPolicy);
        TextView tvMoreApp = dialog.findViewById(R.id.tvMoreApp);
        TextView tvShareWF = dialog.findViewById(R.id.tvShareWF);
        TextView tvRateMe5Star = dialog.findViewById(R.id.tvRateMe5Star);
        TextView tvFeedback = dialog.findViewById(R.id.tvFeedback);
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        String PASS = sharedPreferences.getString("pass", "");
        if (PASS.equals("")){
            tvDeletePasswd.setVisibility(View.GONE);
            tvChangePasswd.setVisibility(View.GONE);
        } else {
            tvSetPasswd.setVisibility(View.GONE);
        }
        tvSetPasswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogSetPassWord(context);
            }
        });

        tvChangePasswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                SharedPreferences sharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                String PASS = sharedPreferences.getString("pass", "");
                Toast.makeText(context, PASS, Toast.LENGTH_SHORT).show();
                if (PASS.equals("")) {
                    showDialogSetPassWord(context);
                } else {
                    showDialogChangePassWord(context);
                }
            }
        });
        tvDeletePasswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                SharedPreferences sharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                String PASS = sharedPreferences.getString("pass", "");
                if (PASS.equals("")) {
                    showDialogSetPassWord(context);
                } else {
                    showDialogDeletePassword(context);
                }
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
                Intent browse = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com.vn/?hl=vi"));

                context.startActivity(browse);
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
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Hey check out my app at \n https://play.google.com/store/apps/details?id=com.videoslidshow.maker.music.videomaker.photoslideshow");
                context.startActivity(Intent.createChooser(shareIntent, "Choose one"));
            }
        });
        tvRateMe5Star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "RateMe5Star", Toast.LENGTH_SHORT).show();
                context.startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=com.videoslidshow.maker.music.videomaker.photoslideshow")));
            }
        });
        tvFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "FeedBack", Toast.LENGTH_SHORT).show();
                Intent Email = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "tunv@vietmobi.net", null));
                Email.setType("text/email");
                Email.putExtra(Intent.EXTRA_EMAIL, new String[]{"tunv@vietmobi.net"});
                Email.putExtra(Intent.EXTRA_SUBJECT, "Feedback app Note");
                Email.putExtra(Intent.EXTRA_TEXT, "Dear Nguyen Tu Van" + "");
                context.startActivity(Intent.createChooser(Email, "Send Feedback:"));
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    @Override
    public void onClick(View v) {

    }

    private void createDialog() {

    }

    public void showDialogSetPassWord(Context context) {
        final android.app.Dialog dialog = new android.app.Dialog(context/*, android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen*/);
        Window window = dialog.getWindow();
        dialog.setContentView(R.layout.dialog_set_passwd);
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
                SharedPreferences sharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                if (pass.length() >= 4 && pass.length() < 20) {
                    Toast.makeText(context, "Password you set is: " + pass, Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("pass", pass);
                    editor.apply();
                    dialog.dismiss();
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                } else {
                    textInputLayout.setError("Less than 20 and greater than 4 characters");
                    textInputLayout.requestFocus();
                }
            }
        });
        dialog.show();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void showDialogDelete(Context context) {
        new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Confirm delete note")
                .setContentText("Are you sure?")
                .setConfirmText("Yes")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        Toast.makeText(context, "Delete note successfully", Toast.LENGTH_SHORT).show();
                        sweetAlertDialog.dismissWithAnimation();
                    }
                })
                .setCancelButton("No", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                    }
                })
                .show();
    }

    public void showDialogCreateFolder(Context context) {
        final android.app.Dialog dialog = new android.app.Dialog(context/*, android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen*/);
        Window window = dialog.getWindow();
        dialog.setContentView(R.layout.dialog_create_folder);
        TextInputLayout textInputLayout = dialog.findViewById(R.id.tilNameFolder);
        TextInputEditText edtNameFolder = dialog.findViewById(R.id.edtNameFolder);
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
                String name = String.valueOf(edtNameFolder.getText());
                String timeLastEditNote = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

                if (name.length() <= 20 && name.length() > 0) {

                    folder = new Folder(name, timeLastEditNote);
                    FolderNoteDatabase.getInstance(view.getContext()).folderNoteDAO().insertFolder(folder);

                    Toast.makeText(context, "Create folder successfully!", Toast.LENGTH_SHORT).show();
                    InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.toggleSoftInput(InputMethodManager.RESULT_HIDDEN, 0);
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                    dialog.dismiss();
                } else {
                    textInputLayout.setError("Less than 20 and greater than 0 characters");
                    textInputLayout.requestFocus();
                }
            }
        });
        dialog.show();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void showDialogEditFolder(Context context) {
        final android.app.Dialog dialog = new android.app.Dialog(context/*, android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen*/);
        Window window = dialog.getWindow();
        dialog.setContentView(R.layout.dialog_edit_folder);

        TextInputLayout textInputLayout = dialog.findViewById(R.id.tilNameFolder);
        TextInputEditText edtNameFolder = dialog.findViewById(R.id.edtNameFolder);
        TextView btnCancel = dialog.findViewById(R.id.btnCancel);
        TextView btnAccept = dialog.findViewById(R.id.btnAccept);
        edtNameFolder.requestFocus();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = String.valueOf(edtNameFolder.getText());
                String timeLastEditNote = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
                if (name.length() <= 20 && name.length() > 0) {

                    folder = new Folder(name, timeLastEditNote);
                    FolderNoteDatabase.getInstance(view.getContext()).folderNoteDAO().updateFolder(folder);

                    Toast.makeText(context, "Create folder successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                    dialog.dismiss();
                } else {
                    textInputLayout.setError("Less than 20 and greater than 0 characters");
                    textInputLayout.requestFocus();
                }
            }
        });
        dialog.show();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void showDialogMoveToFolder(Context context) {
        final android.app.Dialog dialog = new android.app.Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_sheet_move_to_folder);

        RecyclerView rcvListFolder = dialog.findViewById(R.id.rcvListFolder);
        MaterialButton btnAddNewFolder = dialog.findViewById(R.id.btnAddNewFolder);
        LinearLayout lineListFolder = dialog.findViewById(R.id.lineListFolder);
        btnAddNewFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogCreateFolder(context);
            }
        });

        listFolderAdapter = new ListFolderAdapter(listFolder, context);
        listFolder = new ArrayList<>();
        listFolder = FolderNoteDatabase.getInstance(context).folderNoteDAO().getListFolder();
        listFolderAdapter.setData(listFolder);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        rcvListFolder.setLayoutManager(linearLayoutManager);
        rcvListFolder.setAdapter(listFolderAdapter);

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }


}
