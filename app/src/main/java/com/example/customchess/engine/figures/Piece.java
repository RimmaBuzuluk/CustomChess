package com.example.customchess.engine.figures;

import com.example.customchess.engine.exceptions.ChessException;
import com.example.customchess.engine.misc.Color;
import com.example.customchess.engine.movements.Movable;

public abstract class Piece {

    private Color color;

    public Piece(Color color) {
        this.color = color;
    }

    public abstract boolean isTrajectoryCorrect(Movable movement) throws ChessException;

    public boolean isWhite() {
        return color.equals(Color.White);
    }

    public boolean isBlack() {
        return color.equals(Color.Black);
    }
}
