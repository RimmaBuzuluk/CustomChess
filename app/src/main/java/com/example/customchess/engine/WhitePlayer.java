package com.example.customchess.engine;

import android.util.Log;

import com.example.customchess.engine.exceptions.InvalidOrderMoveException;
import com.example.customchess.engine.figures.ChessPiece;
import com.example.customchess.engine.misc.Color;


public class WhitePlayer implements Player {

    private final OneDeviceGame  game;
    private final Color          teamColor;


    public WhitePlayer(OneDeviceGame game) {
        this.game = game;
        teamColor = Color.White;
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
//        Log.d("move", "black's move");
        game.setCurrentPlayer(new BlackPlayer(game));
    }

    @Override
    public Color getColor() {
        return teamColor;
    }
}
