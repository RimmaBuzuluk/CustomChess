package com.example.customchess.engine.movements;


public class BoardPosition implements Position {

    private Verticals vertical;
    private String    horizontal;

    public BoardPosition(Verticals vertical, String horizontal) {
        this.vertical = vertical;
        this.horizontal = horizontal;
    }

    public BoardPosition(Verticals vertical, Integer horizontal) {
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
