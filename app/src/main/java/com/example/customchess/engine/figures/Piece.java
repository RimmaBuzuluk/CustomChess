package com.example.customchess.engine.figures;

import com.example.customchess.engine.Board;
import com.example.customchess.engine.OneDeviceGame;
import com.example.customchess.engine.exceptions.ChessException;
import com.example.customchess.engine.movements.Movable;


public interface Piece {

    void tryToMove(Movable movement, OneDeviceGame board) throws ChessException;
}
