package com.example.customchess;

import com.example.customchess.engine.exceptions.ChessException;
import com.example.customchess.engine.figures.Bishop;
import com.example.customchess.engine.misc.Color;
import com.example.customchess.engine.movements.Movement;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class BishopFigureTest extends FigureMoveTest {
    Bishop bishop = new Bishop(Color.Black);

    Movement move1 = new Movement(a1, b2);
    Movement move2 = new Movement(a1, b3);  // fail
    Movement move3 = new Movement(h8, d4);
    Movement move4 = new Movement(h8, h2);  // fail
    Movement move5 = new Movement(h2, d6);
    Movement move6 = new Movement(d6, h2);
    Movement move7 = new Movement(d6, b3);  // fail
    Movement move8 = new Movement(h1, a8);

    @Test
    public void t8() {
        try {
            assertTrue(bishop.isTrajectoryValid(move8));
        } catch (ChessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void t7() {
        try {
            assertTrue(bishop.isTrajectoryValid(move7));
            Assert.fail();
        } catch (ChessException e) {
            // success
        }
    }

    @Test
    public void t6() {
        try {
            assertTrue(bishop.isTrajectoryValid(move6));
        } catch (ChessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void t5() {
        try {
            assertTrue(bishop.isTrajectoryValid(move5));
        } catch (ChessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void t3() {
        try {
            assertTrue(bishop.isTrajectoryValid(move3));
        } catch (ChessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void t1() {
        try {
            assertTrue(bishop.isTrajectoryValid(move1));
        } catch (ChessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void t2() {
        try {
            assertTrue(bishop.isTrajectoryValid(move2));
            Assert.fail();
        } catch (ChessException e) {
            // success
        }
    }

    @Test
    public void t4() {
        try {
            assertTrue(bishop.isTrajectoryValid(move4));
            Assert.fail();
        } catch (ChessException e) {
            // success
        }
    }

}
