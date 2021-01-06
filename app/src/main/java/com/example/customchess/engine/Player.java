package com.example.customchess.engine;

import com.example.customchess.engine.exceptions.InvalidOrderMoveException;
import com.example.customchess.engine.figures.Piece;

public interface Player {

    boolean isCorrectPlayerMove(Piece selected) throws InvalidOrderMoveException;
    void changePlayer();
}
