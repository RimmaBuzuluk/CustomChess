package com.example.customchess.engine;

import com.example.customchess.engine.exceptions.InvalidOrderMoveException;
import com.example.customchess.engine.figures.ChessPiece;
import com.example.customchess.engine.misc.Color;

public interface Player {

    boolean isCorrectPlayerMove(ChessPiece selected) throws InvalidOrderMoveException;
    void changePlayer();
    Color getColor();
}
