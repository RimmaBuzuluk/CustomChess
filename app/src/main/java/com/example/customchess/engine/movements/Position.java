package com.example.customchess.engine.movements;

import com.example.customchess.engine.misc.Verticals;

public interface Position {

    Verticals getVertical();
    Integer getHorizontal();
}
