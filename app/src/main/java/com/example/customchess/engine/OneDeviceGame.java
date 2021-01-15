package com.example.customchess.engine;

import com.example.customchess.engine.exceptions.BeatFigureException;
import com.example.customchess.engine.exceptions.CastlingException;
import com.example.customchess.engine.exceptions.ChessException;
import com.example.customchess.engine.exceptions.FigureNotChosenException;
import com.example.customchess.engine.exceptions.MoveOnEmptyCageException;
import com.example.customchess.engine.exceptions.PawnOnThePassException;
import com.example.customchess.engine.exceptions.PromotionException;
import com.example.customchess.engine.figures.ChessPiece;
import com.example.customchess.engine.figures.Piece;
import com.example.customchess.engine.misc.Color;
import com.example.customchess.engine.misc.Verticals;
import com.example.customchess.engine.movements.BoardPosition;
import com.example.customchess.engine.movements.Movable;
import com.example.customchess.engine.movements.Position;

import java.util.EmptyStackException;
import java.util.Stack;


public class OneDeviceGame implements Game {

    private Board board;
    private Player currentPlayer;
    private Stack<MovementHistory> movementStack;

    //  TODO
    //  2. after try-catch (movements) make a check for a 'check'
    //  3. implement checkmate function

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
                    movementStack.push(new MovementHistory(movement, startFigure, destinationFigure));
                    throw ce;
                } catch (PawnOnThePassException ppe) {
                    board.pawnOnThePass(start, destination);
                    currentPlayer.changePlayer();
                    movementStack.push(new MovementHistory(movement, startFigure, destinationFigure));
                    throw ppe;
                } catch (PromotionException pe) {

                }

                currentPlayer.changePlayer();
                movementStack.push(new MovementHistory(movement, startFigure, destinationFigure));
//                board.isPositionUnderAttack(new BoardPosition(Verticals.A, 5));
//                 if (check for a 'check')
//                     move(firstMove = false) ?
//                     back stack position

            }

        } catch (NullPointerException npe) {
            throw new FigureNotChosenException("Figure was not chosen");
        }

    }


}
