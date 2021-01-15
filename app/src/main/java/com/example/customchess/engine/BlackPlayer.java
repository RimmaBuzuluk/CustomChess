package com.example.customchess.engine;

import android.util.Log;

import com.example.customchess.engine.exceptions.InvalidOrderMoveException;
import com.example.customchess.engine.figures.ChessPiece;


public class BlackPlayer implements Player {

    private final OneDeviceGame game;

    public BlackPlayer(OneDeviceGame game) {
        this.game  = game;
    }

    @Override
    public boolean isCorrectPlayerMove(ChessPiece selected) throws InvalidOrderMoveException {
        if (selected.isBlack()) {
            return true;
        }
        throw new InvalidOrderMoveException("Now the move of black player");
    }

    @Override
    public void changePlayer() {
        Log.d("move", "white's move");
        game.setCurrentPlayer(new WhitePlayer(game));
    }
}
