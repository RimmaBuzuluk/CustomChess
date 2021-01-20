package com.example.customchess.engine;

import android.util.Log;

import com.example.customchess.engine.exceptions.CheckKingException;
import com.example.customchess.engine.exceptions.CheckMateException;
import com.example.customchess.engine.exceptions.ChessException;
import com.example.customchess.engine.exceptions.DrawException;
import com.example.customchess.engine.figures.*;
import com.example.customchess.engine.misc.Color;
import com.example.customchess.engine.misc.Verticals;
import com.example.customchess.engine.movements.BoardPosition;
import com.example.customchess.engine.movements.Movable;
import com.example.customchess.engine.movements.Movement;
import com.example.customchess.engine.movements.Position;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Set;


public class Board {

    private Piece[][] matrix;

    // for unit tests
    public Board(Piece[][] matrix) {
        this.matrix = matrix;
    }

    // for unit tests
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if ((matrix[i][j] == null & board.matrix[i][j] != null)
                        | (matrix[i][j] != null & board.matrix[i][j] == null)) {
                    return false;
                } else if (matrix[i][j] != null & board.matrix[i][j] != null) {
                    if (matrix[i][j].getClass() != board.matrix[i][j].getClass()) {
                        return false;
                    }
                    if (matrix[i][j].getClass() == board.matrix[i][j].getClass()
                            & ((ChessPiece) matrix[i][j]).color != ((ChessPiece) board.matrix[i][j]).color) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public Board() {
        matrix = new ChessPiece[8][8];
        initTeam(Color.White);
        initTeam(Color.Black);
    }

    public void castling(Position start, Position destination) {
        int startVertical = start.getVertical().ordinal();
        int horizontal = start.getHorizontal() - 1;
        int destVertical = destination.getVertical().ordinal();

        Piece temp;

        if (destination.getVertical().equals(Verticals.C)) {
            temp = matrix[horizontal][7]; // rook on queen's flank
            matrix[horizontal][destVertical] = matrix[horizontal][startVertical];
            matrix[horizontal][startVertical + 1] = temp;
            matrix[horizontal][7] = null;

        } else if (destination.getVertical().equals(Verticals.G)) {
            temp = matrix[horizontal][destVertical - 1];
            matrix[horizontal][destVertical] = matrix[horizontal][startVertical];
            matrix[horizontal][destVertical + 1] = temp;
            matrix[horizontal][0] = null;
        }
        matrix[horizontal][startVertical] = null;
//        LOG();
    }

    public void pawnOnThePass(Position start, Position destination) {
        int startVertical = start.getVertical().ordinal();
        int startHorizontal = start.getHorizontal() - 1;
        int destVertical = destination.getVertical().ordinal();
        int destHorizontal = destination.getHorizontal() - 1;

        if (((ChessPiece) matrix[startHorizontal][startVertical]).color.equals(Color.White)) {
            matrix[destHorizontal - 1][destVertical] = null;
        } else {
            matrix[destHorizontal + 1][destVertical] = null;
        }
        matrix[destHorizontal][destVertical] = matrix[startHorizontal][startVertical];
        matrix[startHorizontal][startVertical] = null;
//        LOG();
    }

    public void beatFigure(Position start, Position destination) {
        int startVertical = start.getVertical().ordinal();
        int startHorizontal = start.getHorizontal() - 1;
        int destVertical = destination.getVertical().ordinal();
        int destHorizontal = destination.getHorizontal() - 1;

        Piece figure = matrix[startHorizontal][startVertical];

        matrix[startHorizontal][startVertical] = null;
        matrix[destHorizontal][destVertical] = figure;
//        LOG();
    }

    public boolean isDistanceFree(Movable movement) {
        LinkedList<Piece> distance = getPiecesOnDistance(movement);

        for (int i = 0; i < distance.size(); i++) {
            if (distance.get(i) == null) {
                continue;
            }
            return false;
        }

        return true;
    }

    private LinkedList<Piece> getPiecesOnDistance(Movable movement) {
        Position start = movement.getStart();
        Position destination = movement.getDestination();
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

    private LinkedList<Position> getPositionsOnDistance(Movable movement) {
        Position start = movement.getStart();
        Position destination = movement.getDestination();
        int startVertical = start.getVertical().ordinal();
        int startHorizontal = start.getHorizontal() - 1;
        int destVertical = destination.getVertical().ordinal();
        int destHorizontal = destination.getHorizontal() - 1;
        LinkedList<Position> distance = new LinkedList<>();

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
                distance.add(new BoardPosition(i, startHorizontal + 1));
            }
        } else if (startVertical - destVertical == 0) {
            for (int i = startHorizontal + 1; i < destHorizontal; i++) {
                distance.add(new BoardPosition(startVertical, i + 1));
            }
        } else if (Math.abs(startHorizontal - destHorizontal) == Math.abs(startVertical - destVertical)) {
            if ((start.getVertical().ordinal() < destination.getVertical().ordinal() && start.getHorizontal() < destination.getHorizontal())
                    || (start.getVertical().ordinal() > destination.getVertical().ordinal() && start.getHorizontal() > destination.getHorizontal())) {
                for (int i = 1; (startVertical + i) < destVertical; i++) {
                    Position fuck = new BoardPosition(startVertical + i, startHorizontal + i + 1);
                    distance.add(fuck);
                }
            } else {
                for (int i = 1; (startVertical + i) < destVertical; i++) {
                    Position fuck = new BoardPosition(startVertical + i, destHorizontal - i + 1);
                    distance.add(fuck);
                }
            }
        }

        return distance;
    }

    public boolean isKingUnderAttack(Color teamColor) {
        ChessPiece king = null;
        Position kingPos = null;
        Hashtable<Position, ChessPiece> enemyTeam = getTeamBy(teamColor.equals(Color.White) ? Color.Black : Color.White);
        ChessPiece currentPiece;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                currentPiece = (ChessPiece) matrix[i][j];
                if (isCageEmpty(currentPiece)) {
                    continue;
                }
                if (currentPiece instanceof King & currentPiece.color.equals(teamColor)) {
                    king = currentPiece;
                    kingPos = new BoardPosition(j, i + 1);
                    break;
                }
            }
        }

        Set<Position> keys = enemyTeam.keySet();
        for (Position figure : keys) {
            currentPiece = enemyTeam.get(figure);

            try {
                assert king != null;
                assert currentPiece != null;

                if (currentPiece.isFightTrajectoryValid(new Movement(figure, kingPos))
                        & isDistanceFree(new Movement(kingPos, figure))) {
                    throw new CheckKingException(king.color + " King under attack");
                }
            } catch (CheckKingException cke) {
                return true;
            } catch (ChessException e) {
                // trajectory is incorrect
            }
        }
        return false;
    }

    public void restorePreviousTurn(MovementHistory history) {
        Position destination = history.movement.getDestination();
        Position start       = history.movement.getStart();

        int vertical;
        int horizontal;
        {
            vertical = start.getVertical().ordinal();
            horizontal = start.getHorizontal() - 1;
            matrix[horizontal][vertical] = history.start;
        }
        vertical = destination.getVertical().ordinal();
        horizontal = destination.getHorizontal() - 1;
        matrix[horizontal][vertical] = history.destination;
//        LOG();
    }

    public boolean isCheckMate(Color teamColor) {
        Hashtable<Position, ChessPiece> enemyTeam = getTeamBy(teamColor.equals(Color.White) ? Color.Black : Color.White);
        ChessPiece currentPiece;
        boolean answer = false;
        int attackingFiguresAmount = 0;
        LinkedList<Position> attackingFigures = new LinkedList<>();
        ChessPiece king = null;
        Position kingPos = null;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                currentPiece = (ChessPiece) matrix[i][j];
                if (isCageEmpty(currentPiece)) {
                    continue;
                } if (currentPiece instanceof King & currentPiece.color.equals(teamColor)) {
                    king = currentPiece;
                    kingPos = new BoardPosition(j, i + 1);
                    break;
                }
            }
        }

        Set<Position> keys = enemyTeam.keySet();
        for (Position figure : keys) {
            currentPiece = enemyTeam.get(figure);

            try {
                assert currentPiece != null;

                if (currentPiece.isFightTrajectoryValid(new Movement(figure, kingPos))
                        & isDistanceFree(new Movement(figure, kingPos))
                ) {
                    attackingFigures.add(figure);
                    attackingFiguresAmount++;
                }
            } catch (ChessException e) {
                // trajectory is incorrect
            }
        }

        assert kingPos != null;
        LinkedList<Position> cagesAroundKing = getEmptyPositionsAround(kingPos);
        int cagesUnderAttack = 0;
        for (Position position : cagesAroundKing) {
            if (isPositionUnderAttack(king.color, position)) {
                cagesUnderAttack++;
            }
        }

        if (attackingFiguresAmount > 1) {
            if (cagesUnderAttack == cagesAroundKing.size()) {
                answer = true;
            }
        } else if (attackingFiguresAmount == 1) {
            boolean toBeat = hasNoFigureToSaveKingFromCheck(teamColor, kingPos, attackingFigures.get(0));
            boolean toCover = hasNoFigureToCoverKingFromCheck(teamColor, kingPos, attackingFigures.get(0));
            boolean toMoveAway = hasNoCagesToMoveAway(cagesAroundKing, kingPos);
            if (cagesUnderAttack == cagesAroundKing.size()
                    & (! toBeat & ! toCover)) {
                answer = true;
            } else if (cagesAroundKing.size() - cagesUnderAttack > 0
                    & ! toMoveAway) {
                answer = true;
            }
        }

        return answer;
    }

    private boolean hasNoCagesToMoveAway(LinkedList<Position> emptyCagesAroundKing, Position kingPosition) {
        MovementHistory backUpMove;
        Movement currentMovement;
        ChessPiece king = (ChessPiece) findBy(kingPosition);
        boolean answer = false;

        for (Position cage : emptyCagesAroundKing) {
             try {
                 assert king != null;
                 currentMovement = new Movement(kingPosition, cage);

                 if (king.isTrajectoryValid(currentMovement)
                         && isDistanceFree(currentMovement)) {
                     backUpMove = new MovementHistory(currentMovement, king, findBy(cage));
                     swapFigures(kingPosition, cage);
                     if ( ! isKingUnderAttack(king.color)) {
                         answer = true;
                         restorePreviousTurn(backUpMove);
                         break;
                     }
                     restorePreviousTurn(backUpMove);
                 }
             } catch (ChessException ignored) {

             }
        }

        return answer;
    }

    private boolean hasNoFigureToCoverKingFromCheck(Color kingColor, Position kingPosition, Position attackingPiece) {
        Hashtable<Position, ChessPiece> ourTeam = getTeamBy(kingColor);
        MovementHistory backUpMove;
        Movement currentMovement;
        ChessPiece currentPiece;
        boolean answer = false;

        Set<Position> keys = ourTeam.keySet();
        LinkedList<Position> distance = getPositionsOnDistance(new Movement(kingPosition, attackingPiece));
        for (Position piece : keys) {
            currentPiece = ourTeam.get(piece);
            // cover attacking figure case

            for (Position cage : distance) {
                try {
                    assert currentPiece != null;
                    currentMovement = new Movement(piece, cage);

                    if (currentPiece.isTrajectoryValid(currentMovement)
                            && isDistanceFree(currentMovement)) {
                        backUpMove = new MovementHistory(currentMovement, findBy(piece), findBy(cage));
                        swapFigures(piece, cage);
                        if ( ! isKingUnderAttack(kingColor)) {
                            answer = true;
                            restorePreviousTurn(backUpMove);
                            break;
                        }
                        restorePreviousTurn(backUpMove);
                    }
                } catch (ChessException e) {
                    // trajectory is incorrect
                }
            }
        }

        return answer;
    }

    private boolean hasNoFigureToSaveKingFromCheck(Color kingColor, Position kingPosition, Position attackingPiece) {
        Hashtable<Position, ChessPiece> ourTeam = getTeamBy(kingColor);
        MovementHistory backUpMove;
        Movement currentMovement;
        ChessPiece currentPiece;
        boolean answer = false;

        Set<Position> keys = ourTeam.keySet();
        for (Position piece : keys) {
            currentPiece = ourTeam.get(piece);
            // beat attacking figure case
            try {
                assert currentPiece != null;

                currentMovement = new Movement(piece, attackingPiece);
                if (currentPiece.isFightTrajectoryValid(currentMovement)
                        && isDistanceFree(currentMovement)) {
                    backUpMove = new MovementHistory(currentMovement, findBy(piece), findBy(attackingPiece));
                    beatFigure(piece, attackingPiece);
                    if ( ! isKingUnderAttack(kingColor)) {
                        answer = true;
                        restorePreviousTurn(backUpMove);
                        break;
                    }
                    restorePreviousTurn(backUpMove);
                }
            } catch (ChessException e) {
                // trajectory is incorrect
            }
        }

        return answer;
    }

    public boolean isPositionUnderAttack(Color teamColor, Position position) {
        Hashtable<Position, ChessPiece> enemyTeam = getTeamBy(teamColor.equals(Color.White) ? Color.Black : Color.White);
        ChessPiece currentPiece;
        boolean answer = false;

        Set<Position> keys = enemyTeam.keySet();
        for (Position figure : keys) {
            currentPiece = enemyTeam.get(figure);

            try {
                assert currentPiece != null;

                if (currentPiece.isFightTrajectoryValid(new Movement(position, figure))
                        & isDistanceFree(new Movement(position, figure)) // it doesn't correct in this situation
                ) {
                    answer = true;
                }
            } catch (ChessException e) {
                // trajectory is incorrect
            }
        }

        return answer;
    }

    public Hashtable<Position, ChessPiece> getTeamBy(Color team) {
        Hashtable<Position, ChessPiece> enemyTeam = new Hashtable<>();
        ChessPiece currentPiece;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                currentPiece = (ChessPiece) matrix[i][j];
                if (isCageEmpty(currentPiece)) {
                    continue;
                } if (currentPiece.color.equals(team)) {
                    enemyTeam.put(new BoardPosition(j, i + 1), currentPiece);
                }
            }
        }

        return enemyTeam;
    }

    public boolean checkForDraw(Color enemyColor) {
        Hashtable<Position, ChessPiece> enemyTeam = getTeamBy(enemyColor);
        LinkedList<Position> cagesAround;
        int figuresCannotMove = 0;
        ChessPiece currentPiece;

        // fixme
        //  1. add check for a fight trajectory
        //  2. add check for a not check

        Set<Position> keys = enemyTeam.keySet();
        for (Position figure : keys) {
            currentPiece = enemyTeam.get(figure);
            cagesAround = getEmptyPositionsAround(figure);

            try {
                assert currentPiece != null;

                for (Position cageAround : cagesAround) {
                    if (currentPiece.isTrajectoryValid(new Movement(figure, cageAround))
                            & isDistanceFree(new Movement(cageAround, figure))) {
                        throw new DrawException("Draw");
                    }
                }
            } catch (DrawException de) {
                figuresCannotMove++;
            } catch (ChessException e) {
                // trajectory is incorrect
            }
        }
        return figuresCannotMove == enemyTeam.size();
    }

    // TODO
    //  1. maybe delete it in the end
    public void checkForKingCheck(Color team) throws CheckKingException {
        ChessPiece king = null;
        Position kingPos = null;
        Hashtable<Position, Piece> enemyTeam = new Hashtable<>();
        ChessPiece currentPiece;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                currentPiece = (ChessPiece) matrix[i][j];
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
            currentPiece = (ChessPiece) enemyTeam.get(figure);

            try {
                assert king != null;
                assert currentPiece != null;

                if (currentPiece.isFightTrajectoryValid(new Movement(kingPos, figure))
                        & isDistanceFree(new Movement(kingPos, figure))) {
                    throw new CheckKingException(king.color + " King under attack");
                }
            } catch (CheckKingException cke) {
                throw cke;
            } catch (ChessException e) {
                // trajectory is incorrect
            }
        }
    }

    private LinkedList<Position> getEmptyPositionsAround(Position position) {
        int vertical = position.getVertical().ordinal();
        int horizontal = position.getHorizontal() - 1;
        LinkedList<Position> freeCages = new LinkedList<>();

        for (int i = horizontal - 1; i < horizontal + 2; i++) {
            try {
                if (isCageEmpty(matrix[i][vertical - 1])) {
                    freeCages.add(new BoardPosition(vertical - 1, i + 1));
                }
            } catch (IndexOutOfBoundsException ignored) {

            }
        }

        for (int i = horizontal - 1; i < horizontal + 2; i++) {
            try {
                if (isCageEmpty(matrix[i][vertical + 1])) {
                    freeCages.add(new BoardPosition(vertical + 1, i + 1));
                }
            } catch (IndexOutOfBoundsException ignored) {

            }
        }

        try {
            if (isCageEmpty(matrix[horizontal + 1][vertical])) {
                freeCages.add(new BoardPosition(vertical, horizontal + 2));
            }
        } catch (IndexOutOfBoundsException ignored) {

        }

        try {
            if (isCageEmpty(matrix[horizontal - 1][vertical])) {
                freeCages.add(new BoardPosition(vertical, horizontal));
            }
        } catch (IndexOutOfBoundsException ignored) {

        }

        return freeCages;
    }

    public Piece findBy(Position position) {
        int vertical = position.getVertical().ordinal();
        int horizontal = position.getHorizontal() - 1;

        if (isCageEmpty(matrix[horizontal][vertical])) {
            return null;
        }

        return matrix[horizontal][vertical];
    }

    // for debug
    public void LOG() {
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

    public void swapFigures(Position start, Position destination) {
        int startVertical = start.getVertical().ordinal();
        int startHorizontal = start.getHorizontal() - 1;
        int destVertical = destination.getVertical().ordinal();
        int destHorizontal = destination.getHorizontal() - 1;

        Piece figure = matrix[startHorizontal][startVertical];

        matrix[startHorizontal][startVertical] = matrix[destHorizontal][destVertical];
        matrix[destHorizontal][destVertical] = figure;
//        LOG();
    }

    public boolean isCageEmpty(Piece figure) {
        return figure == null;
    }

    public void initTeam(Color color) {
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
