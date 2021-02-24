package com.example.customchess.networking;

import com.example.customchess.engine.figures.Piece;
import com.example.customchess.engine.movements.Movable;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class ChessNetPacket implements Serializable {
    private Date movementTime;
    private Movable movement;
    private String piece;


    public ChessNetPacket(Movable movement, Piece piece) {
        this.movementTime = Calendar.getInstance().getTime();
        this.movement = movement;
        this.piece = piece.toString();
    }

    @Override
    public String toString() {
        return "ChessNetPacket{" +
                "movementTime=" + movementTime +
                ", movement=" + movement +
                ", piece='" + piece + '\'' +
                '}';
    }
}
