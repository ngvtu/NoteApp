package vietmobi.net.noteapp.adapter;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.List;

import vietmobi.net.noteapp.R;
import vietmobi.net.noteapp.activity.MainActivity;
import vietmobi.net.noteapp.database.NoteDatabase;
import vietmobi.net.noteapp.model.Folder;
import vietmobi.net.noteapp.model.Note;

public class ListFolderAdapter extends RecyclerView.Adapter<ListFolderAdapter.ViewHolder> {
    private List<Folder> listFolder;
    private Context context;

    public ListFolderAdapter(List<Folder> listFolder, Context context) {
        this.listFolder = listFolder;
        this.context = context;
    }

    public void setData(List<Folder> listFolder) {
        this.listFolder = listFolder;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.line_list_folder, parent, false);
        return new ListFolderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Folder folder = listFolder.get(position);
        if (folder == null){
            return;
        }
        holder.tvNameFolder.setText(folder.getNameFolder());
        int count = NoteDatabase.getInstance(context).noteDAO().getCountNoteOfFolder(folder.getId());
        holder.tvTotal.setText(""+count);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                int idNote = sharedPreferences.getInt("id_note",0);

                // Gan gia tri ofFolder
                int idFolder = folder.getId();
                Note note = NoteDatabase.getInstance(context).noteDAO().getNote(idNote);
                note.setOfFolder(""+idFolder);
                NoteDatabase.getInstance(context).noteDAO().updateNote(note);

                // Gui du lieu di
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("id_folder", idFolder);
                editor.apply();
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listFolder.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        MaterialButton btnAddNewFolder;
        RecyclerView rcvListFolder;
        LinearLayout lineListFolder;
        TextView tvNameFolder, tvTotal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.btnAddNewFolder = itemView.findViewById(R.id.btnAddNewFolder);
            this.rcvListFolder = itemView.findViewById(R.id.rcvListFolder);
            this.lineListFolder = itemView.findViewById(R.id.lineListFolder);
            this.tvNameFolder = itemView.findViewById(R.id.tvNameFolder);
            this.tvTotal = itemView.findViewById(R.id.tvTotal);
        }
    }
}
