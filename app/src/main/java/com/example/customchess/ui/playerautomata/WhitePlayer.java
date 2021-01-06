package com.example.customchess.ui.playerautomata;

import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.customchess.engine.OneDeviceGame;
import com.example.customchess.engine.exceptions.ChessException;
import com.example.customchess.engine.movements.Movable;
import com.example.customchess.engine.movements.Movement;
import com.example.customchess.engine.movements.Position;
import com.example.customchess.ui.CageAdapter;
import com.example.customchess.ui.ChessBoardFragment;


public class WhitePlayer implements PlayerMoveHandler {

    private ChessBoardFragment context;
    private Position start;
    private int      startIndex;
    private int      imageResource;
    private RecyclerView recyclerView;
    private OneDeviceGame game;

    public WhitePlayer(ChessBoardFragment context, RecyclerView recyclerView, OneDeviceGame oneDeviceGame) {
        this.context = context;
        this.game = oneDeviceGame;
        this.recyclerView = recyclerView;
    }

    @Override
    public void clickHandler(Position position, int index, int imageResourceId) {
        Movable move;

        if (start == null) {
            start = position;
            startIndex = index;
            imageResource = imageResourceId;
            return;
        }

        move = new Movement(start, position);

        CageAdapter.ViewHolder startHolder = (CageAdapter.ViewHolder)
                recyclerView.findViewHolderForAdapterPosition(startIndex);
        CageAdapter.ViewHolder destinationHolder = (CageAdapter.ViewHolder)
                recyclerView.findViewHolderForAdapterPosition(index);
        if (startHolder != null && destinationHolder != null) {
            // move this logic to chess engine, because of opportunity 'castling'
            if (startHolder.getFigure().isWhite(imageResourceId)) {
                start = position;
                startIndex = index;
                imageResource = imageResourceId;
                return;
            }
            try {
                game.canMakeMovement(move);
                startHolder.hide();
                destinationHolder.draw(imageResource);
                context.setMoveHandler(new BlackPlayer(context, recyclerView, game));
            } catch (ChessException e) {
                e.printStackTrace();
                Toast.makeText(context.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

        start = null;  // it looks disgusting
    }
}
