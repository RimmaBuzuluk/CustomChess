package com.example.customchess.engine;

import android.util.Log;

import com.example.customchess.engine.exceptions.CastlingException;
import com.example.customchess.engine.exceptions.ChessException;
import com.example.customchess.engine.exceptions.FigureNotChosenException;
import com.example.customchess.engine.movements.Movable;


public class OneDeviceGame implements Game {

    private Board board;
    private Player currentPlayer;


    public OneDeviceGame() {
        board = new Board();
        currentPlayer = new WhitePlayer(this);

    }

    public void setCurrentPlayer(Player player) {
        currentPlayer = player;
    }

    public void canMakeMovement(Movable movement) throws ChessException {

        try {
            if (currentPlayer.isCorrectPlayerMove(board.findBy(movement.getStart()))) {
                if (board.tryToMove(movement)) {
                    currentPlayer.changePlayer();
                }
            }

        } catch (CastlingException ce) {
            currentPlayer.changePlayer();
            throw ce;
        } catch (NullPointerException npe) {
            throw new FigureNotChosenException("Figure was not chosen");
        }

    }


}
