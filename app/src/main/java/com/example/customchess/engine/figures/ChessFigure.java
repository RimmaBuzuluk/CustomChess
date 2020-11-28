package com.example.customchess.engine.figures;

import com.example.customchess.engine.Color;

public abstract class ChessFigure implements Figure {

    private final Color color;

    public ChessFigure(Color color) {
        this.color = color;
    }
}
