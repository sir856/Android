package com.example.admin.note;

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.ArraySet;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements AddNote {

    SharedPreferences pref;
    Set<String> notesSer;
    Set<Note> notes;
    LinearLayout notesLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        pref = getPreferences(MODE_PRIVATE);

        notesSer = pref.getStringSet("notes", new android.support.v4.util.ArraySet<String>());

        notes = new android.support.v4.util.ArraySet<Note>();
    }

    @Override
    protected void onStart() {
        super.onStart();

        Gson gson = new Gson();
        for (String note : notesSer){
            notes.add(gson.fromJson(note, Note.class));
        }

        Note.idCount = pref.getInt("noteCount", 0);

        notesLayout = findViewById(R.id.notesLayout);

        for (Note note :notes){
            showNote(note);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.add:
                startFragment();
                return true;
            default:
                return false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        pref.edit().putInt("noteCount", Note.idCount).commit();
        pref.edit().putStringSet("notes", notesSer).commit();
    }

    @Override
    public void addNote(Note note) {
        notes.add(note);
        notesSer.add(new Gson().toJson(note));
        showNote(note);
    }

    private void showNote(Note note){
        TextView tv = new TextView(this);
        tv.setId(note.id);
        tv.setText(note.getTitle());
        tv.setPadding(0,10,0,10);
        tv.setBackground(getResources().getDrawable(R.drawable.rectangle));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 150);
        lp.setMargins(0, 5, 0, 5);
        notesLayout.addView(tv, lp);
    }

    private void startFragment(){
        Fragment fragment;
        String name = AddNoteFragment.class.getSimpleName();
        fragment = getSupportFragmentManager().findFragmentByTag(name);
        AddNoteFragment.show(getSupportFragmentManager());
    }
}
