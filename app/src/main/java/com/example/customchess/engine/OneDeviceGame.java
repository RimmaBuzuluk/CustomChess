package com.example.customchess.engine;

import com.example.customchess.engine.exceptions.BeatFigureException;
import com.example.customchess.engine.exceptions.CastlingException;
import com.example.customchess.engine.exceptions.CheckKingException;
import com.example.customchess.engine.exceptions.CheckMateException;
import com.example.customchess.engine.exceptions.ChessException;
import com.example.customchess.engine.exceptions.DrawException;
import com.example.customchess.engine.exceptions.FigureNotChosenException;
import com.example.customchess.engine.exceptions.MoveOnEmptyCageException;
import com.example.customchess.engine.exceptions.PawnOnThePassException;
import com.example.customchess.engine.exceptions.PromotionException;
import com.example.customchess.engine.figures.Bishop;
import com.example.customchess.engine.figures.ChessPiece;
import com.example.customchess.engine.figures.King;
import com.example.customchess.engine.figures.Knight;
import com.example.customchess.engine.figures.Pawn;
import com.example.customchess.engine.figures.Piece;
import com.example.customchess.engine.figures.Queen;
import com.example.customchess.engine.figures.Rook;
import com.example.customchess.engine.misc.Color;
import com.example.customchess.engine.misc.Verticals;
import com.example.customchess.engine.movements.BoardPosition;
import com.example.customchess.engine.movements.Movable;
import com.example.customchess.engine.movements.Position;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;


public class OneDeviceGame implements Game {

    private Board board;
    private Player currentPlayer;
    private Stack<MovementHistory> movementStack;
    private List<Piece> blackTeam;
    private List<Piece> whiteTeam;
    private EndGameChecker    gameAnalyser;

    //  TODO
    //   4. implement draw checker

    public OneDeviceGame() {
        movementStack = new Stack<>();
        currentPlayer = new WhitePlayer(this);
        whiteTeam = new LinkedList<>();
        blackTeam = new LinkedList<>();
        initTeam(blackTeam, Color.Black);
        initTeam(whiteTeam, Color.White);
        board = new Board(blackTeam, whiteTeam);
        gameAnalyser  = new EndGameChecker(board, whiteTeam, blackTeam);
    }

    private void initTeam(List<Piece> team, Color color) {
        int pawnRow = 1;
        int kingRow = 0;
        if (Color.Black.equals(color)) {
            pawnRow = 6;
            kingRow = 7;
        }
        team.add(new Rook(color, new BoardPosition(Verticals.H, kingRow + 1)));
        team.add(new Rook(color, new BoardPosition(Verticals.A, kingRow + 1)));
        team.add(new Knight(color, new BoardPosition(Verticals.G, kingRow + 1)));
        team.add(new Knight(color, new BoardPosition(Verticals.B, kingRow + 1)));
        team.add(new Bishop(color, new BoardPosition(Verticals.C, kingRow + 1)));
        team.add(new Bishop(color, new BoardPosition(Verticals.F, kingRow + 1)));
        team.add(new King(color, new BoardPosition(Verticals.E, kingRow + 1)));
        team.add(new Queen(color, new BoardPosition(Verticals.D, kingRow + 1)));
        for (int vertical = 0; vertical < 8; vertical++) {
            team.add(new Pawn(color, new BoardPosition(vertical, pawnRow + 1)));
        }
    }

    public Board getBoard() {
        return board;
    }

    public EndGameChecker getGameAnalyser() {
        return gameAnalyser;
    }

    public void setCurrentPlayer(Player player) {
        currentPlayer = player;
    }

    public MovementHistory getLastMovement() {
        return movementStack.peek();
    }

    public void checkForPat() throws DrawException {
//        if (gameAnalyser.checkForDraw(currentPlayer.getColor())) {
//            throw new DrawException("Draw");
//        }
    }

    public void checkForCheckMate() throws CheckMateException {
        if (gameAnalyser.isCheckMate(currentPlayer.getColor())) {
            throw new CheckMateException("Mate on the board\n" + currentPlayer.getColor() + " is fucked");
        }
    }

    public void promotion(String choice) {
        ChessPiece piece;
        Color team = ((ChessPiece) movementStack.peek().start).color;
        Position destination = movementStack.peek().movement.getDestination();

        switch (choice) {
            case "Queen":
                piece = new Queen(team, destination);
                break;
            case "Bishop":
                piece = new Bishop(team, destination);
                break;
            case "Rook":
                piece = new Rook(team, destination);
                break;
            default:
                piece = new Knight(team, destination);
                break;
        }

        board.promoteTo(destination, piece);
    }

    private void removePieceFromTeam(Piece piece) {
        List<Piece> team = piece.getColor().equals(Color.White) ? whiteTeam : blackTeam;
        team.remove(piece);
    }

    public void canMakeMovement(Movable movement) throws ChessException {
        Position start = movement.getStart();
        Position destination = movement.getDestination();
        Piece startFigure = board.findBy(movement.getStart());
        Piece destinationFigure = board.findBy(movement.getDestination());  // can be null
        MovementHistory currentMovementHeader = new MovementHistory(movement, startFigure, destinationFigure);

        try {
            if (currentPlayer.isCorrectPlayerMove((ChessPiece) startFigure)) {
                try {
                    startFigure.tryToMove(movement, this);

                } catch (MoveOnEmptyCageException mec) {
                    board.swapFigures(start, destination);
                    startFigure.move(destination);
                    throw mec;
                } catch (BeatFigureException bfe) {
                    board.beatFigure(start, destination);
                    startFigure.move(destination);
                    removePieceFromTeam(destinationFigure);
                    throw bfe;
                } catch (CastlingException ce) {
                    board.castling(start, destination);
                    startFigure.move(destination);  // king's move
                    BoardPosition.isShortSchemeCastling(start);
                    // rook's move
                    throw ce;
                } catch (PawnOnThePassException ppe) {
                    board.pawnOnThePass(start, destination);
                    startFigure.move(destination);
                    // remove figure from list
                    throw ppe;
                } catch (PromotionException pe) {
                    board.promotion(start, destination);
                    startFigure.move(destination);
                    removePieceFromTeam(destinationFigure);
                    throw pe;
                }
            }
// TODO: 30.01.21 maybe create MoveException
        } catch (MoveOnEmptyCageException
                | BeatFigureException
                | CastlingException
                | PawnOnThePassException
                | PromotionException ce) {
            if (gameAnalyser.isKingUnderAttack(currentPlayer.getColor())) {
                board.restorePreviousTurn(currentMovementHeader);
                throw new CheckKingException(currentPlayer.getColor() + " King under check");
            }
            currentPlayer.changePlayer();
            movementStack.push(currentMovementHeader);
        } catch (NullPointerException npe) {
            throw new FigureNotChosenException("Figure was not chosen");
        }
    }


}
