package com.example.customchess;

import com.example.customchess.engine.exceptions.ChessException;
import com.example.customchess.engine.figures.Rook;
import com.example.customchess.engine.misc.Color;
import com.example.customchess.engine.movements.Movement;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class RookMoveTest extends FigureMoveTest {
    Rook rook = new Rook(Color.Black);

    Movement move1 = new Movement(a1, b2);  // fail
    Movement move2 = new Movement(a1, f1);
    Movement move3 = new Movement(g6, d6);
    Movement move4 = new Movement(h8, a1);
    Movement move5 = new Movement(h1, a8);  // fail


    @Test
    public void t5() {
        try {
            assertTrue(rook.isTrajectoryValid(move5));
            Assert.fail();
        } catch (ChessException e) {
            // success
        }
    }

    @Test
    public void t4() {
        try {
            assertTrue(rook.isTrajectoryValid(move4));
            Assert.fail();
        } catch (ChessException e) {
            // success
        }
    }

    @Test
    public void t3() {
        try {
            assertTrue(rook.isTrajectoryValid(move3));
        } catch (ChessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void t2() {
        try {
            assertTrue(rook.isTrajectoryValid(move2));
        } catch (ChessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void t1() {
        try {
            assertTrue(rook.isTrajectoryValid(move1));
            Assert.fail();
        } catch (ChessException e) {
            // success
        }
    }
}
