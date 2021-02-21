package com.example.customchess.ui.boardmove;

import com.example.customchess.ui.CageAdapter;
import com.example.customchess.ui.MovementHandler;

public interface UIMove {
    void moveOnBoard(MovementHandler.TempPosition destination,
                     CageAdapter.ViewHolder startHolder,
                     CageAdapter.ViewHolder destinationHolder);
}
