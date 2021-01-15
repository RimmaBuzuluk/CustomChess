package com.example.customchess.engine;

import com.example.customchess.engine.figures.Piece;
import com.example.customchess.engine.movements.Movable;

public class MovementHistory {

    public final Movable movement;
    public final Piece start;
    public final Piece destination;

    public MovementHistory(Movable movement, Piece start, Piece destination) {
        this.movement = movement;
        this.start = start;
        this.destination = destination;
    }
}
