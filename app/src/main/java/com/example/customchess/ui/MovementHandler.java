package com.example.customchess.ui;

import android.os.Handler;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customchess.engine.Game;
import com.example.customchess.engine.exceptions.*;
import com.example.customchess.engine.movements.Movable;
import com.example.customchess.engine.movements.Movement;
import com.example.customchess.engine.movements.Position;
import com.example.customchess.ui.board.BlackPlayerViewBoard;
import com.example.customchess.ui.board.BoardPlayerView;
import com.example.customchess.ui.boardmove.MessagePosterOnUI;
import com.example.customchess.ui.boardmove.UIMove;
import com.example.customchess.ui.figures.Figure;


public class MovementHandler {

    private Fragment context;
    private final Game game;
    private RecyclerView recyclerView;
    private TempPosition start;
    private BoardPlayerView playerView;


    public MovementHandler(Fragment context, Game game, RecyclerView recyclerView, BoardPlayerView playerView) {
        this.context = context;
        this.game = game;
        this.recyclerView = recyclerView;
        this.playerView = playerView;
    }

    public void handle(final TempPosition destination) {
        if (start == null) {
            start = destination;
            return;
        }

        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Movable move = new Movement(start.position, destination.position);
                CageAdapter.ViewHolder startHolder = findCage(start.index);
                CageAdapter.ViewHolder destinationHolder = findCage(destination.index);
                OnUIThreadPoster poster = null;

                if (startHolder != null && destinationHolder != null) {
                    try {
                        game.tryToMakeMovement(move);

                    } catch (MoveOnEmptyCageException
                            | BeatFigureException mee) {
                        poster = new OnUIThreadPoster(simpleMove, start, destination, startHolder, destinationHolder);
                    } catch (CastlingException ce) {
                        poster = new OnUIThreadPoster(castling, start, destination, startHolder, destinationHolder);
                        handler.post(new MessagePosterOnUI(context.getContext(), ce.getMessage()));
                    } catch (OneTeamPiecesSelectedException | FigureNotChosenException otp) {
                        synchronized (start) {
                            start = destination;
                        }
                        return;
                    } catch (PawnEnPassantException ppe) {
                        poster = new OnUIThreadPoster(enPassant, start, destination, startHolder, destinationHolder);
                        handler.post(new MessagePosterOnUI(context.getContext(), ppe.getMessage()));
                    } catch (PromotionException pe) {
                        poster = new OnUIThreadPoster(promotion, start, destination, startHolder, destinationHolder);
                    } catch (ChessException e) {
                        e.printStackTrace();
                        handler.post(new MessagePosterOnUI(context.getContext(), e.getMessage()));
                    }
                }
                if (poster != null) {
                    handler.post(poster);
                }

                synchronized (game) {
                    try {
                        game.checkForCheckMate();
                        game.checkForPat();
                    } catch (CheckMateException | DrawException e) {
                        handler.post(new MessagePosterOnUI(context.getContext(), e.getMessage()));
                    }

                    start = null;  // it looks disgusting
                }
            }
        }, "ChessEngineBackgroundThread").start();
    }

    private UIMove promotion = new UIMove() {
        @Override
        public void moveOnBoard(TempPosition start, TempPosition destination,
                                CageAdapter.ViewHolder startHolder, CageAdapter.ViewHolder destinationHolder) {
            synchronized (game) {
                startHolder.hide();
                Figure promoted = new Figure.Queen(start.imageResource);
                destinationHolder.draw(promoted.getImageId());
                game.promotion("Queen");
            }
        }
    };

    private UIMove simpleMove = new UIMove() {
        @Override
        public void moveOnBoard(TempPosition start, TempPosition destination,
                                CageAdapter.ViewHolder startHolder, CageAdapter.ViewHolder destinationHolder) {
            startHolder.hide();
            destinationHolder.draw(start.imageResource);
        }
    };

    private UIMove enPassant = new UIMove() {
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

    private UIMove castling = new UIMove() {
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

    private CageAdapter.ViewHolder findCage(int index) {
        return (CageAdapter.ViewHolder) recyclerView.findViewHolderForAdapterPosition(index);
    }

    private static class OnUIThreadPoster implements Runnable {
        private UIMove strategy;
        private TempPosition destination;
        private TempPosition start;
        private CageAdapter.ViewHolder startHolder;
        private CageAdapter.ViewHolder destinationHolder;

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

    public static class TempPosition {
        private Position position;
        private int index;
        private int imageResource;

        public TempPosition(Position position, int index, int imageResource) {
            this.position = position;
            this.index = index;
            this.imageResource = imageResource;
        }
    }
}
