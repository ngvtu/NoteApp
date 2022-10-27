package vietmobi.net.noteapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import vietmobi.net.noteapp.R;
import vietmobi.net.noteapp.activity.MainActivity;

public class FindNoteFragment extends Fragment {
    ImageView btnBack;
    EditText edtFindNote;
    RecyclerView rcvResultFind;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_find_note, container, false);
        initViews(view );


        addEvents();

        return view;
    }

    private void initViews(View view) {
        btnBack = view.findViewById(R.id.btnBack);
        edtFindNote = view.findViewById(R.id.edtFindNote);
        rcvResultFind = view.findViewById(R.id.rcvResultFind);
    }

    private void addEvents() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

}