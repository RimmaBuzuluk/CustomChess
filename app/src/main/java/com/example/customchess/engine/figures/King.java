package com.example.customchess.engine.figures;

import com.example.customchess.engine.Board;
import com.example.customchess.engine.EndGameChecker;
import com.example.customchess.engine.OneDeviceGame;
import com.example.customchess.engine.exceptions.BeatFigureException;
import com.example.customchess.engine.exceptions.CastlingException;
import com.example.customchess.engine.exceptions.CheckKingException;
import com.example.customchess.engine.exceptions.ChessException;
import com.example.customchess.engine.exceptions.InvalidMoveException;
import com.example.customchess.engine.exceptions.MoveOnEmptyCageException;
import com.example.customchess.engine.exceptions.OneTeamPiecesSelectedException;
import com.example.customchess.engine.misc.Color;
import com.example.customchess.engine.misc.Verticals;
import com.example.customchess.engine.movements.BoardPosition;
import com.example.customchess.engine.movements.Movable;
import com.example.customchess.engine.movements.Position;

public class King extends ChessPiece {

    public King(Color color, Position position) {
        super(position, 0.0, color);
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
    public void move(Position newPosition) {
        firstMove = false;
        currentPosition = newPosition;
    }

    @Override
    public void tryToMove(Movable movement, OneDeviceGame game) throws ChessException {
        Board board = game.getBoard();
        EndGameChecker gameAnalyser = game.getGameAnalyser();
        ChessPiece startFigure = (ChessPiece) board.findBy(movement.getStart());
        ChessPiece destinationFigure = (ChessPiece) board.findBy(movement.getDestination());
        Position start = movement.getStart();
        Position destination = movement.getDestination();

        if (!board.isCageEmpty(destinationFigure) && startFigure.hasSameColor(destinationFigure)) {
            throw new OneTeamPiecesSelectedException("One team pieces are selected");

        } else if (board.isCageEmpty(destinationFigure)) {
            ChessPiece cornerPiece = getCornerPieceOnFlank(board, destination);

            if (start.getHorizontal().equals(destination.getHorizontal())
                    && Math.abs(start.getVertical().ordinal() - destination.getVertical().ordinal()) == 2
                    && cornerPiece instanceof Rook
                    && (cornerPiece.firstMove & startFigure.firstMove)) {

                Position middle = getMiddleBetween(start, destination);
                if ( ! gameAnalyser.isPositionUnderAttackByEnemyTeam(color, start)
                        && ! gameAnalyser.isPositionUnderAttackByEnemyTeam(color, destination)
                        && ! gameAnalyser.isPositionUnderAttackByEnemyTeam(color, middle)) {
                    throw new CastlingException("castling");
                }
                throw new CheckKingException(color + " King under attack : " + start);

            } else if (isTrajectoryValid(movement)
                    & board.isDistanceFree(movement)) {
                throw new MoveOnEmptyCageException("default move");
            }

        } else {
            if (isFightTrajectoryValid(movement)
                    & board.isDistanceFree(movement)) {
                throw new BeatFigureException("beat figure move");
            }
        }
        throw new InvalidMoveException("Invalid move\n" + movement.getStart() + " - " + movement.getDestination());
    }

    private ChessPiece getCornerPieceOnFlank(Board board, Position onFlank) {
        Verticals vertical;
        if (onFlank.getVertical().ordinal() > 3) { // queen's flank
            vertical = Verticals.A;
        } else {
            vertical = Verticals.H;
        }

        return (ChessPiece) board.findBy(new BoardPosition(vertical, onFlank.getHorizontal()));
    }

    private Position getMiddleBetween(Position start, Position destination) {
        Verticals vertical = Verticals.F;
        int diffVertical = start.getVertical().ordinal() - destination.getVertical().ordinal();
        if (diffVertical == -2) {
            vertical = Verticals.D;
        }

        return new BoardPosition(vertical, start.getHorizontal());
    }
}
