package vietmobi.net.noteapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import vietmobi.net.noteapp.R;
import vietmobi.net.noteapp.model.Note;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private ArrayList<Note> listNote;
    private Context context;

    public NoteAdapter(ArrayList listNote, Context context) {
        this.listNote = listNote;
        this.context = context;
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
        holder.btnMenu.setImageResource(R.drawable.ic_menu_vertical);
        Note note = listNote.get(position);
        holder.tvContent.setText(note.getContent());
        holder.tvTitle.setText(note.getTitle());
        holder.tvLastTimeEdit.setText(note.getTime());
    }

    @Override
    public int getItemCount() {
        return listNote.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView btnMenu;
        TextView tvTitle, tvContent, tvLastTimeEdit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.btnMenu = itemView.findViewById(R.id.btnMenu);
            this.tvTitle = itemView.findViewById(R.id.tvTitle);
            this.tvContent = itemView.findViewById(R.id.tvContent);
            this.tvLastTimeEdit = itemView.findViewById(R.id.tvLastTimeEdit);
            btnMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
                    popupMenu.getMenuInflater().inflate(R.menu.menu_setting_note, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            switch (menuItem.getItemId()) {
                                case R.id.add_to_favorite:
                                    Toast.makeText(view.getContext(), "Add to favorite", Toast.LENGTH_SHORT).show();
                                    return true;
                                case R.id.share:
                                    Toast.makeText(view.getContext(), "Share", Toast.LENGTH_SHORT).show();
                                    return true;
                                case R.id.move:
                                    Toast.makeText(view.getContext(), "Move", Toast.LENGTH_SHORT).show();
                                    return true;
                                case R.id.lock:
                                    Toast.makeText(view.getContext(), "lock", Toast.LENGTH_SHORT).show();
                                    return true;
                                case R.id.delete:
                                    Toast.makeText(view.getContext(), "Delete", Toast.LENGTH_SHORT).show();
                                    return true;
                            }
                            return false;
                        }
                    });
                    popupMenu.show();
                }
            });

        }
    }
}

