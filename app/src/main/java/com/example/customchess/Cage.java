package com.example.customchess;

import com.example.customchess.engine.movements.Position;

import java.util.Objects;

public class Cage {

    private int      colorID;
    private Position position;

    public Cage(int colorID, Position position) {
        this.colorID = colorID;
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cage cage = (Cage) o;
        return colorID == cage.colorID &&
                position.equals(cage.position);
    }

    public int getColorID() {
        return colorID;
    }

    public Position getPosition() {
        return position;
    }

}
