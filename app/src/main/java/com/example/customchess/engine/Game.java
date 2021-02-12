package com.example.customchess.engine;

import com.example.customchess.engine.exceptions.CheckMateException;
import com.example.customchess.engine.exceptions.ChessException;
import com.example.customchess.engine.exceptions.DrawException;
import com.example.customchess.engine.movements.Movable;

public interface Game {
    void checkForPat() throws DrawException;
    void checkForCheckMate() throws CheckMateException;
    void promotion(String choice);
    void tryToMakeMovement(Movable movement) throws ChessException;
}
