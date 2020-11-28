package com.example.customchess.engine.figures;

import com.example.customchess.engine.Color;
import com.example.customchess.engine.movements.Movable;
import com.example.customchess.engine.exceptions.IllegalFigureMovementException;

public class King extends ChessFigure {

    private boolean castlingIsDone;

    public King(Color figureColor) {
        super(figureColor);

        castlingIsDone = false;
    }

    @Override
    public void moveFigure(Movable move) throws IllegalFigureMovementException {

    }
}
