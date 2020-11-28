package com.example.customchess.engine.figures;

import com.example.customchess.engine.Color;
import com.example.customchess.engine.movements.Movable;
import com.example.customchess.engine.exceptions.IllegalFigureMovementException;

public class Knight extends ChessFigure {

    public Knight(Color figureColor) {
        super(figureColor);
    }

    @Override
    public void moveFigure(Movable move) throws IllegalFigureMovementException {

    }
}
