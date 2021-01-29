package com.example.customchess.engine.figures;

import com.example.customchess.engine.OneDeviceGame;
import com.example.customchess.engine.exceptions.ChessException;
import com.example.customchess.engine.movements.Movable;
import com.example.customchess.engine.movements.Position;


public interface Piece {
    void tryToMove(Movable movement, OneDeviceGame board) throws ChessException;
    boolean isTrajectoryValid(Movable movement) throws ChessException;
    boolean isFightTrajectoryValid(Movable movement) throws ChessException;
    void move(Position newPosition);
}
