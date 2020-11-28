package com.example.customchess.engine.gameloop;

import com.example.customchess.engine.figures.Figure;
import com.example.customchess.engine.movements.Position;

public class Table {

    private Figure [][] gameTable;

    public Table() {
        gameTable = new Figure[8][8];

    }

    public boolean isCageEmpty(Position cage) {
        return gameTable[cage.getVertical().ordinal() - 1][cage.getHorizontal() - 1] == null;
    }
}
