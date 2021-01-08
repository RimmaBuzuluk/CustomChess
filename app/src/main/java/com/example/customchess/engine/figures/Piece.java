package com.example.customchess.engine.figures;

import com.example.customchess.engine.exceptions.ChessException;
import com.example.customchess.engine.misc.Color;
import com.example.customchess.engine.movements.Movable;

public abstract class Piece {

    public final Color color;

    public Piece(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Piece";
    }

    public abstract boolean isTrajectoryValid(Movable movement) throws ChessException;

    public boolean isWhite() {
        return color.equals(Color.White);
    }

    public boolean isBlack() {
        return color.equals(Color.Black);
    }
}
