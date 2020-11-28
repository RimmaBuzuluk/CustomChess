package com.example.customchess.engine.figures;

import com.example.customchess.engine.movements.Movable;
import com.example.customchess.engine.exceptions.IllegalFigureMovementException;

public interface Figure {

    void moveFigure(Movable move) throws IllegalFigureMovementException;
}
