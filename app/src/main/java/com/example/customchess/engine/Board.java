package com.example.customchess.engine;

import android.util.Log;

import com.example.customchess.engine.exceptions.CastlingException;
import com.example.customchess.engine.exceptions.CheckKingException;
import com.example.customchess.engine.exceptions.ChessException;
import com.example.customchess.engine.exceptions.FigureNotChosenException;
import com.example.customchess.engine.exceptions.InvalidMoveException;
import com.example.customchess.engine.exceptions.OneTeamPiecesSelectedException;
import com.example.customchess.engine.figures.*;
import com.example.customchess.engine.misc.Color;
import com.example.customchess.engine.misc.Verticals;
import com.example.customchess.engine.movements.BoardPosition;
import com.example.customchess.engine.movements.Movable;
import com.example.customchess.engine.movements.Movement;
import com.example.customchess.engine.movements.Position;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Set;


public class Board {

    private Piece[][] matrix;


    public Board() {
        matrix = new Piece[8][8];
        initTeam(Color.White);
        initTeam(Color.Black);
    }

    public boolean tryToMove(Movable movement) throws ChessException {
        Position start = movement.getStart();
        Position destination = movement.getDestination();

//        checkForCheck();

        if (start.equals(destination)) {
            throw new InvalidMoveException("You can't stand still");
        }

        Piece startFigure = findBy(start);
        Piece destFigure  = findBy(destination);

        if (startFigure == null) {
            throw new FigureNotChosenException("Figure was not chosen");
        }

        // 1 check for a mate  -
        // 2 check for a check -

        // movement to empty cage
        if (destFigure == null) {
            if (startFigure.isTrajectoryValid(movement)
                    & isDistanceFree(start, destination)) {
                swapFigures(start, destination);
                startFigure.move();
            } else {
                throw new InvalidMoveException("Invalid move\n" + start + " - " + destination);
            }
            return true;
        }

        if (startFigure.areSameColor(destFigure)) {
//        castling not implemented completely
            if (startFigure.areSameColor(destFigure)
                    & (startFigure instanceof King && ((King) startFigure).canBeCastling())
                    & (destFigure  instanceof Rook && ((Rook) destFigure).canBeCastling())
                    & isDistanceFree(start, destination)) {
                castling(start, destination);
                startFigure.move();
                destFigure.move();
                throw new CastlingException("castling");
            } else {
                throw new OneTeamPiecesSelectedException("One team pieces are selected");
            }
        } else {
            // beating
            if (startFigure.canBeatByTrajectory(movement)
                    & isDistanceFree(start, destination)) {
                beatFigure(start, destination);
                startFigure.move(); // for logic in 'Pawn'
            } else {
                throw new InvalidMoveException("Invalid move\n" + start + " - " + destination);
            }
        }
        return true;
    }

    public void checkForCheck() throws CheckKingException {
        checkForKingCheck(Color.White);
        checkForKingCheck(Color.Black);
    }

    private void checkForKingCheck(Color team) throws CheckKingException {
        Piece king = null;
        Position kingPos = null;
        Hashtable<Position, Piece> enemyTeam = new Hashtable<>();
        Piece currentPiece;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                currentPiece = matrix[i][j];
                if (isCageEmpty(currentPiece)) {
                    continue;
                } if (currentPiece instanceof King & currentPiece.color.equals(team)) {
                    king = currentPiece;
                    kingPos = new BoardPosition(j, i + 1);
                } else if (!currentPiece.color.equals(team)) {
                    enemyTeam.put(new BoardPosition(j, i + 1), currentPiece);
                }
            }
        }

        Set<Position> keys = enemyTeam.keySet();
        for (Position figure : keys) {
            currentPiece = enemyTeam.get(figure);

            try {
                assert king != null;
                assert currentPiece != null;

                if (currentPiece.canBeatByTrajectory(new Movement(kingPos, figure))
                        & isDistanceFree(kingPos, figure)) {
                    throw new CheckKingException(king.color + " King under attack");
                }
            } catch (CheckKingException cke) {
                throw cke;
            } catch (ChessException e) {
                // trajectory is incorrect
            }
        }
    }

    private void castling(Position start, Position destination) {
        int startVertical = start.getVertical().ordinal();
        int startHorizontal = start.getHorizontal() - 1;
        int destVertical = destination.getVertical().ordinal();
        int destHorizontal = destination.getHorizontal() - 1;

        Piece temp;
        int diff = Math.abs(destVertical - startVertical);
        if (diff == 3) {
            temp = matrix[destHorizontal][destVertical];
            matrix[destHorizontal][destVertical + 1] = matrix[startHorizontal][startVertical];
            matrix[startHorizontal][startVertical - 1] = temp;
        } else if (diff == 4) {
            temp = matrix[destHorizontal][destVertical];
            matrix[destHorizontal][destVertical - 2] = matrix[startHorizontal][startVertical];
            matrix[startHorizontal][startVertical + 1] = temp;
        }
        matrix[startHorizontal][startVertical] = null;
        matrix[destHorizontal][destVertical] = null;
//        LOG();
    }

    private void beatFigure(Position start, Position destination) {
        int startVertical = start.getVertical().ordinal();
        int startHorizontal = start.getHorizontal() - 1;
        int destVertical = destination.getVertical().ordinal();
        int destHorizontal = destination.getHorizontal() - 1;

        Piece figure = matrix[startHorizontal][startVertical];

        matrix[startHorizontal][startVertical] = null;
        matrix[destHorizontal][destVertical] = figure;
//        LOG();
    }

    private boolean isDistanceFree(Position start, Position destination) {
        LinkedList<Piece> distance = getPiecesOnDistance(start, destination);

        for (int i = 0; i < distance.size(); i++) {
            if (distance.get(i) == null) {
                continue;
            }
            return false;
        }

        return true;
    }

    private LinkedList<Piece> getPiecesOnDistance(Position start, Position destination) {
        int startVertical = start.getVertical().ordinal();
        int startHorizontal = start.getHorizontal() - 1;
        int destVertical = destination.getVertical().ordinal();
        int destHorizontal = destination.getHorizontal() - 1;
        LinkedList<Piece> distance = new LinkedList<>();

        if (startVertical > destVertical) {
            int temp = startVertical;
            startVertical = destVertical;
            destVertical = temp;
        }
        if (startHorizontal > destHorizontal) {
            int temp = startHorizontal;
            startHorizontal = destHorizontal;
            destHorizontal = temp;
        }

        if (startHorizontal - destHorizontal == 0) {
            for (int i = startVertical + 1; i < destVertical; i++) {
                distance.add(matrix[startHorizontal][i]);
            }
        } else if (startVertical - destVertical == 0) {
            for (int i = startHorizontal + 1; i < destHorizontal; i++) {
                distance.add(matrix[i][startVertical]);
            }
        } else if (Math.abs(startHorizontal - destHorizontal) == Math.abs(startVertical - destVertical)) {
            if ((start.getVertical().ordinal() < destination.getVertical().ordinal() && start.getHorizontal() < destination.getHorizontal())
                    || (start.getVertical().ordinal() > destination.getVertical().ordinal() && start.getHorizontal() > destination.getHorizontal())) {
                for (int i = 1; (startVertical + i) < destVertical; i++) {
                    Position fuck = new BoardPosition(startVertical + i, startHorizontal + i + 1);
                    distance.add(matrix[startHorizontal + i][startVertical + i]);
                }
            } else {
                for (int i = 1; (startVertical + i) < destVertical; i++) {
                    Position fuck = new BoardPosition(startVertical + i, destHorizontal - i + 1);
                    distance.add(matrix[destHorizontal - i][startVertical + i]);
                }
            }
        }

        return distance;
    }

    public Piece findBy(Position position) throws FigureNotChosenException {
        int vertical = position.getVertical().ordinal();
        int horizontal = position.getHorizontal() - 1;

        if (isCageEmpty(matrix[horizontal][vertical])) {
            return null;
        }

        return matrix[horizontal][vertical];
    }

    private void LOG() {
        for (Integer i = 0; i < 8; i++) {
            for (Integer j = 0; j < 8; j++) {
                if (matrix[i][j] != null) {
                    Verticals[] fuck = Verticals.values();
                    Position position = new BoardPosition(fuck[j], i + 1);
                    Log.d("move", matrix[i][j] + " on position " + position + "\n");
                }
            }
        }
        Log.d("move", "------------------------------------------------------------------------------+-+");
    }

    private void swapFigures(Position start, Position destination) {
        int startVertical = start.getVertical().ordinal();
        int startHorizontal = start.getHorizontal() - 1;
        int destVertical = destination.getVertical().ordinal();
        int destHorizontal = destination.getHorizontal() - 1;

        Piece figure = matrix[startHorizontal][startVertical];

        matrix[startHorizontal][startVertical] = matrix[destHorizontal][destVertical];
        matrix[destHorizontal][destVertical] = figure;
//        LOG();
    }

    private boolean isCageEmpty(Piece figure) {
        return figure == null;
    }

    private void initTeam(Color color) {
        int pawnRow = 1;
        int kingRow = 0;
        if (Color.Black.equals(color)) {
            pawnRow = 6;
            kingRow = 7;
        }
        matrix[kingRow][0] = new Rook(color);
        matrix[kingRow][7] = new Rook(color);
        matrix[kingRow][1] = new Knight(color);
        matrix[kingRow][6] = new Knight(color);
        matrix[kingRow][2] = new Bishop(color);
        matrix[kingRow][5] = new Bishop(color);
        matrix[kingRow][3] = new King(color);
        matrix[kingRow][4] = new Queen(color);
        for (int i = 0; i < 8; i++) {
            matrix[pawnRow][i] = new Pawn(color);
        }
    }
}
