package com.example.customchess.engine.figures;

import com.example.customchess.engine.exceptions.ChessException;
import com.example.customchess.engine.misc.Color;
import com.example.customchess.engine.movements.Movable;
import com.example.customchess.engine.movements.Position;

public abstract class ChessPiece implements Piece {

    public final Color color;
    protected boolean  firstMove;
    protected Position currentPosition;
    protected double   weight;


    public ChessPiece(Color color, double weight, Position position) {
        this.color = color;
        this.firstMove = true;
        this.weight = weight;
        this.currentPosition = position;
    }

    @Override
    public String toString() {
        return "Piece";
    }

    public abstract boolean isTrajectoryValid(Movable movement) throws ChessException;

    public abstract boolean isFightTrajectoryValid(Movable movement) throws ChessException;

    public abstract void move();

    public boolean hasSameColor(ChessPiece figure) {
        return this.color.equals(figure.color);
    }

    public boolean isWhite() {
        return color.equals(Color.White);
    }

    public boolean isBlack() {
        return color.equals(Color.Black);
    }
}