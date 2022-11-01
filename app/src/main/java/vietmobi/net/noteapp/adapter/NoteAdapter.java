package vietmobi.net.noteapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import vietmobi.net.noteapp.Dialog;
import vietmobi.net.noteapp.R;
import vietmobi.net.noteapp.RecyclerViewInterface;
import vietmobi.net.noteapp.activity.UpdateNoteActivity;
import vietmobi.net.noteapp.database.NoteDatabase;
import vietmobi.net.noteapp.model.Note;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> implements RecyclerViewInterface {

    public static final int MY_REQUEST_CODE = 10;
    private List<Note> listNote;
    private Context context;
    private boolean isLocked;

    public NoteAdapter(List listNote, Context context) {
        this.listNote = listNote;
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
        holder.tvContent.setText(note.getContent());
        holder.tvTitle.setText(note.getTitle());
        holder.tvLastTimeEdit.setText(note.getTime());

        holder.line_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickGoToUpDate(note);
            }
        });

        holder.btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
                popupMenu.getMenuInflater().inflate(R.menu.menu_setting_note, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.add_to_favorite:
                                note.setFavorite(true);
                                NoteDatabase.getInstance(view.getContext()).noteDAO().updateNote(note);
                                Toast.makeText(view.getContext(), "Add to favorite", Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.share:
                                Toast.makeText(view.getContext(), "Share", Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.move:
                                Dialog dialog = new Dialog();
                                dialog.showDialogMoveToFolder(view.getContext());
                                Toast.makeText(view.getContext(), "Move", Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.lock:
                                if (holder.tvTitle.getVisibility() == View.INVISIBLE){
                                    holder.tvTitle.setVisibility(View.GONE);
                                    holder.tvContent.setVisibility(View.GONE);
                                } else{
                                    holder.tvTitle.setVisibility(View.VISIBLE);
                                    holder.tvContent.setVisibility(View.VISIBLE);
                                    holder.line_note.setBackgroundColor(R.drawable.bg_border_clean);
                                    Toast.makeText(view.getContext(), "lock", Toast.LENGTH_SHORT).show();
                                }
                                return true;
                            case R.id.delete:
                                deleteNote(note);
                                return true;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }


    private void onClickGoToUpDate(Note note) {
        Intent intent = new Intent(context, UpdateNoteActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("note", note);
        intent.putExtras(bundle);

        ((Activity) context).startActivityForResult(intent, MY_REQUEST_CODE);
        context.startActivity(intent);
    }


    private void deleteNote(Note note) {
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
                        notifyDataSetChanged();
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

    @Override
    public int getItemCount() {
        return listNote.size();
    }

    @Override
    public void configNote() {

    }

    @Override
    public void loadFragment(Fragment fragment) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout line_note;
        ImageView btnMenu;
        TextView tvTitle, tvContent, tvLastTimeEdit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.btnMenu = itemView.findViewById(R.id.btnMenu);
            this.tvTitle = itemView.findViewById(R.id.tvTitle);
            this.tvContent = itemView.findViewById(R.id.tvContent);
            this.line_note = itemView.findViewById(R.id.line_note);
            this.tvLastTimeEdit = itemView.findViewById(R.id.tvLastTimeEdit);
        }
    }

}

