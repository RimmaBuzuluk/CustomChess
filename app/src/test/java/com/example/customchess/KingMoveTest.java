package com.example.customchess;

import com.example.customchess.engine.exceptions.ChessException;
import com.example.customchess.engine.figures.King;
import com.example.customchess.engine.misc.Color;
import com.example.customchess.engine.movements.Movement;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class KingMoveTest extends FigureMoveTest {
    King king = new King(Color.White);

    Movement move1 = new Movement(a1, b2);
    Movement move2 = new Movement(h1, g1);
    Movement move3 = new Movement(h2, h1);
    Movement move4 = new Movement(h2, f1);  // fail
    Movement move5 = new Movement(h3, h5);  // fail



    @Test
    public void t5() {
        try {
            assertTrue(king.isTrajectoryValid(move5));
            Assert.fail();
        } catch (ChessException e) {
            // success
        }
    }

    @Test
    public void t4() {
        try {
            assertTrue(king.isTrajectoryValid(move4));
            Assert.fail();
        } catch (ChessException e) {
            // success
        }
    }

    @Test
    public void t3() {
        try {
            assertTrue(king.isTrajectoryValid(move3));
        } catch (ChessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void t2() {
        try {
            assertTrue(king.isTrajectoryValid(move2));
        } catch (ChessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void t1() {
        try {
            assertTrue(king.isTrajectoryValid(move1));
        } catch (ChessException e) {
            e.printStackTrace();
        }
    }
}
