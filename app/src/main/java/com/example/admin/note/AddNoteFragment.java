package com.example.admin.note;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

public class AddNoteFragment extends DialogFragment {
    private EditText title, text;
    private AddNote mAddNote;
    AlertDialog.Builder builder;

    public static final String TAG = AddNoteFragment.class.getSimpleName();

    public static void show(FragmentManager fragmentManager) {
        AddNoteFragment addNoteFragment = new AddNoteFragment();
        addNoteFragment.show(fragmentManager, TAG);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof AddNote){
            mAddNote = (AddNote) context;
        }
    }

    @Override
    public void onStart() {
        super.onStart();

//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Note note = new Note ("Local");
//                String titleStr = title.getText().toString();
//                String textStr = text.getText().toString();
//                if (!titleStr.isEmpty() && !textStr.isEmpty()){
//                   note.setText(textStr);
//                   note.setTitle(titleStr);
//                   mAddNote.addNote(note);
//                }
//                else {
//                    Toast.makeText(view.getContext(),"Fields should not be empty", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        builder = new AlertDialog.Builder(getActivity());

        View inflate = getActivity().getLayoutInflater().inflate(R.layout.add_note, null);

        builder.setTitle("Add note").setView(inflate).setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Note note = new Note ("Local");
                String titleStr = title.getText().toString();
                String textStr = text.getText().toString();
                if (!titleStr.isEmpty() && !textStr.isEmpty()){
                    note.setText(textStr);
                    note.setTitle(titleStr);
                    mAddNote.addNote(note);
                }
                else {
                    Toast.makeText(builder.getContext(),"Fields should not be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        title = inflate.findViewById(R.id.enterTitle);
        text = inflate.findViewById(R.id.enterText);

        Toast.makeText(getActivity(), "DialogCreated", Toast.LENGTH_SHORT).show();

        return builder.create();
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mAddNote = null;
    }
}
