package com.example.customchess.ui;

import android.os.Handler;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customchess.engine.Game;
import com.example.customchess.engine.NetworkGame;
import com.example.customchess.engine.exceptions.BeatFigureException;
import com.example.customchess.engine.exceptions.CastlingException;
import com.example.customchess.engine.exceptions.CheckMateException;
import com.example.customchess.engine.exceptions.ChessException;
import com.example.customchess.engine.exceptions.DrawException;
import com.example.customchess.engine.exceptions.FigureNotChosenException;
import com.example.customchess.engine.exceptions.MoveOnEmptyCageException;
import com.example.customchess.engine.exceptions.OneTeamPiecesSelectedException;
import com.example.customchess.engine.exceptions.PawnEnPassantException;
import com.example.customchess.engine.exceptions.PromotionException;
import com.example.customchess.engine.movements.Movable;
import com.example.customchess.engine.movements.Movement;
import com.example.customchess.ui.board.BoardPlayerView;
import com.example.customchess.ui.boardmove.MessagePosterOnUI;
import com.example.customchess.ui.boardmove.UIMove;
import com.example.customchess.ui.figures.Figure;
import com.example.customchess.ui.figures.TempPosition;
import com.example.customchess.ui.fragments.PromotionDialogFragment;

public class NetMovementHandler extends MovementHandler
        implements PromotionDialogFragment.PromotionDialogListener {
    private final NetworkGame  game;
    private final Handler handler;
    private TempPosition start;
    private OnUIThreadPoster poster;

    public NetMovementHandler(Fragment context, Game game, RecyclerView recyclerView, BoardPlayerView playerView) {
        super(context, ((NetworkGame) game), recyclerView, playerView);
        this.game = (NetworkGame) game;
        handler = new Handler();
    }

    @Override
    public void handle(final TempPosition destination) {
        if (start == null) {
            start = destination;
            return;
        }
        boolean isLegalMove = true;

        final Movable move = new Movement(start.position, destination.position);

        CageAdapter.ViewHolder startHolder = findCage(start.index);
        CageAdapter.ViewHolder destinationHolder = findCage(destination.index);
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
                start = destination;
                return;
            } catch (PawnEnPassantException ppe) {
                poster = new OnUIThreadPoster(enPassant, start, destination, startHolder, destinationHolder);
                handler.post(new MessagePosterOnUI(context.getContext(), ppe.getMessage()));
            } catch (PromotionException pe) {
                poster = new OnUIThreadPoster(promotion, start, destination, startHolder, destinationHolder);
            } catch (ChessException e) {
                e.printStackTrace();
                isLegalMove = false;
                handler.post(new MessagePosterOnUI(context.getContext(), e.getMessage()));
            }
        }

        try {
            game.checkForCheckMate();
            game.checkForPat();
        } catch (CheckMateException | DrawException e) {
            handler.post(new MessagePosterOnUI(context.getContext(), e.getMessage()));
        }

        start = null;

        if (isLegalMove) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        game.approveMoveOnServer(move);
                        if (poster != null) {
                            handler.post(poster);
                        }
                    } catch (ChessException e) {
                        e.printStackTrace();
                    }
                    try {
                        Movable movement = game.getEnemyMove();
                        movement.getDestination();
                        if (poster != null) {
                            handler.post(poster);
                        }
                    } catch (ChessException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    private void enemyMove(Movable move,
                           TempPosition destination,
                           CageAdapter.ViewHolder startHolder,
                           CageAdapter.ViewHolder destinationHolder) {
        try {
            game.tryToMakeMovement(move);

        } catch (MoveOnEmptyCageException
                | BeatFigureException mee) {
            poster = new OnUIThreadPoster(simpleMove, start, destination, startHolder, destinationHolder);
        } catch (CastlingException ce) {
            poster = new OnUIThreadPoster(castling, start, destination, startHolder, destinationHolder);
            handler.post(new MessagePosterOnUI(context.getContext(), ce.getMessage()));
        } catch (OneTeamPiecesSelectedException | FigureNotChosenException otp) {
            start = destination;
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

    @Override
    public void applyChoice(String piece, TempPosition start,
                            CageAdapter.ViewHolder startHolder,
                            CageAdapter.ViewHolder destinationHolder) {
        synchronized (game) {
            Figure promoted;
            switch (piece) {
                case "Queen":
                    promoted = new Figure.Queen(start.imageResource);
                    break;
                case "Knight":
                    promoted = new Figure.Knight(start.imageResource);
                    break;
                case "Rook":
                    promoted = new Figure.Rook(start.imageResource);
                    break;
                default:
                    promoted = new Figure.Bishop(start.imageResource);
                    break;
            }
            startHolder.hide();
            destinationHolder.draw(promoted.getImageId());
            game.promotion(piece);
        }
        Toast.makeText(context.getContext(), piece, Toast.LENGTH_SHORT).show();
    }

    private final UIMove promotion = new UIMove() {
        @Override
        public void moveOnBoard(TempPosition start, TempPosition destination,
                                CageAdapter.ViewHolder startHolder, CageAdapter.ViewHolder destinationHolder) {
            assert context.getFragmentManager() != null;
            PromotionDialogFragment dialog = new PromotionDialogFragment(
                    NetMovementHandler.this, startHolder, destinationHolder, start);
            dialog.show(context.getFragmentManager(), "promotion dialog");
        }
    };
}
