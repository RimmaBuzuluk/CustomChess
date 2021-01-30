package com.example.customchess;

import com.example.customchess.engine.OneDeviceGame;
import com.example.customchess.engine.exceptions.CheckMateException;
import com.example.customchess.engine.exceptions.ChessException;
import com.example.customchess.engine.movements.BoardPosition;
import com.example.customchess.engine.movements.Movement;

import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class OneDeviceGameTest extends FigureMoveTest {

    OneDeviceGame testGame = new OneDeviceGame();
    List<Movement> gameMovements = new LinkedList<>();

    public void gameInit1() {
        gameMovements.add(new Movement(e2, e4));
        gameMovements.add(new Movement(b7, b5));

        gameMovements.add(new Movement(d1, h5));
        gameMovements.add(new Movement(h7, h6));

        gameMovements.add(new Movement(f1, c4));
        gameMovements.add(new Movement(b5, b4));
        gameMovements.add(new Movement(c4, c2)); // fail

        gameMovements.add(new Movement(c4, f7));
    }

    @Test
    public void t1() {
        gameInit1();
        for (Movement movement : gameMovements) {
            try {
                testGame.canMakeMovement(movement);
            } catch (ChessException ignored) { }
            try {
                testGame.checkForCheckMate();
            } catch (CheckMateException ignored) { }
        }
        boolean answer = false;
        try {
            testGame.checkForCheckMate();
        } catch (CheckMateException e) {
            answer = true;
        }
        assertTrue(answer);
    }





}
