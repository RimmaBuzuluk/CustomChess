package com.example.customchess;

import com.example.customchess.engine.movements.Position;

public class Cage {

    private int      colorID;
    private Position position;

    public Cage(int colorID) {
        this.colorID = colorID;
    }

    public int getColorID() {
        return colorID;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
