package com.example.customchess.engine.automata;

import com.example.customchess.engine.OneDeviceGame;
import com.example.customchess.engine.automata.Player;
import com.example.customchess.engine.automata.WhitePlayer;
import com.example.customchess.engine.exceptions.InvalidOrderMoveException;
import com.example.customchess.engine.figures.ChessPiece;
import com.example.customchess.engine.misc.Color;


public class BlackPlayer implements Player {

    private final OneDeviceGame game;
    private final Color         teamColor;

    public BlackPlayer(OneDeviceGame game) {
        this.game = game;
        teamColor = Color.Black;
    }

    @Override
    public boolean isCorrectPlayerMove(ChessPiece selected) throws InvalidOrderMoveException {
        if (selected.isBlack()) {
            return true;
        }
        throw new InvalidOrderMoveException("Now the move of black player");
    }

    @Override
    public void changePlayer() {
        game.setCurrentPlayer(new WhitePlayer(game));
    }

    @Override
    public Color getColor() {
        return teamColor;
    }
}
