package com.example.customchess.ui.activ;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.customchess.R;
import com.example.customchess.ui.Team;
import com.example.customchess.ui.fragments.ChessBoardFragment;

public class NetworkGameActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private Fragment boardFragment;
    private Button   saveButton;
    private Button   loadButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        fragmentManager = getSupportFragmentManager();
        saveButton = findViewById(R.id.save);
        saveButton.setOnClickListener(saveListener);
        loadButton = findViewById(R.id.load);
        loadButton.setOnClickListener(loadListener);
        boardFragment = new ChessBoardFragment(Team.Black);

        fragmentManager.beginTransaction()
                .add(R.id.chess_board_frag, boardFragment)
                .commit();

    }

    private View.OnClickListener saveListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String movements = ((ChessBoardFragment) boardFragment).getHistory();
            SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("history", movements);
            editor.apply();
        }
    };

    private View.OnClickListener loadListener = new View.OnClickListener() {
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
