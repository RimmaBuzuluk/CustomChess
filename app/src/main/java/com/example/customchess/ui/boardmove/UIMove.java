package com.example.customchess.ui.boardmove;

import com.example.customchess.ui.CageAdapter;
import com.example.customchess.ui.OneDeviceMovementHandler;
import com.example.customchess.ui.figures.TempPosition;

public interface UIMove {
    void moveOnBoard(TempPosition start,
                     TempPosition destination,
                     CageAdapter.ViewHolder startHolder,
                     CageAdapter.ViewHolder destinationHolder);
}
