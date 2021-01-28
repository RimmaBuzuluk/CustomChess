package com.example.customchess.engine.movements;


import com.example.customchess.engine.misc.Verticals;


public class BoardPosition implements Position {

    private Verticals vertical;
    private String    horizontal;

    public BoardPosition(Verticals vertical, String horizontal) {
        this.vertical = vertical;
        this.horizontal = horizontal;
    }
    // delete it at the end
    public BoardPosition(Integer vertical, Integer horizontal) {
        Verticals[] verticals = Verticals.values();
        this.vertical = verticals[vertical];
        this.horizontal = horizontal.toString();
    }

    public BoardPosition(Verticals vertical, Integer horizontal) {
        this.vertical = vertical;
        this.horizontal = horizontal.toString();
    }

    public Verticals getVertical() {
        return vertical;
    }

    public static boolean isShortSchemeCastling(Position destination) {
        return destination.getVertical().equals(Verticals.G);
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof BoardPosition) {
            if (object == this) {
                return true;
            }
            return ((BoardPosition) object).horizontal.equals(this.horizontal)
                    && ((BoardPosition) object).vertical.equals(this.vertical);
        }

        return false;
    }

    @Override
    public String toString() {
        return vertical + horizontal;
    }

    public Integer getHorizontal() {
        return Integer.parseInt(horizontal);
    }
}
