package com.example.customchess.engine;

import com.example.customchess.engine.exceptions.CheckKingException;
import com.example.customchess.engine.exceptions.ChessException;
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

    public Board(Piece[][] matrix) {
        this.matrix = matrix;
    }

    public Board() {
        matrix = new ChessPiece[8][8];
        initTeam(Color.White);
        initTeam(Color.Black);
    }

    public void castling(Position start, Position destination) {
        int horizontal = start.getHorizontal();
        int startVertical = start.getVertical().ordinal();

        Position oldRookPlace;
        Position newRookPlace;

        if ( BoardPosition.isShortSchemeCastling(destination) ) {
            oldRookPlace = new BoardPosition(Verticals.H, horizontal);
            newRookPlace = new BoardPosition(startVertical - 1, horizontal);

        } else {
            oldRookPlace = new BoardPosition(Verticals.A, horizontal);
            newRookPlace = new BoardPosition(startVertical + 1, horizontal);
        }
        put(destination, findBy(start));
        put(newRookPlace, findBy(oldRookPlace));
        hide(oldRookPlace);
        hide(start);
    }

    public void pawnOnThePass(Position start, Position destination) {
        Position oldAttackedPawn;

        if (((ChessPiece) findBy(start)).color.equals(Color.White)) {
            oldAttackedPawn = new BoardPosition(destination.getVertical().ordinal(),
                    destination.getHorizontal() - 1);
        } else {
            oldAttackedPawn = new BoardPosition(destination.getVertical().ordinal(),
                    destination.getHorizontal() + 1);
        }
        put(destination, findBy(start));
        hide(start);
        hide(oldAttackedPawn);
    }

    public void beatFigure(Position start, Position destination) {
        put(destination, findBy(start));
        hide(start);
    }

    private void hide(Position position) {
        put(position, null);
    }

    private void put(Position position, Piece piece) {
        int vertical = position.getVertical().ordinal();
        int horizontal = position.getHorizontal() - 1;

        matrix[horizontal][vertical] = piece;
    }

    public void swapFigures(Position start, Position destination) {
        Piece startFigure = findBy(start);
        Piece destinationFigure = findBy(destination);

        put(start, destinationFigure);
        put(destination, startFigure);
    }

    public boolean isDistanceFree(Movable movement) {
        // TODO: 28.01.21 move method getPositionsOnDistance to Movement maybe
        LinkedList<Position> path = getPositionsOnDistance(movement);

        for (int i = 0; i < path.size(); i++) {
            if ( ! isCageEmpty(
                    findBy(path.get(i))) ) {
                return false;
            }
        }

        return true;
    }

    public void restorePreviousTurn(MovementHistory history) {
        put(history.movement.getStart(), history.start);
        put(history.movement.getDestination(), history.destination);
    }

    public void promoteTo(Position position, ChessPiece promotion) {
        put(position, promotion);
    }

    public Piece findBy(Position position) {
        int vertical = position.getVertical().ordinal();
        int horizontal = position.getHorizontal() - 1;

        if (isCageEmpty(matrix[horizontal][vertical])) {
            return null;
        }

        return matrix[horizontal][vertical];
    }

    public boolean isCageEmpty(Piece figure) {
        return figure == null;
    }

    public void promotion(Position start, Position destination) {
        if ( ! isCageEmpty(findBy(destination)) ) {
            beatFigure(start, destination);
        } else {
            swapFigures(start, destination);
        }
    }

    public void initTeam(Color color) {
        int pawnRow = 1;
        int kingRow = 0;
        if (Color.Black.equals(color)) {
            pawnRow = 6;
            kingRow = 7;
        }
        matrix[kingRow][0] = new Rook(color, new BoardPosition(Verticals.H, kingRow + 1));
        matrix[kingRow][7] = new Rook(color, new BoardPosition(Verticals.A, kingRow + 1));
        matrix[kingRow][1] = new Knight(color, new BoardPosition(Verticals.G, kingRow + 1));
        matrix[kingRow][6] = new Knight(color, new BoardPosition(Verticals.B, kingRow + 1));
        matrix[kingRow][2] = new Bishop(new BoardPosition(Verticals.C, kingRow + 1), color);
        matrix[kingRow][5] = new Bishop(new BoardPosition(Verticals.F, kingRow + 1), color);
        matrix[kingRow][3] = new King(color, new BoardPosition(Verticals.E, kingRow + 1));
        matrix[kingRow][4] = new Queen(color, new BoardPosition(Verticals.D, kingRow + 1));
        for (int vertical = 0; vertical < 8; vertical++) {
            matrix[pawnRow][vertical] = new Pawn(color, new BoardPosition(vertical, pawnRow + 1));
        }
    }

    //  not refactored -----------------------------------------------------------------------------

    // TODO: 29.01.21 refactor it
    public boolean isCheckMate(Color teamColor) {
        Hashtable<Position, ChessPiece> enemyTeam = getTeamBy(Color.getOppositeColor(teamColor));
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
            boolean canBeat = hasNoFigureToSaveKingFromCheck(teamColor, attackingFigures.get(0));
            boolean canCover = hasNoFigureToCoverKingFromCheck(teamColor, kingPos, attackingFigures.get(0));
            boolean canMoveAway = hasNoCagesToMoveAway(cagesAroundKing, kingPos);
            if ( ! canBeat
                    & ! canMoveAway
                    & ! canCover) {
                answer = true;
            }
        }

        return answer;
    }

    //  TODO try to refactor this piece of garbage code
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
        Hashtable<Position, ChessPiece> enemyTeam = getTeamBy(Color.getOppositeColor(teamColor));
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

//  --------- todo refactor this at the end --------------------------------------------------------
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

    private boolean hasNoFigureToSaveKingFromCheck(Color kingColor, Position attackingPiece) {
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
//  ------------------------------------------------------------------------------------------------

    public boolean isPositionUnderAttack(Color teamColor, Position position) {
        Hashtable<Position, ChessPiece> enemyTeam = getTeamBy( Color.getOppositeColor(teamColor) );
        ChessPiece currentPiece;
        boolean answer = false;

        Set<Position> keys = enemyTeam.keySet();
        for (Position figure : keys) {
            currentPiece = enemyTeam.get(figure);

            try {
                assert currentPiece != null;

                if (currentPiece.isFightTrajectoryValid(new Movement(position, figure))
                        & isDistanceFree(new Movement(position, figure))) {
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

    // TODO: 29.01.21 implement it in correct way
    public boolean checkForDraw(Color teamColor) {
        Hashtable<Position, ChessPiece> team = getTeamBy(teamColor);
        LinkedList<Position> cagesAround;
        int figuresCannotMove = 0;
        Position kingPos = null;
        ChessPiece king = null;
        ChessPiece currentPiece;

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


        return figuresCannotMove == team.size() && ! isKingUnderAttack(teamColor);
    }

    // TODO: 29.01.21 move it to Position class
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
}