package com.example.customchess.engine.figures;

import com.example.customchess.engine.exceptions.ChessException;
import com.example.customchess.engine.exceptions.InvalidMoveException;
import com.example.customchess.engine.misc.Color;
import com.example.customchess.engine.movements.Movable;
import com.example.customchess.engine.movements.Position;

public class Knight extends Piece {

    public Knight(Color color) {
        super(color);
    }

    @Override
    public String toString() {
        return "Knight";
    }

    @Override
    public boolean isTrajectoryValid(Movable movement) throws ChessException {
        Position start = movement.getStart();
        Position destination = movement.getDestination();

        int startVertical = start.getVertical().ordinal();
        int startHorizontal = start.getHorizontal() - 1;
        int destVertical = destination.getVertical().ordinal();
        int destHorizontal = destination.getHorizontal() - 1;

        if ((Math.abs(startHorizontal - destHorizontal) == 2 && Math.abs(startVertical - destVertical) == 1)
                || (Math.abs(startHorizontal - destHorizontal) == 1 && Math.abs(startVertical - destVertical) == 2)) {
            return true;
        }
        throw new InvalidMoveException("Invalid move\n" + start + " - " + destination);
    }
}
