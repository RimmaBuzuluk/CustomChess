package com.example.customchess.ui.activ;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.customchess.R;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_history);

        String moves = getIntent().getStringExtra("history");
        TextView textView = findViewById(R.id.history_place);
        textView.setText(moves);

    }
}
