package com.example.customchess;

import android.app.Application;
import android.content.Context;

import java.util.ArrayList;

public class ChessApplication extends Application {

    private static ArrayList<Integer> chessColors;

    @Override
    public void onCreate() {
        super.onCreate();

        int brown = getResources().getColor(R.color.brown);
        int beige = getResources().getColor(R.color.beige);

        chessColors = new ArrayList<>(64);
        for (int i = 0; i < 64; i++) {
            if (chessColors.get(i) % 2 == 0) {
                chessColors.add(brown);
            } else {
                chessColors.add(beige);
            }
        }
    }
}
