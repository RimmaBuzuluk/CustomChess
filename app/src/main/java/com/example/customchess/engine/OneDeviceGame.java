package com.example.customchess.engine;

import android.util.Log;

import com.example.customchess.engine.exceptions.ChessException;
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
//            Log.d("move", movement.toString());
            if (currentPlayer.isCorrectPlayerMove(board.findBy(movement.getStart()))) {
                if (board.tryToMove(movement)) {

                    currentPlayer.changePlayer();
                }
            }

        }
        catch (ChessException ex) {
            throw ex;
        }
        catch (NullPointerException npe) {
//            npe.printStackTrace();
        }
    }


}
