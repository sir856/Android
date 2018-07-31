package com.example.admin.note;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

public class NoteFragment extends Fragment {
    Note note;

    static public NoteFragment newInstance(Note note){
        Bundle bundle = new Bundle();
        bundle.putString("note", new Gson().toJson(note));
        NoteFragment fragment = new NoteFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_note, container, false);

        Bundle args = getArguments();
        Note note = new Gson().fromJson(args.getString("note"), Note.class);

        TextView title = view.findViewById(R.id.note_title);
        title.setText(note.getTitle());

        TextView text = view.findViewById(R.id.note_text);
        text.setText(note.getText());

        return view;
    }
}