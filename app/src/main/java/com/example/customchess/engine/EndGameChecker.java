package com.example.customchess.engine;

import com.example.customchess.engine.exceptions.ChessException;
import com.example.customchess.engine.figures.King;
import com.example.customchess.engine.figures.Piece;
import com.example.customchess.engine.misc.Color;
import com.example.customchess.engine.movements.Movement;
import com.example.customchess.engine.movements.Position;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class EndGameChecker {

    private final Board board;
    private List<Piece> whiteTeam;
    private List<Piece> blackTeam;

    public EndGameChecker(Board board,
                          List<Piece> whiteTeam,
                          List<Piece> blackTeam) {
        this.board = board;
        this.whiteTeam = whiteTeam;
        this.blackTeam = blackTeam;
    }

    public boolean isCheckMate(Color teamColor) {
        List<Piece> enemyTeam = getTeamBy(Color.getOppositeColor(teamColor));
        Piece king = getKingBy(teamColor);
        assert king != null;
        Position kingPos = king.getCurrentPosition();
        boolean answer = false;
        List<Position> attackingFigures = getAttackingFigures(enemyTeam, kingPos);
        int attackingFiguresAmount = attackingFigures.size();
        int cagesAroundKingUnderAttack = 0;
        List<Position> emptyCagesAroundKing = getEmptyPositionsAround(kingPos);

        for (Position position : emptyCagesAroundKing) {
            if (isPositionUnderAttackByEnemyTeam(king.getColor(), position)) {
                cagesAroundKingUnderAttack++;
            }
        }

        if (attackingFiguresAmount > 1) {
            if (cagesAroundKingUnderAttack == emptyCagesAroundKing.size()) {
                answer = true;
            }
        } else if (attackingFiguresAmount == 1) {
            boolean canBeat = isFigureToBeatAttackingPiece(teamColor, attackingFigures.get(0));
            boolean canCover = isPieceToCoverKingFromCheck(teamColor, kingPos, attackingFigures.get(0));
            boolean canMoveAway = isCageToMoveKingAway(emptyCagesAroundKing, kingPos);
            if ( ! canBeat
                    & ! canMoveAway
                    & ! canCover) {
                answer = true;
            }
        }

        return answer;
    }

    private List<Position> getAttackingFigures(List<Piece> enemyTeam, Position attackedPosition) {
        List<Position> attackingFigures = new LinkedList<>();
        Position currentPosition;

        for (Piece figure : enemyTeam) {
            currentPosition = figure.getCurrentPosition();

            try {
                if (figure.isFightTrajectoryValid(new Movement(currentPosition, attackedPosition))
                        & board.isDistanceFree(new Movement(currentPosition, attackedPosition))) {
                    attackingFigures.add(currentPosition);
                }
            } catch (ChessException e) {
                // trajectory is incorrect
            }
        }

        return attackingFigures;
    }

    private Piece getKingBy(Color teamColor) {
        List<Piece> team = getTeamBy(teamColor);

        for (Piece currentPiece : team) {
            if ( currentPiece instanceof King ) {
                return currentPiece;
            }
        }
        return null;
    }

    public boolean isKingUnderAttack(Color teamColor) {
        Piece king = getKingBy(teamColor);
        assert king != null;
        return isPositionUnderAttackByEnemyTeam(teamColor, king.getCurrentPosition());
    }

    public boolean isPositionUnderAttackByEnemyTeam(Color teamColor, Position position) {
        List<Piece> enemyTeam = getTeamBy( Color.getOppositeColor(teamColor) );
        Position currentPosition;
        boolean answer = false;

        for (Piece figure : enemyTeam) {
            currentPosition = figure.getCurrentPosition();

            try {
                assert currentPosition != null;
                Movement move = new Movement(currentPosition, position);

                if (figure.isFightTrajectoryValid(move)
                        & board.isDistanceFree(move)
                        & ! position.equals(currentPosition)) {
                    answer = true;
                    break;
                }
            } catch (ChessException e) {
                // trajectory is incorrect
            }
        }

        return answer;
    }

    public boolean isCageToMoveKingAway(List<Position> emptyCagesAroundKing, Position kingPosition) {
        MovementHistory backUpMove;
        Movement currentMovement;
        Piece king = board.findBy(kingPosition);
        boolean answer = false;

        for (Position cage : emptyCagesAroundKing) {
            try {
                assert king != null;
                currentMovement = new Movement(kingPosition, cage);

                if (king.isTrajectoryValid(currentMovement)
                        && board.isDistanceFree(currentMovement)) {
                    backUpMove = new MovementHistory(currentMovement, king, board.findBy(cage));
                    board.swapFigures(kingPosition, cage);
                    king.setPosition(cage);
                    if ( ! isKingUnderAttack(king.getColor())) {
                        answer = true;
                        board.restorePreviousTurn(backUpMove);
                        king.setPosition(kingPosition);
                        break;
                    }
                    board.restorePreviousTurn(backUpMove);
                    king.setPosition(kingPosition);
                }
            } catch (ChessException ignored) {

            }
        }

        return answer;
    }

    public boolean isPieceToCoverKingFromCheck(Color kingColor, Position kingPosition, Position attackingPiece) {
        List<Piece> ourTeam = getTeamBy(kingColor);
        MovementHistory backUpMove;
        Movement currentMovement;
        Position currentPosition;
        boolean answer = false;
        LinkedList<Position> distance = Movement.getPositionsOnDistance(new Movement(kingPosition, attackingPiece));

        for (Piece piece : ourTeam) {
            currentPosition = piece.getCurrentPosition();
            // cover attacking figure case

            for (Position cage : distance) {
                try {
                    assert currentPosition != null;
                    currentMovement = new Movement(currentPosition, cage);

                    if (piece.isTrajectoryValid(currentMovement)
                            && board.isDistanceFree(currentMovement)) {
                        backUpMove = new MovementHistory(currentMovement, board.findBy(currentPosition), board.findBy(cage));
                        board.swapFigures(currentPosition, cage);
                        piece.setPosition(cage);
                        if ( ! isKingUnderAttack(kingColor)) {
                            answer = true;
                            board.restorePreviousTurn(backUpMove);
                            piece.setPosition(currentPosition);
                            break;
                        }
                        board.restorePreviousTurn(backUpMove);
                        piece.setPosition(currentPosition);
                    }
                } catch (ChessException e) {
                    // trajectory is incorrect
                }
            }
        }

        return answer;
    }

    public boolean isFigureToBeatAttackingPiece(Color kingColor, Position attackingPiece) {
        List<Piece> ourTeam = getTeamBy(kingColor);
        List<Piece> enemyTeam = getTeamBy(Color.getOppositeColor(kingColor));
        MovementHistory backUpMove;
        Movement currentMovement;
        Position currentPosition;
        boolean answer = false;

        for (Piece piece : ourTeam) {
            currentPosition = piece.getCurrentPosition();
            // beat attacking figure case
            try {
                assert currentPosition != null;

                currentMovement = new Movement(currentPosition, attackingPiece);
                if (piece.isFightTrajectoryValid(currentMovement)
                        && board.isDistanceFree(currentMovement)) {
                    backUpMove = new MovementHistory(currentMovement, board.findBy(currentPosition), board.findBy(attackingPiece));
                    enemyTeam.remove(board.findBy(attackingPiece));
                    board.beatFigure(currentPosition, attackingPiece);
                    piece.setPosition(attackingPiece);
                    if ( ! isKingUnderAttack(kingColor)) {
                        answer = true;
                        board.restorePreviousTurn(backUpMove);
                        piece.setPosition(currentPosition);
                        enemyTeam.add(board.findBy(attackingPiece));
                        break;
                    }
                    board.restorePreviousTurn(backUpMove);
                    piece.setPosition(currentPosition);
                    enemyTeam.add(board.findBy(attackingPiece));
                }
            } catch (ChessException e) {
                // trajectory is incorrect
            }
        }

        return answer;
    }

    public List<Piece> getTeamBy(Color teamColor) {
        return teamColor.equals(Color.White) ? whiteTeam : blackTeam;
    }

    //TODO: 29.01.21 implement it in correct way

//    public boolean checkForDraw(Color teamColor) {
//        Hashtable<Position, ChessPiece> team = getTeamBy(teamColor);
//        LinkedList<Position> cagesAround;
//        int figuresCannotMove = 0;
//        Position kingPos = null;
//        ChessPiece king = null;
//        ChessPiece currentPiece;
//
//        for (int i = 0; i < board.getBoardRange(); i++) {
//            for (int j = 0; j < board.getBoardRange(); j++) {
//                currentPiece = (ChessPiece) matrix[i][j];
//                if (board.isCageEmpty(currentPiece)) {
//                    continue;
//                } if (currentPiece instanceof King & currentPiece.color.equals(teamColor)) {
//                    king = currentPiece;
//                    kingPos = new BoardPosition(j, i + 1);
//                    break;
//                }
//            }
//        }
//
//        return figuresCannotMove == team.size() && ! isKingUnderAttack(teamColor);
//    }

    public List<Position> getEmptyPositionsAround(Position position) {
        List<Position> cagesAround = position.getPositionsAround();
        List<Position> freeCages = new ArrayList<>(cagesAround.size());

        for (Position place : cagesAround) {
            if ( board.isCageEmpty(board.findBy(place)) ) {
                freeCages.add(place);
            }
        }

        return freeCages;
    }
}
