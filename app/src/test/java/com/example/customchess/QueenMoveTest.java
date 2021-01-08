package com.example.customchess;

import com.example.customchess.engine.exceptions.ChessException;
import com.example.customchess.engine.figures.Queen;
import com.example.customchess.engine.misc.Color;
import com.example.customchess.engine.movements.Movement;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class QueenMoveTest extends FigureMoveTest {
    Queen queen = new Queen(Color.Black);

    Movement move1 = new Movement(a1, b2);
    Movement move2 = new Movement(a1, f1);
    Movement move3 = new Movement(g6, d6);
    Movement move4 = new Movement(h8, a1);
    Movement move5 = new Movement(a1, b2);
    Movement move6 = new Movement(a1, b3);  // fail
    Movement move7 = new Movement(h8, d4);
    Movement move8 = new Movement(h8, h2);  // fail
    Movement move9 = new Movement(h2, d6);
    Movement move10 = new Movement(d6, h2);
    Movement move11 = new Movement(d6, b3);  // fail

    @Test
    public void t10() {
        try {
            assertTrue(queen.isTrajectoryValid(move10));
        } catch (ChessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void t9() {
        try {
            assertTrue(queen.isTrajectoryValid(move9));
        } catch (ChessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void t7() {
        try {
            assertTrue(queen.isTrajectoryValid(move7));
        } catch (ChessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void t11() {
        try {
            assertTrue(queen.isTrajectoryValid(move11));
            Assert.fail();
        } catch (ChessException e) {
            // success
        }
    }

    @Test
    public void t8() {
        try {
            assertTrue(queen.isTrajectoryValid(move8));
        } catch (ChessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void t6() {
        try {
            assertTrue(queen.isTrajectoryValid(move6));
            Assert.fail();
        } catch (ChessException e) {
            // success
        }
    }

    @Test
    public void t5() {
        try {
            assertTrue(queen.isTrajectoryValid(move5));
        } catch (ChessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void t4() {
        try {
            assertTrue(queen.isTrajectoryValid(move4));
        } catch (ChessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void t3() {
        try {
            assertTrue(queen.isTrajectoryValid(move3));
        } catch (ChessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void t2() {
        try {
            assertTrue(queen.isTrajectoryValid(move2));
        } catch (ChessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void t1() {
        try {
            assertTrue(queen.isTrajectoryValid(move1));
        } catch (ChessException e) {
            e.printStackTrace();
        }
    }
}
