package com.example.customchess;

import com.example.customchess.engine.exceptions.ChessException;
import com.example.customchess.engine.figures.Pawn;
import com.example.customchess.engine.misc.Color;
import com.example.customchess.engine.movements.Movement;

import org.junit.Test;
import org.junit.Assert;

import static org.junit.Assert.*;

public class PawnMoveTest extends FigureMoveTest {
    Pawn white = new Pawn(Color.White);
    Pawn black = new Pawn(Color.Black);

    Movement move1 = new Movement(h7, h6);
    Movement move2 = new Movement(h2, h3);
    Movement move3 = new Movement(h7, h4);  // fail
    Movement move4 = new Movement(h2, h5);  // fail
    Movement move5 = new Movement(h7, h8);  // fail
    Movement move6 = new Movement(h2, h1);  // fail
    Movement move7 = new Movement(h7, h8);
    Movement move8 = new Movement(h2, h1);
    Movement move9 = new Movement(h7, h5);
    Movement move10 = new Movement(h5, h3); // fail


    @Test
    public void t9() {
        try {
            assertTrue(black.isTrajectoryValid(move9));
            assertTrue(black.isTrajectoryValid(move10));
            Assert.fail();
        } catch (ChessException e) {
            // success
        }
    }

    @Test
    public void t8() {
        try {
            assertTrue(black.isTrajectoryValid(move8));
        } catch (ChessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void t7() {
        try {
            assertTrue(white.isTrajectoryValid(move7));
        } catch (ChessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void t1() {
        try {
            assertTrue(black.isTrajectoryValid(move1));
        } catch (ChessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void t2() {
        try {
            assertTrue(white.isTrajectoryValid(move2));
        } catch (ChessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void t3() {
        try {
            assertTrue(black.isTrajectoryValid(move3));
            Assert.fail();
        } catch (ChessException e) {
            // success
        }
    }

    @Test
    public void t4() {
        try {
            assertTrue(white.isTrajectoryValid(move4));
            Assert.fail();
        } catch (ChessException e) {
            // success
        }
    }

    @Test
    public void t5() {
        try {
            assertTrue(black.isTrajectoryValid(move5));
            Assert.fail();
        } catch (ChessException e) {
            // success
        }
    }

    @Test
    public void t6() {
        try {
            assertTrue(white.isTrajectoryValid(move6));
            Assert.fail();
        } catch (ChessException e) {
            // success
        }
    }
}
