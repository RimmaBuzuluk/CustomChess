package com.example.customchess.ui.figures;


public class Figure {

    public final int color;


    public Figure(int color) {
        this.color = color;
    }

    public boolean isWhite(int color) {
        return color >= 2131099765 && color <= 2131099770;
    }

    public boolean isBlack(int color) {
        return color >= 2131099735 && color <= 2131099740;
    }
}
