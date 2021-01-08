package com.example.customchess.engine.figures;

import com.example.customchess.engine.exceptions.ChessException;
import com.example.customchess.engine.exceptions.InvalidMoveException;
import com.example.customchess.engine.misc.Color;
import com.example.customchess.engine.movements.Movable;
import com.example.customchess.engine.movements.Position;

public class Pawn extends Piece {

    private boolean firstMove;

    public Pawn(Color color) {
        super(color);
        firstMove = true;
    }

    @Override
    public String toString() {
        return "Pawn";
    }

    @Override
    public boolean isTrajectoryValid(Movable movement) throws ChessException {
        Position start = movement.getStart();
        Position destination = movement.getDestination();

        int startVertical = start.getVertical().ordinal();
        int startHorizontal = start.getHorizontal() - 1;
        int destVertical = destination.getVertical().ordinal();
        int destHorizontal = destination.getHorizontal() - 1;
        int horizontalDiff = startHorizontal - destHorizontal;

        if (this.color.equals(Color.White)) {
            horizontalDiff = -horizontalDiff;
        }

        if (startVertical == destVertical & horizontalDiff > 0) {
            if (firstMove && horizontalDiff < 3) {
                firstMove = false;
                return true;
            }
            if (horizontalDiff < 2) {
                return true;
            }
        }
        throw new InvalidMoveException("Invalid move\n" + start + " - " + destination);
    }
}
