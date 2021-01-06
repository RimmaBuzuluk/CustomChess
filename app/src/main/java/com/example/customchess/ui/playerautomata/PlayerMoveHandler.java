package com.example.customchess.ui.playerautomata;

import com.example.customchess.engine.movements.Position;


public interface PlayerMoveHandler {

    void clickHandler(final Position position, final int index, final int imageResourceId);
}
