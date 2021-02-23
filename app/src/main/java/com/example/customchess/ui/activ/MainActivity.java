package com.example.customchess.ui.activ;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.customchess.R;

public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    Fragment gameFragment;
    private Button oneDeviceGame;
    private Button networkGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        oneDeviceGame = findViewById(R.id.one_device_game_button);
        networkGame = findViewById(R.id.network_game_button);
        oneDeviceGame.setOnClickListener(oneDevice);
        networkGame.setOnClickListener(net);
    }

    private View.OnClickListener oneDevice = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, OneDeviceGameActivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener net = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, NetworkGameActivity.class);
            startActivity(intent);
        }
    };

}