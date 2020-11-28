package com.example.customchess.engine.movements;

import com.example.customchess.engine.movements.Verticals;

public class Position {

    private Verticals vertical;
    private String    horizontal;

    public Position(Verticals vertical, String horizontal) {
        this.vertical = vertical;
        this.horizontal = horizontal;
    }

    public Position(Verticals vertical, Integer horizontal) {
        this.vertical = vertical;
        this.horizontal = horizontal.toString();
    }

    public Verticals getVertical() {
        return vertical;
    }

    @Override
    public String toString() {
        return vertical + horizontal;
    }

    public Integer getHorizontal() {
        return Integer.parseInt(horizontal);
    }
}
