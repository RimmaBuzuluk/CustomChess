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
import com.example.customchess.engine.figures.Knight;
import com.example.customchess.engine.figures.Pawn;
import com.example.customchess.engine.figures.Piece;
import com.example.customchess.engine.figures.Queen;
import com.example.customchess.engine.figures.Rook;
import com.example.customchess.engine.misc.Color;
import com.example.customchess.engine.movements.Movable;
import com.example.customchess.engine.movements.Position;

import java.util.Stack;


public class OneDeviceGame implements Game {

    private Board board;
    private Player currentPlayer;
    private Stack<MovementHistory> movementStack;

    //  TODO
    //   4. implement draw checker

    public OneDeviceGame() {
        board = new Board();
        currentPlayer = new WhitePlayer(this);
        movementStack = new Stack<>();
    }

    public Board getBoard() {
        return board;
    }

    public void setCurrentPlayer(Player player) {
        currentPlayer = player;
    }

    public MovementHistory getLastMovement() {
        return movementStack.peek();
    }

    public void checkForCheckMate() throws CheckMateException {
        if (board.isCheckMate(currentPlayer.getColor())) {
            throw new CheckMateException("Mate on the board\n" + currentPlayer.getColor() + " is fucked");
        }
    }

    public void promotion(String choice) {
        ChessPiece piece;
        Color team = ((ChessPiece) movementStack.peek().start).color;

        switch (choice) {
            case "Queen":
                piece = new Queen(team);
                break;
            case "Bishop":
                piece = new Bishop(team);
                break;
            case "Rook":
                piece = new Rook(team);
                break;
            default:
                piece = new Knight(team);
                break;
        }

        board.promoteTo(movementStack.peek().movement.getDestination(), piece);
    }

    public void canMakeMovement(Movable movement) throws ChessException {

        try {
            Position start = movement.getStart();
            Position destination = movement.getDestination();
            Piece startFigure = board.findBy(movement.getStart());
            Piece destinationFigure = board.findBy(movement.getDestination());  // can be null
            MovementHistory currentMovementHeader = new MovementHistory(movement, startFigure, destinationFigure);

            if (currentPlayer.isCorrectPlayerMove((ChessPiece) startFigure)) {
                try {
                    startFigure.tryToMove(movement, this);

                } catch (MoveOnEmptyCageException mec) {
                    board.swapFigures(start, destination);
                } catch (BeatFigureException bfe) {
                    board.beatFigure(start, destination);
                } catch (CastlingException ce) {
                    board.castling(start, destination);
                    currentPlayer.changePlayer();
                    movementStack.push(currentMovementHeader);
                    throw ce;
                } catch (PawnOnThePassException ppe) {
                    board.pawnOnThePass(start, destination);
                    currentPlayer.changePlayer();
                    movementStack.push(currentMovementHeader);
                    throw ppe;
                } catch (PromotionException pe) {
                    board.promotion(start, destination);
                    currentPlayer.changePlayer();
                    movementStack.push(currentMovementHeader);
                    throw pe;
                }

                if (board.isKingUnderAttack(currentPlayer.getColor())) {
                    board.restorePreviousTurn(currentMovementHeader);
                    throw new CheckKingException(currentPlayer.getColor() + " King under check");
                }
                currentPlayer.changePlayer();
                movementStack.push(currentMovementHeader);
            }

        } catch (NullPointerException npe) {
            throw new FigureNotChosenException("Figure was not chosen");
        }
    }
}
