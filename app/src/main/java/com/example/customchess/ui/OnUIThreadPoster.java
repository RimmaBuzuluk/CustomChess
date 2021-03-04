package com.example.customchess.ui;

import com.example.customchess.ui.boardmove.UIMove;
import com.example.customchess.ui.figures.TempPosition;

public class OnUIThreadPoster implements Runnable {
    private final UIMove strategy;
    private final TempPosition destination;
    private final TempPosition start;
    private final CageAdapter.ViewHolder startHolder;
    private final CageAdapter.ViewHolder destinationHolder;

    public OnUIThreadPoster(UIMove strategy, TempPosition start, TempPosition destination,
                            CageAdapter.ViewHolder startHolder, CageAdapter.ViewHolder destinationHolder) {
        this.strategy = strategy;
        this.start = start;
        this.destination = destination;
        this.startHolder = startHolder;
        this.destinationHolder = destinationHolder;
    }

    @Override
    public void run() {
        strategy.moveOnBoard(start, destination, startHolder, destinationHolder);
    }
}