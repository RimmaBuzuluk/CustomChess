package com.example.customchess.engine;

import android.util.Log;

import com.example.customchess.engine.exceptions.InvalidOrderMoveException;
import com.example.customchess.engine.figures.ChessPiece;


public class WhitePlayer implements Player {

    private final OneDeviceGame  game;

    public WhitePlayer(OneDeviceGame game) {
        this.game  = game;
    }

    @Override
    public boolean isCorrectPlayerMove(ChessPiece selected)  throws InvalidOrderMoveException {
        if (selected.isWhite()) {
            return true;
        }
        throw new InvalidOrderMoveException("Now the move of white player");
    }

    @Override
    public void changePlayer() {
        Log.d("move", "black's move");
        game.setCurrentPlayer(new BlackPlayer(game));
    }
}
