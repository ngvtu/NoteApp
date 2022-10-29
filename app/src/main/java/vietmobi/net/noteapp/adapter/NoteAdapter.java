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

import java.util.List;

import vietmobi.net.noteapp.R;
import vietmobi.net.noteapp.RecyclerViewInterface;
import vietmobi.net.noteapp.model.Note;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;

    private List<Note> listNote;
    private Context context;


    public NoteAdapter( List listNote, Context context,RecyclerViewInterface recyclerViewInterface) {
        this.listNote = listNote;
        this.context = context;
        this.recyclerViewInterface = recyclerViewInterface;
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
        return new ViewHolder(view, recyclerViewInterface);
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
    }

    @Override
    public int getItemCount() {
        if (listNote != null) {
            return listNote.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView btnMenu;
        TextView tvTitle, tvContent, tvLastTimeEdit;

        public ViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recyclerViewInterface != null){
                        int position= getAdapterPosition();

                        if (position != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(position);
                        }
                    }
                }
            });

        }
    }
}

