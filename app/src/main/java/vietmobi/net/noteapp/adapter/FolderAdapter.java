package vietmobi.net.noteapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vietmobi.net.noteapp.R;
import vietmobi.net.noteapp.model.Folder;

public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.ViewHolder>{
    private List<Folder> listFolder;
    private Context context;

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
        holder.tvTotal.setText("+0");
        holder.tvLastTimeEdit.setText(folder.getTimeLastEditFolder());

        holder.btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickShowAllNote();
                Toast.makeText(context,  "oke", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onClickShowAllNote() {
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