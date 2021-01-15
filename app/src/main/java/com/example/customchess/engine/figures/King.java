package com.example.customchess.engine.figures;

import com.example.customchess.engine.Board;
import com.example.customchess.engine.OneDeviceGame;
import com.example.customchess.engine.exceptions.BeatFigureException;
import com.example.customchess.engine.exceptions.CastlingException;
import com.example.customchess.engine.exceptions.CheckKingException;
import com.example.customchess.engine.exceptions.ChessException;
import com.example.customchess.engine.exceptions.InvalidMoveException;
import com.example.customchess.engine.exceptions.MoveOnEmptyCageException;
import com.example.customchess.engine.exceptions.OneTeamPiecesSelectedException;
import com.example.customchess.engine.misc.Color;
import com.example.customchess.engine.movements.Movable;
import com.example.customchess.engine.movements.Position;

public class King extends ChessPiece {

    public King(Color color) {
        super(color, 0.0);
    }

    @Override
    public String toString() {
        return "King";
    }

    @Override
    public boolean isTrajectoryValid(Movable movement) throws ChessException {
        Position start = movement.getStart();
        Position destination = movement.getDestination();

        int startVertical = start.getVertical().ordinal();
        int startHorizontal = start.getHorizontal() - 1;
        int destVertical = destination.getVertical().ordinal();
        int destHorizontal = destination.getHorizontal() - 1;

        int horizontalDiff = Math.abs(startHorizontal - destHorizontal);
        int verticalDiff = Math.abs(startVertical - destVertical);

        if (verticalDiff < 2 && horizontalDiff < 2) {
            return true;
        }
        throw new InvalidMoveException("Invalid move\n" + start + " - " + destination);
    }

    @Override
    public boolean isFightTrajectoryValid(Movable movement) throws ChessException {
        return isTrajectoryValid(movement);
    }

    @Override
    public void move() {
        firstMove = false;
    }

    @Override
    public void tryToMove(Movable movement, OneDeviceGame board) throws ChessException {
        try {
            canMakeMovement(movement, board);

        } catch (MoveOnEmptyCageException
                | BeatFigureException
                | CastlingException cke) {
            firstMove = false;
            throw cke;
        }
    }

    private void canMakeMovement(Movable movement, OneDeviceGame game) throws ChessException {
        Board board = game.getBoard();
        ChessPiece startFigure = (ChessPiece) board.findBy(movement.getStart());
        ChessPiece destinationFigure = (ChessPiece) board.findBy(movement.getDestination());
        Position destination = movement.getDestination();
        Position start = movement.getStart();

        if (!board.isCageEmpty(destinationFigure) && startFigure.hasSameColor(destinationFigure)) {
            if (destinationFigure instanceof Rook
                    && (startFigure.firstMove & destinationFigure.firstMove)
                    && board.isDistanceFree(movement)) {
                if ( ! board.isPositionUnderAttack(color, start)) {
                    throw new CastlingException("castling");
                }
                throw new CheckKingException(color + " King under attack : " + start);
            }
            throw new OneTeamPiecesSelectedException("One team pieces are selected");

        } else if (board.isCageEmpty(destinationFigure)) {
            if (isTrajectoryValid(movement)
                    & board.isDistanceFree(movement)) {
                throw new MoveOnEmptyCageException("default move");
            }

        } else {
            if (isFightTrajectoryValid(movement)
                    & board.isDistanceFree(movement)
                    & ! board.isPositionUnderAttack(color, start)) {
                throw new BeatFigureException("beat figure move");
            }
        }
        throw new InvalidMoveException("Invalid move\n" + movement.getStart() + " - " + movement.getDestination());
    }
}
