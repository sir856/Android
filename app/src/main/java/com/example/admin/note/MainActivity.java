package com.example.admin.note;

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.ArraySet;
import android.util.SparseArray;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements AddNote {

    SharedPreferences pref;
    Set<String> notesSer;
    HashMap notes;
    LinearLayout notesLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        pref = getPreferences(MODE_PRIVATE);

        notesSer = pref.getStringSet("notes", new android.support.v4.util.ArraySet<String>());

        notes = new HashMap<Integer, Note>();

        Gson gson = new Gson();
        for (String note : notesSer){
            Note noteClass = gson.fromJson(note, Note.class);
            notes.put(noteClass.getId(), noteClass);
        }

        Note.idCount = pref.getInt("noteCount", 0);

        notesLayout = findViewById(R.id.notesLayout);

        showNotes();
    }

    private void showNotes(){
        for (Object it : notes.values()){
            Note note = (Note) it;
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
        pref.edit().putInt("noteCount", Note.idCount).commit();
        pref.edit().putStringSet("notes", notesSer).commit();
        super.onPause();
    }

    @Override
    public void addNote(Note note) {
        notes.put(note.getId(), note);
        notesSer.add(new Gson().toJson(note));
        showNote(note);
    }

    private void showNote(Note note){
        TextView tv = new TextView(this);
        tv.setId(note.getId());
        String text = note.getTitle();
        tv.setText(text);
        tv.setPadding(0,10,0,10);
        tv.setBackground(getResources().getDrawable(R.drawable.rectangle));

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 150);
        lp.setMargins(0, 5, 0, 5);
        notesLayout.addView(tv, lp);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment;
                TextView tv = (TextView) view;
                String name = Integer.toString(tv.getId());
                fragment = getSupportFragmentManager().findFragmentByTag(name);
                if (fragment == null){
                    fragment = NoteFragment.newInstance((Note) notes.get(tv.getId()));
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.noteFragmentContainer, fragment, name).commit();
                    transaction.addToBackStack(fragment.getClass().getSimpleName());
                }
            }
        });

        registerForContextMenu(tv);
    }

    public static final int GroupId = Menu.NONE;
    public static final int Order = Menu.NONE;

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (notes.containsKey(v.getId())){
            menu.add(GroupId, v.getId(), Order, "Delete");
        }
        else{
            super.onCreateContextMenu(menu, v, menuInfo);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (notes.containsKey(item.getItemId())){
            deleteNote(item.getItemId());
        }
        return super.onContextItemSelected(item);
    }

    private void deleteNote(int Id){
        notesSer.remove(new Gson().toJson(notes.get(Id)));
        notes.remove(Id);
        LinearLayout notesContainer = findViewById(R.id.notesLayout);
        notesContainer.removeView(findViewById(Id));
    }

    private void startFragment(){
        AddNoteFragment.show(getSupportFragmentManager());
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0){
            getSupportFragmentManager().popBackStack();
        }
        else{
            super.onBackPressed();
        }
    }
}
