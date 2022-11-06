package vietmobi.net.noteapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
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
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import vietmobi.net.noteapp.R;
import vietmobi.net.noteapp.activity.MainActivity;
import vietmobi.net.noteapp.activity.NoteOfFolderActivity;
import vietmobi.net.noteapp.database.FolderNoteDatabase;
import vietmobi.net.noteapp.database.NoteDatabase;
import vietmobi.net.noteapp.model.Folder;

public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.ViewHolder>{
    private List<Folder> listFolder;
    private Context context;
    private Folder folder;

    public FolderAdapter(List<Folder> listFolder, Context context) {
        this.listFolder = listFolder;
        this.context = context;
    }

    public void setData(List<Folder> folderList) {
        this.listFolder = folderList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.line_folder, parent, false);

        return new FolderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Folder folder = listFolder.get(position);
        if (folder == null){
            return;
        }
        holder.btnMenu.setImageResource(R.drawable.ic_menu_vertical);
        holder.tvName.setText(folder.getNameFolder());

        int id = folder.getId();
        int count = NoteDatabase.getInstance(context).noteDAO().getCountNoteOfFolder(id);
        holder.tvTotal.setText(""+count);
        holder.tvLastTimeEdit.setText(folder.getTimeLastEditFolder());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NoteOfFolderActivity.class);
                intent.putExtra("id_folderNote", folder.getId());
                context.startActivity(intent);
            }
        });

        holder.btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
                popupMenu.getMenuInflater().inflate(R.menu.menu_settings_folder, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.edit:
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
                                            folder.setNameFolder(name);
                                            folder.setTimeLastEditFolder(timeLastEditNote);
                                            FolderNoteDatabase.getInstance(view.getContext()).folderNoteDAO().updateFolder(folder);

                                            Toast.makeText(context, "Update folder successfully!", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(context, MainActivity.class);
                                            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                                            inputMethodManager.toggleSoftInput(InputMethodManager.RESULT_HIDDEN, 0);
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
                                return true;
                            case R.id.delete:
                                new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                                        .setTitleText("Confirm delete note")
                                        .setContentText("Are you sure?")
                                        .setConfirmText("Yes")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
//                                                NoteDatabase.getInstance(context).noteDAO().deleteNote(note);
                                                Toast.makeText(context, "Delete note successfully", Toast.LENGTH_SHORT).show();
                                                FolderNoteDatabase.getInstance(context).folderNoteDAO().deleteFolder(folder);
                                                sweetAlertDialog.dismissWithAnimation();
                                                notifyDataSetChanged();
                                                InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                                                inputMethodManager.toggleSoftInput(InputMethodManager.RESULT_HIDDEN, 0);
                                                Intent intent = new Intent(context, MainActivity.class);
                                                context.startActivity(intent);
                                            }
                                        })
                                        .setCancelButton("No", new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                sweetAlertDialog.dismissWithAnimation();
                                            }
                                        })
                                        .show();
                                return true;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return listFolder.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout line_folder;
        ImageView btnMenu;
        TextView tvName, tvTotal, tvLastTimeEdit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.btnMenu = itemView.findViewById(R.id.btnMenu);
            this.line_folder = itemView.findViewById(R.id.line_folder);
            this.tvName = itemView.findViewById(R.id.tvName);
            this.tvTotal = itemView.findViewById(R.id.tvTotal);
            this.tvLastTimeEdit = itemView.findViewById(R.id.tvLastTimeEdit);
        }
    }
}
