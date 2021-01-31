package com.example.customchess.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.ListFragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.customchess.R;

public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    Fragment chessBoardFragment;
    Button   save;
    Button   load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        chessBoardFragment = fragmentManager.findFragmentById(R.id.chess_board_frag);
        save = findViewById(R.id.save);
        load = findViewById(R.id.load);

        save.setOnClickListener(saveButton);
        load.setOnClickListener(loadButton);

    }

    private View.OnClickListener saveButton = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String movements = ((ChessBoardFragment) chessBoardFragment).getHistory();
            SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("history", movements);
            editor.apply();
        }
    };

    private View.OnClickListener loadButton = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
            String movements = sharedPref.getString("history", "");
            Intent intent = new Intent(view.getContext(), HistoryActivity.class);
            intent.putExtra("history", movements);
            startActivity(intent);
        }
    };

}