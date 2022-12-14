package vietmobi.net.noteapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.ActionMode;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import java.util.Random;

import cn.pedant.SweetAlert.SweetAlertDialog;
import vietmobi.net.noteapp.Dialog;
import vietmobi.net.noteapp.R;
import vietmobi.net.noteapp.activity.MainActivity;
import vietmobi.net.noteapp.activity.UpdateNoteActivity;
import vietmobi.net.noteapp.database.FolderNoteDatabase;
import vietmobi.net.noteapp.database.NoteDatabase;
import vietmobi.net.noteapp.fragment.AllNoteFragment;
import vietmobi.net.noteapp.model.Folder;
import vietmobi.net.noteapp.model.Note;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    public static final int MY_REQUEST_CODE = 10;
    private List<Note> listNote;
    private List<Note> listNoteOld;
    private Context context;
    Folder folder;
    ListFolderAdapter listFolderAdapter;
    List<Folder> listFolder;
    private String filename = "internalStorage.txt";
    AllNoteFragment allNoteFragment;

    boolean isEnable = false;
    boolean isSelectAll = false;
//    boolean isSelect;
    ArrayList<Note> selectList = new ArrayList<>();

    Activity activity;

    //Th?? m???c do m??nh ?????t
    private String filepath = "ThuMucCuaToi";
    File myInternalFile;

    public NoteAdapter(Activity activity) {
        this.activity = activity;
    }

    public NoteAdapter(List listNote, Context context) {
        this.listNote = listNote;
        this.listNoteOld = listNote;
        this.context = context;
    }

    public void setData(List<Note> noteArrayList) {
        this.listNote = noteArrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.line_note, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note note = listNote.get(position);
        if (note == null) {
            return;
        }
        holder.btnMenu.setImageResource(R.drawable.ic_menu_vertical);
        if (note.isLocked() == true) {
            holder.tvContent.setVisibility(View.GONE);
            holder.tvTitle.setVisibility(View.GONE);
            holder.tvLastTimeEdit.setText(note.getTime());
            holder.viewLock.setImageResource(R.drawable.ic_lock_new);
        } else {
            holder.tvContent.setText(note.getContent());
            holder.tvTitle.setText(note.getTitle());
            holder.tvLastTimeEdit.setText(note.getTime());
            holder.viewLock.setVisibility(View.GONE);
        }

//        holder.itemView.setBackgroundColor(getRandomColor());

        if (note.isFavorite() == true) {
            holder.viewFavorite.setImageResource(R.drawable.ic_favorite);
        }

        holder.viewFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (note.isFavorite() == false) {
                    note.setFavorite(true);
                    holder.viewFavorite.setImageResource(R.drawable.ic_favorite);
                    Toast.makeText(view.getContext(), "Add to favorite", Toast.LENGTH_SHORT).show();
                    NoteDatabase.getInstance(context).noteDAO().updateNote(note);
                    notifyItemChanged(listNote.indexOf(note));
//                    listNote = NoteDatabase.getInstance(context).noteDAO().getListNote();
//                    setData(listNote);
                } else {
                    note.setFavorite(false);
                    holder.viewFavorite.setImageResource(R.drawable.ic_favorite_border);
                    Toast.makeText(view.getContext(), "Remove from favorite", Toast.LENGTH_SHORT).show();
                }
                NoteDatabase.getInstance(view.getContext()).noteDAO().updateNote(note);
                setData(listNote);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (note.isLocked() == true) {
                    final android.app.Dialog dialog = new android.app.Dialog(context/*, android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen*/);
                    Window window = dialog.getWindow();
                    dialog.setContentView(R.layout.dialog_enter_passwd);
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
                            String passWordApp = sharedPreferences.getString("pass", "");
                            if (pass.equals(passWordApp)) {
                                onClickGoToUpDate(note);
                                dialog.dismiss();
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
                } else {
                    onClickGoToUpDate(note);
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
//                Check condition
                if (!isEnable) {
//                    When action mode is not enable
//                    initialize action mode
                    ActionMode.Callback callback = new ActionMode.Callback() {
                        @Override
                        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
//                            Initialize menu inflate
                            MenuInflater menuInflater = actionMode.getMenuInflater();

                            menuInflater.inflate(R.menu.main_toolbar, menu);

                            return true;
                        }

                        @Override
                        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {

                            isEnable = true;

                            clickItem(holder, position);

                            return true;
                        }

                        @Override
                        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                            switch (menuItem.getItemId()) {
                                case R.id.delete:
                                    for (Note note : selectList) {
//                                        listNote.remove(note);
                                        NoteDatabase.getInstance(context).noteDAO().deleteNote(note);
                                        listNote = NoteDatabase.getInstance(context).noteDAO().getListNote();
                                        notifyDataSetChanged();
                                        setData(listNote);
                                    }
                                    actionMode.finish();
                                    break;
                                case R.id.move:
                                    for (Note note : selectList) {
//                                        listNote.remove(note);
                                        showDialogMoveToFolder();
                                        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putInt("id_note", note.getId());
                                        editor.apply();
//
//                                        listNote = NoteDatabase.getInstance(context).noteDAO().getListNote();
//                                        setData(listNote);
                                    }
                                    Toast.makeText(context, "Coming soon", Toast.LENGTH_SHORT).show();
                                    break;
                                case R.id.share:
                                    Toast.makeText(context, "Share coming soon", Toast.LENGTH_SHORT).show();
                                    break;
                                case R.id.select_all:
                                    if (selectList.size() == listNote.size()) {

                                        isSelectAll = false;

                                        selectList.clear();
                                    } else {
                                        isSelectAll = true;

                                        selectList.clear();

                                        selectList.addAll(listNote);
                                    }
                                    notifyDataSetChanged();
                                    break;
                            }
                            return true;
                        }

                        @Override
                        public void onDestroyActionMode(ActionMode actionMode) {
                            // when action mode is destroy
                            // set isEnable false
                            isEnable = false;
                            // set isSelectAll false
                            isSelectAll = false;
                            // clear select array list
                            selectList.clear();



                            holder.viewSelect.setVisibility(View.GONE);
                            // notify adapter
                            notifyDataSetChanged();

                        }
                    };

                    ((AppCompatActivity) view.getContext()).startActionMode(callback);
                } else {
                    clickItem(holder, position);
                }
                return true;
            }
        });

        holder.btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
                popupMenu.getMenuInflater().inflate(R.menu.menu_setting_note, popupMenu.getMenu());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    popupMenu.setForceShowIcon(true);
                }
                if (note.isLocked()) {
                    popupMenu.getMenu().findItem(R.id.lock).setVisible(false);
                } else {
                    popupMenu.getMenu().findItem(R.id.unLock).setVisible(false);
                }
                if (note.getOfFolder() == null) {
                    popupMenu.getMenu().findItem(R.id.deleteFromFolder).setVisible(false);
                }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.move:
                                showDialogMoveToFolder();
                                SharedPreferences sharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putInt("id_note", note.getId());
                                editor.apply();
                                listNote = NoteDatabase.getInstance(context).noteDAO().getListNote();
                                setData(listNote);
                                notifyItemChanged(listNote.indexOf(note));
                                return true;
                            case R.id.lock:
                                SharedPreferences sharedPreferences1 = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                                String passNote = sharedPreferences1.getString("pass", "");
                                if (passNote.equals("")) {
                                    Toast.makeText(context, "You have not set a password!!", Toast.LENGTH_SHORT).show();
                                    Dialog dialog = new Dialog();
                                    dialog.showDialogSetPassWord(context);
                                    NoteDatabase.getInstance(context).noteDAO().updateNote(note);
//                                    listNote = NoteDatabase.getInstance(context).noteDAO().getListNote();
//                                    setData(listNote);
                                } else {
                                    note.setLocked(true);
                                    NoteDatabase.getInstance(context).noteDAO().updateNote(note);
                                    Toast.makeText(context, "Lock", Toast.LENGTH_SHORT).show();
//                                    holder.viewLock.setImageResource(R.drawable.ic_lock_new);
//                                    listNote = NoteDatabase.getInstance(context).noteDAO().getListNote();
//                                    setData(listNote);
                                    notifyItemChanged(listNote.indexOf(note));
                                }
                                return true;
                            case R.id.unLock:
                                final android.app.Dialog dialog = new android.app.Dialog(context/*, android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen*/);
                                Window window = dialog.getWindow();
                                dialog.setContentView(R.layout.dialog_enter_passwd);
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
                                        String passWordApp = sharedPreferences.getString("pass", "");
                                        if (pass.equals(passWordApp)) {
                                            note.setLocked(false);
                                            NoteDatabase.getInstance(context).noteDAO().updateNote(note);
                                            Toast.makeText(context, "Unlock", Toast.LENGTH_SHORT).show();
                                            notifyItemChanged(listNote.indexOf(note));
                                            dialog.dismiss();
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
                                return true;
                            case R.id.delete:
                                deleteNote(note);
                                return true;
                            case R.id.share_pdf:
                                sharePDF(note);
                                return true;
                            case R.id.deleteFromFolder:
                                note.setOfFolder(null);
                                NoteDatabase.getInstance(context).noteDAO().updateNote(note);
                                listNote = NoteDatabase.getInstance(context).noteDAO().getListNote();
                                notifyItemChanged(listNote.indexOf(note));
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }


    private void clickItem(ViewHolder holder, int positions) {
        Note note = listNote.get(positions);
        if (!isEnable) {
            holder.viewSelect.setVisibility(View.GONE);

            selectList.remove(note);
        } else {
            holder.viewSelect.setVisibility(View.VISIBLE);

            selectList.add(note);
        }
    }

    private void sharePDF(Note note) {
        Toast.makeText(context, "SHARE PDF", Toast.LENGTH_SHORT).show();

        ContextWrapper contextWrapper = new ContextWrapper(context.getApplicationContext());
        File directory = contextWrapper.getDir(filepath, Context.MODE_PRIVATE);
        myInternalFile = new File(directory, filename);
        String content = note.getContent();
        String title = note.getTitle();
        try {
            FileOutputStream fos = new FileOutputStream(myInternalFile);
            fos.write(title.getBytes());
            fos.write("\n".getBytes());
            fos.write(content.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(contextWrapper, "create file ok", Toast.LENGTH_SHORT).show();

//        "/data/data/vietmobi.net.noteapp/app_ThuMucCuaToi/internalStorage.txt""file://" +
        File file = new File(Environment.getExternalStorageDirectory().toString(), "/" + filename);
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/*");
        sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("data/data/vietmobi.net.noteapp/app_ThuMucCuaToi/internalStorage.txt"));
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Sharing file...");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, "Sharing File...");
        context.startActivity(Intent.createChooser(sharingIntent, "Share File"));
    }

    private int getRandomColor() {
        List<Integer> colorBackground = new ArrayList<>();
        colorBackground.add(R.color.xanhngoc);
        colorBackground.add(R.color.xanhnuocbien);
        colorBackground.add(R.color.hongnhe);
        colorBackground.add(R.color.cam);
        colorBackground.add(R.color.hong);
        Random random = new Random();
        int number = random.nextInt(colorBackground.size());
        return colorBackground.get(number);
    }

    private void onClickGoToUpDate(Note note) {
        Intent intent = new Intent(context, UpdateNoteActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("note", note);
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        ((Activity) context).startActivityForResult(intent, MY_REQUEST_CODE);
//        ((Activity) context).finishAndRemoveTask();
    }

    public void showDialogMoveToFolder() {
        final android.app.Dialog dialog = new android.app.Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_sheet_move_to_folder);

        RecyclerView rcvListFolder = dialog.findViewById(R.id.rcvListFolder);
        MaterialButton btnAddNewFolder = dialog.findViewById(R.id.btnAddNewFolder);
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
                    ((Activity) context).finishAndRemoveTask();
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

    private void deleteNote(Note note) {
        if (note.isLocked()) {
            final android.app.Dialog dialog = new android.app.Dialog(context/*, android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen*/);
            Window window = dialog.getWindow();
            dialog.setContentView(R.layout.dialog_enter_passwd);
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
                    String passWordApp = sharedPreferences.getString("pass", "");
                    if (pass.equals(passWordApp)) {
                        NoteDatabase.getInstance(context).noteDAO().deleteNote(note);
                        Toast.makeText(context, "Delete note successfully", Toast.LENGTH_SHORT).show();
//                        notifyItemRemoved(listNote.indexOf(note));

                        listNote = NoteDatabase.getInstance(context).noteDAO().getListNote();
                        setData(listNote);
                        dialog.dismiss();
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
        } else {
            new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Confirm delete note")
                    .setContentText("Are you sure?")
                    .setConfirmText("Yes")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            NoteDatabase.getInstance(context).noteDAO().deleteNote(note);
                            Toast.makeText(context, "Delete note successfully", Toast.LENGTH_SHORT).show();
                            sweetAlertDialog.dismissWithAnimation();

//                        notifyItemRemoved(listNote.indexOf(note));

                            listNote = NoteDatabase.getInstance(context).noteDAO().getListNote();
                            setData(listNote);
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
    }

    @Override
    public int getItemCount() {
        return listNote.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout line_note;
        ImageView btnMenu, viewFavorite, viewLock, viewSelect;
        TextView tvTitle, tvContent, tvLastTimeEdit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.btnMenu = itemView.findViewById(R.id.btnMenu);
            this.tvTitle = itemView.findViewById(R.id.tvTitle);
            this.tvContent = itemView.findViewById(R.id.tvContent);
            this.line_note = itemView.findViewById(R.id.line_note);
            this.tvLastTimeEdit = itemView.findViewById(R.id.tvLastTimeEdit);
            this.viewFavorite = itemView.findViewById(R.id.viewFavorite);
            this.viewLock = itemView.findViewById(R.id.viewLock);
            this.viewSelect = itemView.findViewById(R.id.viewSelect);
        }
    }

}

