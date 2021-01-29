package com.example.customchess.engine.movements;

import com.example.customchess.engine.misc.Verticals;

import java.util.List;

public interface Position {
    Verticals getVertical();
    Integer getHorizontal();
    List<Position> getPositionsAround();
}
