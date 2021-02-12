package com.example.customchess.ui;

import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customchess.engine.Game;
import com.example.customchess.engine.exceptions.*;
import com.example.customchess.engine.movements.Movable;
import com.example.customchess.engine.movements.Movement;
import com.example.customchess.engine.movements.Position;
import com.example.customchess.ui.figures.Figure;


public class MovementHandler {

    private Fragment context;
    private Game game;
    private RecyclerView recyclerView;
    private Position start;
    private int      startIndex;
    private int      imageResource;


    public MovementHandler(Fragment context, Game game, RecyclerView recyclerView) {
        this.context = context;
        this.game = game;
        this.recyclerView = recyclerView;
    }

    public void handle(final Position position, final int index, final int imageResourceId) {
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
            try {
                game.tryToMakeMovement(move);

            } catch (MoveOnEmptyCageException
                    | BeatFigureException mee) {
                startHolder.hide();
                destinationHolder.draw(imageResource);
            } catch (CastlingException ce) {
                CageAdapter.ViewHolder newRook;
                CageAdapter.ViewHolder oldRook;
                if (index < 24) {
                    newRook = (CageAdapter.ViewHolder)
                            recyclerView.findViewHolderForAdapterPosition(startIndex - 8);
                    oldRook = (CageAdapter.ViewHolder)
                            recyclerView.findViewHolderForAdapterPosition(index - 8);
                    newRook.draw(oldRook.getImageResource());
                    destinationHolder.draw(startHolder.getImageResource());
                    startHolder.hide(); // old king
                    oldRook.hide();
                } else if (index > 24) {
                    newRook = (CageAdapter.ViewHolder)
                            recyclerView.findViewHolderForAdapterPosition(startIndex + 8);
                    oldRook = (CageAdapter.ViewHolder)
                            recyclerView.findViewHolderForAdapterPosition(index + 16);
                    newRook.draw(oldRook.getImageResource());
                    destinationHolder.draw(startHolder.getImageResource());
                    startHolder.hide();
                    oldRook.hide();
                }
            } catch (OneTeamPiecesSelectedException | FigureNotChosenException otp) {
                start = position;
                startIndex = index;
                imageResource = imageResourceId;
                return;
            } catch (PawnEnPassantException ppe) {
                CageAdapter.ViewHolder passedPawn = (CageAdapter.ViewHolder)
                        recyclerView.findViewHolderForAdapterPosition(index + 1) ;

                if (startIndex % 2 == 0) {
                    passedPawn = (CageAdapter.ViewHolder)
                            recyclerView.findViewHolderForAdapterPosition(index - 1);
                }
                assert passedPawn != null;
                passedPawn.hide();
                destinationHolder.draw(startHolder.getImageResource());
                startHolder.hide();

                Toast.makeText(context.getContext(), ppe.getMessage(), Toast.LENGTH_SHORT).show();
            } catch (PromotionException pe) {
                // TODO     implement choice of 4 figures
                startHolder.hide();
                destinationHolder.draw(new Figure.Queen(imageResource).getImageId());
                Toast.makeText(context.getContext(), pe.getMessage(), Toast.LENGTH_SHORT).show();
                game.promotion("Queen");
            } catch (ChessException e) {
                e.printStackTrace();
                Toast.makeText(context.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

        try {
            game.checkForCheckMate();
            game.checkForPat();
        } catch (CheckMateException | DrawException e) {
            Toast.makeText(context.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        start = null;  // it looks disgusting
    }
}
