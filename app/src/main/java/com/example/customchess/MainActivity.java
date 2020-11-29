package com.example.customchess;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.customchess.engine.movements.Position;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CageAdapter.OnItemSelected {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    @Override
    public void onItemClicked(Position position) {
        Toast.makeText(this, position.toString(), Toast.LENGTH_SHORT).show();
    }
}