package vietmobi.net.noteapp.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import vietmobi.net.noteapp.R;
import vietmobi.net.noteapp.adapter.FolderAdapter;
import vietmobi.net.noteapp.database.FolderNoteDatabase;
import vietmobi.net.noteapp.model.Folder;

public class FolderNoteFragment extends Fragment {
    RecyclerView rcvFolderNote;
    FolderAdapter noteAdapter;
    List<Folder> listFolder;
    FloatingActionButton btnAdd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_folder, container, false);

        rcvFolderNote = view.findViewById(R.id.rcvFolderNote);
        btnAdd =view.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Esssssss", Toast.LENGTH_SHORT).show();
                showDialogCreateFolder(getContext());
            }
        });
        addData();
        return view;
    }

    public void showDialogCreateFolder(Context context) {
        final android.app.Dialog dialog = new android.app.Dialog(getContext());
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
            Folder folder;
            @Override
            public void onClick(View view) {
                String name = String.valueOf(edtNameFolder.getText());
                String timeLastEditNote = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

                if (name.length() <= 20 && name.length() > 0) {

                    folder = new Folder(name, timeLastEditNote);
                    FolderNoteDatabase.getInstance(getContext()).folderNoteDAO().insertFolder(folder);

                    listFolder = FolderNoteDatabase.getInstance(getContext()).folderNoteDAO().getListFolder();
                    addData();

                    Toast.makeText(context, "Create folder successfully!", Toast.LENGTH_SHORT).show();
                    InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.toggleSoftInput(InputMethodManager.RESULT_HIDDEN, 0);
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


    @Override
    public void onResume() {
        super.onResume();
        addData();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    public void addData() {
        noteAdapter = new FolderAdapter(listFolder, getContext());
        listFolder = new ArrayList<>();

        listFolder = FolderNoteDatabase.getInstance(getContext()).folderNoteDAO().getListFolder();
        noteAdapter.setData(listFolder);

        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), 2);
        rcvFolderNote.setLayoutManager(linearLayoutManager);
        rcvFolderNote.setAdapter(noteAdapter);
    }
}