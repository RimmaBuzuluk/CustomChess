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
import com.example.customchess.engine.figures.ChessPiece;
import com.example.customchess.engine.figures.Piece;
import com.example.customchess.engine.misc.Color;
import com.example.customchess.engine.movements.Movable;
import com.example.customchess.engine.movements.Position;

import java.util.Stack;


public class OneDeviceGame implements Game {

    private Board board;
    private Player currentPlayer;
    private Stack<MovementHistory> movementStack;

    //  TODO
    //   3. implement checkmate function
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

    public void canMakeMovement(Movable movement) throws ChessException {

        try {
            Position start = movement.getStart();
            Position destination = movement.getDestination();
            Piece startFigure = board.findBy(movement.getStart());
            Piece destinationFigure = board.findBy(movement.getDestination());  // can be null
            MovementHistory currentMovementHeader = new MovementHistory(movement, startFigure, destinationFigure);

            if (board.checkForDraw(currentPlayer.getColor().equals(Color.White) ? Color.Black : Color.White)) {
                throw new DrawException("Draw");
            }

            if (currentPlayer.isCorrectPlayerMove((ChessPiece) startFigure)) {
                try {
                    startFigure.tryToMove(movement, this);

                    // TODO
                    //  maybe add to some catches check for 'check'
                } catch (MoveOnEmptyCageException mec) {
                    board.swapFigures(start, destination);
                } catch (BeatFigureException bfe) {
                    board.beatFigure(start, destination);
                } catch (CastlingException ce) {
                    board.castling(start, destination);
                    currentPlayer.changePlayer();
                    throw ce;
                } catch (PawnOnThePassException ppe) {
                    board.pawnOnThePass(start, destination);
                    currentPlayer.changePlayer();
                    throw ppe;
                } catch (PromotionException pe) {

                }

                // it should be in a method
//                if (board.isCheckMate(currentPlayer.getColor())) {
//                    throw new CheckMateException("Mate on the board");
//                }

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
