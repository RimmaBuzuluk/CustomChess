package com.example.customchess.ui.figures;

import com.example.customchess.engine.movements.Position;

public class TempPosition {
    public final Position position;
    public final int index;
    public final int imageResource;

    public TempPosition(Position position, int index, int imageResource) {
        this.position = position;
        this.index = index;
        this.imageResource = imageResource;
    }
}