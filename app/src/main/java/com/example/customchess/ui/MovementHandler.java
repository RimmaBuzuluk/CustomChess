package com.example.customchess.ui;

import android.os.Handler;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customchess.engine.Game;
import com.example.customchess.engine.exceptions.*;
import com.example.customchess.engine.movements.Movable;
import com.example.customchess.engine.movements.Movement;
import com.example.customchess.engine.movements.Position;
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

                if (startHolder != null && destinationHolder != null) {
                    try {
                        game.tryToMakeMovement(move);

                    } catch (MoveOnEmptyCageException
                            | BeatFigureException mee) {
                        handler.post(new OnUIThreadPoster(simpleMove, destination, startHolder, destinationHolder));
                    } catch (CastlingException ce) {
                        handler.post(new OnUIThreadPoster(castling, destination, startHolder, destinationHolder));
                    } catch (OneTeamPiecesSelectedException | FigureNotChosenException otp) {
                        start = destination;
                        return;
                    } catch (PawnEnPassantException ppe) {
                        handler.post(new OnUIThreadPoster(enPassant, destination, startHolder, destinationHolder));
                        handler.post(new MessagePosterOnUI(context.getContext(), ppe.getMessage()));
                    } catch (PromotionException pe) {
                        handler.post(new OnUIThreadPoster(promotion, destination, startHolder, destinationHolder));
                    } catch (ChessException e) {
                        e.printStackTrace();
                        handler.post(new MessagePosterOnUI(context.getContext(), e.getMessage()));
                    }
                }

                // todo
                //  is it correct ?
                //  check when network gaming is gonna testing
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
        public void moveOnBoard(TempPosition destination, CageAdapter.ViewHolder startHolder, CageAdapter.ViewHolder destinationHolder) {
            startHolder.hide();
            destinationHolder.draw(new Figure.Queen(destination.imageResource).getImageId());
            game.promotion("Queen");
        }
    };

    private UIMove simpleMove = new UIMove() {
        @Override
        public void moveOnBoard(TempPosition destination, CageAdapter.ViewHolder startHolder, CageAdapter.ViewHolder destinationHolder) {
            startHolder.hide();
            destinationHolder.draw(start.imageResource);
        }
    };

    private UIMove enPassant = new UIMove() {
        @Override
        public void moveOnBoard(TempPosition destination, CageAdapter.ViewHolder startHolder, CageAdapter.ViewHolder destinationHolder) {
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
        public void moveOnBoard(TempPosition destination,
                                CageAdapter.ViewHolder startHolder, CageAdapter.ViewHolder destinationHolder) {
            CageAdapter.ViewHolder newRook;
            CageAdapter.ViewHolder oldRook;
            if (destination.index < 24) {
                newRook = findCage(start.index - 8);
                oldRook = findCage(destination.index - 8);
            } else {
                newRook = findCage(start.index + 8);
                oldRook = findCage(destination.index + 16);
            }
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
        private CageAdapter.ViewHolder startHolder;
        private CageAdapter.ViewHolder destinationHolder;

        public OnUIThreadPoster(UIMove strategy, TempPosition destination,
                                CageAdapter.ViewHolder startHolder, CageAdapter.ViewHolder destinationHolder) {
            this.strategy = strategy;
            this.destination = destination;
            this.startHolder = startHolder;
            this.destinationHolder = destinationHolder;
        }

        @Override
        public void run() {
            strategy.moveOnBoard(destination, startHolder, destinationHolder);
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
