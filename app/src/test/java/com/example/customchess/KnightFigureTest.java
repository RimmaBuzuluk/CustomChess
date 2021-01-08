package com.example.customchess;

import com.example.customchess.engine.exceptions.ChessException;
import com.example.customchess.engine.figures.Knight;
import com.example.customchess.engine.misc.Color;
import com.example.customchess.engine.movements.Movement;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class KnightFigureTest extends FigureMoveTest {
    Knight knight = new Knight(Color.White);

    Movement move1 = new Movement(a1, b3);
    Movement move2 = new Movement(a1, b2);  // fail
    Movement move3 = new Movement(f1, h2);
    Movement move4 = new Movement(g8, e7);
    Movement move5 = new Movement(g8, g6);  // fail

    @Test
    public void t4() {
        try {
            assertTrue(knight.isTrajectoryValid(move4));
        } catch (ChessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void t1() {
        try {
            assertTrue(knight.isTrajectoryValid(move1));
        } catch (ChessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void t3() {
        try {
            assertTrue(knight.isTrajectoryValid(move3));
        } catch (ChessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void t2() {
        try {
            knight.isTrajectoryValid(move2);
            Assert.fail();
        } catch (ChessException ex) {
            // success
        }
    }

    @Test
    public void t5() {
        try {
            knight.isTrajectoryValid(move5);
            Assert.fail();
        } catch (ChessException ex) {
            // success
        }
    }

}