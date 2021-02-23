package com.example.customchess.engine;

import com.example.customchess.engine.exceptions.CheckMateException;
import com.example.customchess.engine.exceptions.ChessException;
import com.example.customchess.engine.exceptions.DrawException;
import com.example.customchess.engine.movements.Movable;

public class NetworkGame implements Game {
    @Override
    public void checkForPat() throws DrawException {

    }

    @Override
    public void checkForCheckMate() throws CheckMateException {

    }

    @Override
    public void promotion(String choice) {

    }

    @Override
    public void tryToMakeMovement(Movable movement) throws ChessException {

    }
}
