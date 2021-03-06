package com.example.customchess.ui;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customchess.engine.Game;
import com.example.customchess.ui.board.BlackPlayerViewBoard;
import com.example.customchess.ui.board.BoardPlayerView;
import com.example.customchess.ui.boardmove.UIMove;
import com.example.customchess.ui.figures.TempPosition;

public abstract class MovementHandler {
    protected final Fragment context;
    protected final RecyclerView recyclerView;
    protected final BoardPlayerView playerView;

    public MovementHandler(Fragment context, Game game, RecyclerView recyclerView, BoardPlayerView playerView) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.playerView = playerView;
    }

    public CageAdapter.ViewHolder findCage(int index) {
        return (CageAdapter.ViewHolder) recyclerView.findViewHolderForAdapterPosition(index);
    }

    public abstract void handle(TempPosition position);

    protected final UIMove simpleMove = new UIMove() {
        @Override
        public void moveOnBoard(TempPosition start, TempPosition destination,
                                CageAdapter.ViewHolder startHolder, CageAdapter.ViewHolder destinationHolder) {
            startHolder.hide();
            destinationHolder.draw(start.imageResource);
        }
    };

    protected final UIMove enPassant = new UIMove() {
        @Override
        public void moveOnBoard(TempPosition start, TempPosition destination,
                                CageAdapter.ViewHolder startHolder, CageAdapter.ViewHolder destinationHolder) {
            CageAdapter.ViewHolder passedPawn = findCage(destination.index + 1) ;

            if (start.index % 2 == 0) {
                passedPawn = findCage(destination.index - 1);
            }
            assert passedPawn != null;
            passedPawn.hide();
            destinationHolder.draw(startHolder.getImageResource());
            startHolder.hide();
        }
    };

    protected final UIMove castling = new UIMove() {
        @Override
        public void moveOnBoard(TempPosition start, TempPosition destination,
                                CageAdapter.ViewHolder startHolder, CageAdapter.ViewHolder destinationHolder) {
            CageAdapter.ViewHolder newRook;
            CageAdapter.ViewHolder oldRook;
            int newRookPlaceIndex;
            int oldRookPlaceIndex;

            if (playerView instanceof BlackPlayerViewBoard) {
                if (destination.index < 24) {
                    newRookPlaceIndex = start.index - 8;
                    oldRookPlaceIndex = destination.index - 8;
                } else {
                    newRookPlaceIndex = start.index + 8;
                    oldRookPlaceIndex = destination.index + 16;
                }
            } else {
                if (destination.index < 24) {
                    newRookPlaceIndex = start.index - 8;
                    oldRookPlaceIndex = destination.index - 16;
                } else {
                    newRookPlaceIndex = start.index + 8;
                    oldRookPlaceIndex = destination.index + 8;
                }
            }
            newRook = findCage(newRookPlaceIndex);
            oldRook = findCage(oldRookPlaceIndex);
            newRook.draw(oldRook.getImageResource());
            destinationHolder.draw(startHolder.getImageResource());
            startHolder.hide();
            oldRook.hide();
        }
    };
}
