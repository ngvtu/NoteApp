package vietmobi.net.noteapp.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.List;

import vietmobi.net.noteapp.R;
import vietmobi.net.noteapp.model.Folder;

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
        holder.tvTotal.setText("1");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "" +folder.getId(), Toast.LENGTH_SHORT).show();

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
