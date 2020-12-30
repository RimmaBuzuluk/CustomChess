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
