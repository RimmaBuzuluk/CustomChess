package com.example.customchess.engine.figures;

import com.example.customchess.engine.exceptions.ChessException;
import com.example.customchess.engine.misc.Color;
import com.example.customchess.engine.movements.Movable;

public class Bishop extends Piece {

    public Bishop(Color color) {
        super(color);
    }

    @Override
    public boolean isTrajectoryCorrect(Movable movement) throws ChessException {
        return true;
    }
}
