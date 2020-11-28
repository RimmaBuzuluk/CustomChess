package com.example.customchess.engine.gameloop;

import com.example.customchess.engine.Color;
import com.example.customchess.engine.gameloop.Player;

public class ChessPlayer implements Player {

    private Color color;

    public ChessPlayer(Color color) {
        this.color = color;
    }
}
