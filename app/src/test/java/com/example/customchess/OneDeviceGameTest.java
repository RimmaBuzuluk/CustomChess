package com.example.customchess;

import com.example.customchess.engine.OneDeviceGame;
import com.example.customchess.engine.exceptions.BeatFigureException;
import com.example.customchess.engine.exceptions.CastlingException;
import com.example.customchess.engine.exceptions.CheckMateException;
import com.example.customchess.engine.exceptions.ChessException;
import com.example.customchess.engine.exceptions.DrawException;
import com.example.customchess.engine.exceptions.MoveOnEmptyCageException;
import com.example.customchess.engine.exceptions.PawnEnPassantException;
import com.example.customchess.engine.exceptions.PromotionException;
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
                testGame.tryToMakeMovement(movement);
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

    public void BobbyFisherVSBorisSpassky() {
        gameMovements.add(new Movement(c2, c4));
        gameMovements.add(new Movement(e7, e6));

        gameMovements.add(new Movement(e1, f2));  // fail
        gameMovements.add(new Movement(e8, g8));  // fail
        gameMovements.add(new Movement(h2, g3));  // fail
        gameMovements.add(new Movement(e2, e5));  // fail
        gameMovements.add(new Movement(g1, e2));  // fail

        gameMovements.add(new Movement(g1, f3));
        gameMovements.add(new Movement(c8, d7));  // fail
        gameMovements.add(new Movement(e8, c8));  // fail
        gameMovements.add(new Movement(d7, d5));

        gameMovements.add(new Movement(d2, d4));
        gameMovements.add(new Movement(e8, g7));  // fail
        gameMovements.add(new Movement(d8, d4));  // fail
        gameMovements.add(new Movement(g8, f6));

        gameMovements.add(new Movement(e1, g1));  // fail
        gameMovements.add(new Movement(f3, f1));  // fail
        gameMovements.add(new Movement(f3, e1));  // fail
        gameMovements.add(new Movement(h4, e5));  // fail

        gameMovements.add(new Movement(b1, c3));
        gameMovements.add(new Movement(f8, e7));

        gameMovements.add(new Movement(c1, g5));
        gameMovements.add(new Movement(e8, g8));

        gameMovements.add(new Movement(e1, c1));  // fail

        gameMovements.add(new Movement(e2, e3));
        gameMovements.add(new Movement(h7, h6));

        gameMovements.add(new Movement(g5, h4));
        gameMovements.add(new Movement(b7, b6));

        gameMovements.add(new Movement(d4, d5));  // fail
        gameMovements.add(new Movement(g8, e8));  // fail

        gameMovements.add(new Movement(c4, d5));
        gameMovements.add(new Movement(f6, d5));

        gameMovements.add(new Movement(h4, e7));
        gameMovements.add(new Movement(d8, e7));

        gameMovements.add(new Movement(c3, d5));
        gameMovements.add(new Movement(e6, d5));

        gameMovements.add(new Movement(a1, c1));
        gameMovements.add(new Movement(c8, e6));

        gameMovements.add(new Movement(d1, a4));
        gameMovements.add(new Movement(c7, c5));

        gameMovements.add(new Movement(a4, a3));
        gameMovements.add(new Movement(f8, c8));

        gameMovements.add(new Movement(f1, b5));
        gameMovements.add(new Movement(a7, a6));

        gameMovements.add(new Movement(d4, c5));
        gameMovements.add(new Movement(b6, c5));

        gameMovements.add(new Movement(e1, g1));
        gameMovements.add(new Movement(a8, a7));

        gameMovements.add(new Movement(b5, e2));
        gameMovements.add(new Movement(b8, d7));

        gameMovements.add(new Movement(f3, d4));
        gameMovements.add(new Movement(e7, f8));

        gameMovements.add(new Movement(d4, e6));
        gameMovements.add(new Movement(f7, e6));

        gameMovements.add(new Movement(e3, e4));
        gameMovements.add(new Movement(d5, d4));

        gameMovements.add(new Movement(f2, f4));
        gameMovements.add(new Movement(f8, e7));

        gameMovements.add(new Movement(e4, e5));
        gameMovements.add(new Movement(c8, b8));

        gameMovements.add(new Movement(e2, c4));
        gameMovements.add(new Movement(g8, h8));

        gameMovements.add(new Movement(a3, h3));
        gameMovements.add(new Movement(d7, f8));

        gameMovements.add(new Movement(b2, b3));
        gameMovements.add(new Movement(a6, a5));

        gameMovements.add(new Movement(f4, f5));
        gameMovements.add(new Movement(e6, f5));

        gameMovements.add(new Movement(f1, f5));
        gameMovements.add(new Movement(f8, h7));

        gameMovements.add(new Movement(c1, f1));
        gameMovements.add(new Movement(e7, d8));

        gameMovements.add(new Movement(h3, g3));
        gameMovements.add(new Movement(a7, e7));

        gameMovements.add(new Movement(h2, h4));
        gameMovements.add(new Movement(b8, b7));

        gameMovements.add(new Movement(e5, e6));
        gameMovements.add(new Movement(b7, c7));

        gameMovements.add(new Movement(g3, e5));
        gameMovements.add(new Movement(d8, e8));

        gameMovements.add(new Movement(a2, a4));
        gameMovements.add(new Movement(e8, d8));

        gameMovements.add(new Movement(f1, f2));
        gameMovements.add(new Movement(d8, e8));

        gameMovements.add(new Movement(f2, f3));
        gameMovements.add(new Movement(e8, d8));

        gameMovements.add(new Movement(c4, d3));
        gameMovements.add(new Movement(d8, e8));

        gameMovements.add(new Movement(e5, e4));
        gameMovements.add(new Movement(h7, f6));

        gameMovements.add(new Movement(f5, f6));
        gameMovements.add(new Movement(g7, f6));

        gameMovements.add(new Movement(f3, f6));
        gameMovements.add(new Movement(h8, g8));

        gameMovements.add(new Movement(d3, c4));
        gameMovements.add(new Movement(g8, h8));

        gameMovements.add(new Movement(e4, f4));
        gameMovements.add(new Movement(h8, g8));

        gameMovements.add(new Movement(f4, h6));
        gameMovements.add(new Movement(d4, d3));

        gameMovements.add(new Movement(f6, g6));
        gameMovements.add(new Movement(e7, g7));

        gameMovements.add(new Movement(e6, e7));
        gameMovements.add(new Movement(e8, f7));

        gameMovements.add(new Movement(e7, e8));
    }

    @Test
    public void t2() {
        BobbyFisherVSBorisSpassky();
        int fail = 0;
        int ok   = 0;
        for (Movement movement : gameMovements) {
            try {
                testGame.tryToMakeMovement(movement);
            } catch (PromotionException pe) {
                ok++;
                testGame.promotion("Queen");
            } catch (MoveOnEmptyCageException
                    | BeatFigureException
                    | CastlingException
                    | PawnEnPassantException ce) {
                ok++;
                System.out.println(movement + " IS OK");
            } catch (ChessException ignored) {
                fail++;
                System.out.println(movement + " ---- FAILED");
            }
            try {
                testGame.checkForCheckMate();
            } catch (CheckMateException ignored) {
                System.out.println(" ---------------  CHECKMATE  --------------- ");
            }
        }
        boolean answer = false;
        try {
            testGame.checkForCheckMate();
        } catch (CheckMateException e) {
            answer = true;
        }
        System.out.println("\n ---------------  FAILS  : " + fail + "  --------------- ");
        System.out.println(  " ---------------  PASSED : " + ok +   "  --------------- ");
        assertTrue(answer);
    }

    public void pawnOnPassWithCheck() {
        gameMovements.add(new Movement(b2, b4));
        gameMovements.add(new Movement(e7, e5));

        gameMovements.add(new Movement(c1, a3));
        gameMovements.add(new Movement(e8, e7));

        gameMovements.add(new Movement(d1, c1));
        gameMovements.add(new Movement(e7, e6));

        gameMovements.add(new Movement(c1, b2));
        gameMovements.add(new Movement(c7, c5));

        gameMovements.add(new Movement(b2, b3));

        gameMovements.add(new Movement(b8, c6));  // fail
        gameMovements.add(new Movement(c5, b4));  // fail

        gameMovements.add(new Movement(c5, c4));

        gameMovements.add(new Movement(d2, d4));
        gameMovements.add(new Movement(c4, d3)); // fail
    }

    @Test
    public void t3() {
        pawnOnPassWithCheck();
        int fail = 0;
        int ok   = 0;
        for (Movement movement : gameMovements) {
            try {
                testGame.tryToMakeMovement(movement);
            } catch (PromotionException pe) {
                ok++;
                testGame.promotion("Queen");
            } catch (MoveOnEmptyCageException
                    | BeatFigureException
                    | CastlingException
                    | PawnEnPassantException ce) {
                ok++;
                System.out.println(movement + " IS OK");
            } catch (ChessException ignored) {
                fail++;
                System.out.println(movement + " ---- FAILED");
            }
            try {
                testGame.checkForCheckMate();
            } catch (CheckMateException ignored) {
                System.out.println(" ---------------  CHECKMATE  --------------- ");
            }
        }
        System.out.println("\n ---------------  FAILS  : " + fail + "  --------------- ");
        System.out.println(  " ---------------  PASSED : " + ok +   "  --------------- ");
        assertEquals(fail, 3);
    }

    public void gameStefanLevitskyVSFrankJamesMarshall1912() {
        gameMovements.add(new Movement(d2, d4));
        gameMovements.add(new Movement(e7, e6));

        gameMovements.add(new Movement(e2, e4));
        gameMovements.add(new Movement(d7, d5));

        gameMovements.add(new Movement(b1, c3));
        gameMovements.add(new Movement(c7, c5));

        gameMovements.add(new Movement(c3, e4));  // fail

        gameMovements.add(new Movement(g1, f3));
        gameMovements.add(new Movement(b8, c6));

        gameMovements.add(new Movement(e4, d5));
        gameMovements.add(new Movement(e6, d5));

        gameMovements.add(new Movement(d4, d3));  // fail

        gameMovements.add(new Movement(f1, e2));
        gameMovements.add(new Movement(g8, f6));

        gameMovements.add(new Movement(e1, c1));  // fail

        gameMovements.add(new Movement(e1, g1));
        gameMovements.add(new Movement(f8, e7));

        gameMovements.add(new Movement(c1, g5));
        gameMovements.add(new Movement(e8, g8));

        gameMovements.add(new Movement(d4, c5));
        gameMovements.add(new Movement(c8, e6));

        gameMovements.add(new Movement(f3, d4));
        gameMovements.add(new Movement(e7, c5));

        gameMovements.add(new Movement(d4, e6));
        gameMovements.add(new Movement(f7, e6));

        gameMovements.add(new Movement(e2, g4));
        gameMovements.add(new Movement(d8, d6));

        gameMovements.add(new Movement(g4, h3));
        gameMovements.add(new Movement(a8, e8));

        gameMovements.add(new Movement(d1, d2));
        gameMovements.add(new Movement(c5, b4));

        gameMovements.add(new Movement(g5, f6));
        gameMovements.add(new Movement(f8, f6));

        gameMovements.add(new Movement(a1, d1));
        gameMovements.add(new Movement(d6, c5));

        gameMovements.add(new Movement(d2, e2));
        gameMovements.add(new Movement(b4, c3));

        gameMovements.add(new Movement(b2, c3));
        gameMovements.add(new Movement(c5, c3));

        gameMovements.add(new Movement(d1, d5));
        gameMovements.add(new Movement(c6, d4));

        gameMovements.add(new Movement(e2, h5));
        gameMovements.add(new Movement(e8, f8));

        gameMovements.add(new Movement(d5, e5));
        gameMovements.add(new Movement(f6, h6));

        gameMovements.add(new Movement(h5, g5));
        gameMovements.add(new Movement(h6, h3));

        gameMovements.add(new Movement(e5, c5));
        gameMovements.add(new Movement(c3, g3));
//      -------  my moves  --------------------
        gameMovements.add(new Movement(f2, g3));
        gameMovements.add(new Movement(d4, e2));

        gameMovements.add(new Movement(g1, h1));
        gameMovements.add(new Movement(f8, f1));
    }

    @Test
    public void t4() {
        gameStefanLevitskyVSFrankJamesMarshall1912();
        int fail = 0;
        int ok   = 0;
        for (Movement movement : gameMovements) {
            try {
                testGame.tryToMakeMovement(movement);
            } catch (PromotionException pe) {
                testGame.promotion("Queen");
                ok++;
            } catch (MoveOnEmptyCageException
                    | BeatFigureException
                    | CastlingException
                    | PawnEnPassantException ce) {
                ok++;
                System.out.println(movement + " IS OK");
            } catch (ChessException ignored) {
                fail++;
                System.out.println(movement + " ---- FAILED");
            }
            try {
                testGame.checkForCheckMate();
            } catch (CheckMateException ignored) {
                System.out.println(" ---------------  CHECKMATE  --------------- ");
            }
        }
        boolean answer = false;
        try {
            testGame.checkForCheckMate();
        } catch (CheckMateException e) {
            answer = true;
        }
        System.out.println("\n ---------------  FAILS  : " + fail + "  --------------- ");
        System.out.println(  " ---------------  PASSED : " + ok +   "  --------------- ");
        assertTrue(answer);
    }

    public void pawnOnPassLogicCrashTest() {
        gameMovements.add(new Movement(d2, d4));
        gameMovements.add(new Movement(d7, d5));

        gameMovements.add(new Movement(e1, d2));
        gameMovements.add(new Movement(e7, e5));

        gameMovements.add(new Movement(d4, e5));
        gameMovements.add(new Movement(d5, d4));

        gameMovements.add(new Movement(e2, e4));
        gameMovements.add(new Movement(d4, e3));

        gameMovements.add(new Movement(e5, e6));  // fail
        gameMovements.add(new Movement(d2, d3));  // fail

        gameMovements.add(new Movement(d2, e1));
        gameMovements.add(new Movement(c7, c5));

        gameMovements.add(new Movement(f1, d3));
        gameMovements.add(new Movement(b7, b6));

        gameMovements.add(new Movement(g1, h3));
        gameMovements.add(new Movement(b8, c6));

        gameMovements.add(new Movement(e1, g1));  // fail

        gameMovements.add(new Movement(d3, b5));

        gameMovements.add(new Movement(c6, e5));  // fail
    }

    @Test
    public void t5() {
        pawnOnPassLogicCrashTest();
        int fail = 0;
        int ok   = 0;
        for (Movement movement : gameMovements) {
            try {
                testGame.tryToMakeMovement(movement);
            } catch (PromotionException pe) {
                testGame.promotion("Queen");
                ok++;
            } catch (MoveOnEmptyCageException
                    | BeatFigureException
                    | CastlingException
                    | PawnEnPassantException ce) {
                ok++;
                System.out.println(movement + " IS OK");
            } catch (ChessException ignored) {
                fail++;
                System.out.println(movement + " ---- FAILED");
            }
            try {
                testGame.checkForCheckMate();
            } catch (CheckMateException ignored) {
                System.out.println(" ---------------  CHECKMATE  --------------- ");
            }
        }
        boolean answer = false;
        try {
            testGame.checkForCheckMate();
        } catch (CheckMateException e) {
            answer = true;
        }
        System.out.println("\n ---------------  FAILS  : " + fail + "  --------------- ");
        System.out.println(  " ---------------  PASSED : " + ok +   "  --------------- ");
        assertFalse(answer);
    }

    @Test
    public void t5p1() {
        pawnOnPassLogicCrashTest();
        int fail = 0;
        int ok   = 0;
        for (Movement movement : gameMovements) {
            try {
                testGame.tryToMakeMovement(movement);
            } catch (PromotionException pe) {
                testGame.promotion("Queen");
                ok++;
            } catch (MoveOnEmptyCageException
                    | BeatFigureException
                    | CastlingException
                    | PawnEnPassantException ce) {
                ok++;
                System.out.println(movement + " IS OK");
            } catch (ChessException ignored) {
                fail++;
                System.out.println(movement + " ---- FAILED");
            }
            try {
                testGame.checkForCheckMate();
            } catch (CheckMateException ignored) {
                System.out.println(" ---------------  CHECKMATE  --------------- ");
            }
        }
        System.out.println("\n ---------------  FAILS  : " + fail + "  --------------- ");
        System.out.println(  " ---------------  PASSED : " + ok +   "  --------------- ");
        assertEquals(fail, 4);
    }

    public void castlingLogicCrashTest1() {
        gameMovements.add(new Movement(g2, g4)); // w
        gameMovements.add(new Movement(d7, d5)); // b
        gameMovements.add(new Movement(f1, h3)); // w
        gameMovements.add(new Movement(c8, e6)); // b
        gameMovements.add(new Movement(e1, g1)); // fail
        gameMovements.add(new Movement(g1, f3)); // w
        gameMovements.add(new Movement(e8, c8)); // fail
        gameMovements.add(new Movement(d8, d6)); // b
        gameMovements.add(new Movement(h3, f1)); // w
        gameMovements.add(new Movement(e8, c8)); // fail
        gameMovements.add(new Movement(b8, c6)); // b
        gameMovements.add(new Movement(e1, g1)); // fail
        gameMovements.add(new Movement(f1, h3)); // w
        gameMovements.add(new Movement(d6, d8)); // b
        gameMovements.add(new Movement(e1, g1)); // w
        gameMovements.add(new Movement(e8, c8)); // fail
        gameMovements.add(new Movement(d8, d6)); // b
        gameMovements.add(new Movement(g4, g5)); // w
        gameMovements.add(new Movement(e8, c8)); // b
    }

    @Test
    public void t6() {
        castlingLogicCrashTest1();
        int fail = 0;
        int ok   = 0;
        for (Movement movement : gameMovements) {
            try {
                testGame.tryToMakeMovement(movement);
            } catch (PromotionException pe) {
                testGame.promotion("Queen");
                ok++;
            } catch (MoveOnEmptyCageException
                    | BeatFigureException
                    | CastlingException
                    | PawnEnPassantException ce) {
                ok++;
                System.out.println(movement + " IS OK");
            } catch (ChessException ignored) {
                fail++;
                System.out.println(movement + " ---- FAILED");
            }
            try {
                testGame.checkForCheckMate();
            } catch (CheckMateException ignored) {
                System.out.println(" ---------------  CHECKMATE  --------------- ");
            }
        }
        System.out.println("\n ---------------  FAILS  : " + fail + "  --------------- ");
        System.out.println(  " ---------------  PASSED : " + ok +   "  --------------- ");
        assertEquals(fail, 5);
    }

    public void castlingLogicCrashTest2() {
        gameMovements.add(new Movement(g2, g4));
        gameMovements.add(new Movement(g7, g5));

        gameMovements.add(new Movement(f2, f4));
        gameMovements.add(new Movement(f7, f5));

        gameMovements.add(new Movement(g4, f5));
        gameMovements.add(new Movement(e7, e6));

        gameMovements.add(new Movement(f5, e6));
        gameMovements.add(new Movement(g5, f4));

        gameMovements.add(new Movement(e2, e3));
        gameMovements.add(new Movement(d8, f6));

        gameMovements.add(new Movement(e3, f4));
        gameMovements.add(new Movement(f6, f4));

        gameMovements.add(new Movement(f1, h3));
        gameMovements.add(new Movement(f4, g4));

        gameMovements.add(new Movement(g1, e2));
        gameMovements.add(new Movement(d7, d5));
        gameMovements.add(new Movement(e1, g1)); // fail
        gameMovements.add(new Movement(e2, d4));
        gameMovements.add(new Movement(g4, f4));

        gameMovements.add(new Movement(e1, g1)); // fail
        gameMovements.add(new Movement(e1, f1)); // fail

        gameMovements.add(new Movement(d4, b5));
        gameMovements.add(new Movement(f4, e4));

        gameMovements.add(new Movement(e1, g1)); // fail

        gameMovements.add(new Movement(d1, e2));
        gameMovements.add(new Movement(c7, c5));

        gameMovements.add(new Movement(e1, g1));
        gameMovements.add(new Movement(e4, h4));

        gameMovements.add(new Movement(b5, a3));
        gameMovements.add(new Movement(b7, b5));

        gameMovements.add(new Movement(e8, d8)); // fail

        gameMovements.add(new Movement(a3, b5));
        gameMovements.add(new Movement(e8, d8));
    }

    @Test
    public void t7() {
        castlingLogicCrashTest2();
        int fail = 0;
        int ok   = 0;
        for (Movement movement : gameMovements) {
            try {
                testGame.tryToMakeMovement(movement);
            } catch (PromotionException pe) {
                testGame.promotion("Queen");
                ok++;
            } catch (MoveOnEmptyCageException
                    | BeatFigureException
                    | CastlingException
                    | PawnEnPassantException ce) {
                ok++;
                System.out.println(movement + " IS OK");
            } catch (ChessException ignored) {
                fail++;
                System.out.println(movement + " ---- FAILED");
            }
            try {
                testGame.checkForCheckMate();
            } catch (CheckMateException ignored) {
                System.out.println(" ---------------  CHECKMATE  --------------- ");
            }
        }
        System.out.println("\n ---------------  FAILS  : " + fail + "  --------------- ");
        System.out.println(  " ---------------  PASSED : " + ok +   "  --------------- ");
        assertEquals(fail, 5);
    }

    public void castlingLogicCrashTest3() {
        gameMovements.add(new Movement(g2, g4));
        gameMovements.add(new Movement(g7, g5));

        gameMovements.add(new Movement(f2, f4));
        gameMovements.add(new Movement(f7, f5));

        gameMovements.add(new Movement(g4, f5));
        gameMovements.add(new Movement(e7, e6));

        gameMovements.add(new Movement(f5, e6));
        gameMovements.add(new Movement(g5, f4));

        gameMovements.add(new Movement(e2, e3));
        gameMovements.add(new Movement(d8, f6));

        gameMovements.add(new Movement(e3, f4));
        gameMovements.add(new Movement(f6, f4));

        gameMovements.add(new Movement(f1, h3));
        gameMovements.add(new Movement(f4, g4));

        gameMovements.add(new Movement(g1, e2));
        gameMovements.add(new Movement(d7, d5));
        gameMovements.add(new Movement(e1, g1)); // fail
        gameMovements.add(new Movement(e2, d4));
        gameMovements.add(new Movement(g4, f4));

        gameMovements.add(new Movement(e1, g1)); // fail
        gameMovements.add(new Movement(e1, f1)); // fail

        gameMovements.add(new Movement(d4, b5));
        gameMovements.add(new Movement(f4, e4));

        gameMovements.add(new Movement(e1, g1)); // fail

        gameMovements.add(new Movement(d1, e2));
        gameMovements.add(new Movement(c7, c5));

        gameMovements.add(new Movement(e1, g1));
        gameMovements.add(new Movement(e4, h4));

        gameMovements.add(new Movement(e2, e5));
        gameMovements.add(new Movement(b8, a6));

        gameMovements.add(new Movement(b5, c7));

        gameMovements.add(new Movement(e8, d7)); // fail

        gameMovements.add(new Movement(a6, c7));
        gameMovements.add(new Movement(e5, d6));

        gameMovements.add(new Movement(c8, e6));
        gameMovements.add(new Movement(e8, c8)); // fail
        gameMovements.add(new Movement(d2, d3));
        gameMovements.add(new Movement(a7, a6));

        gameMovements.add(new Movement(d6, c6));
        gameMovements.add(new Movement(e8, c8)); // fail
        gameMovements.add(new Movement(e6, d7));

        gameMovements.add(new Movement(b2, b3));
        gameMovements.add(new Movement(c7, b5));

        gameMovements.add(new Movement(a2, a4));
        gameMovements.add(new Movement(e8, c8)); // fail
        gameMovements.add(new Movement(e8, d8));

        gameMovements.add(new Movement(c6, a6));
        gameMovements.add(new Movement(d8, e8));

        gameMovements.add(new Movement(d3, d4));
        gameMovements.add(new Movement(e8, c8)); // fail
    }

    @Test
    public void t8() {
        castlingLogicCrashTest3();
        int fail = 0;
        int ok   = 0;
        for (Movement movement : gameMovements) {
            try {
                testGame.tryToMakeMovement(movement);
            } catch (PromotionException pe) {
                testGame.promotion("Queen");
                ok++;
            } catch (MoveOnEmptyCageException
                    | BeatFigureException
                    | CastlingException
                    | PawnEnPassantException ce) {
                ok++;
                System.out.println(movement + " IS OK");
            } catch (ChessException ignored) {
                fail++;
                System.out.println(movement + " ---- FAILED");
            }
            try {
                testGame.checkForCheckMate();
            } catch (CheckMateException ignored) {
                System.out.println(" ---------------  CHECKMATE  --------------- ");
            }
        }
        System.out.println("\n ---------------  FAILS  : " + fail + "  --------------- ");
        System.out.println(  " ---------------  PASSED : " + ok +   "  --------------- ");
        assertEquals(fail, 9);
    }

    public void castlingLogicCrashTest4() {
        gameMovements.add(new Movement(f2, f4));
        gameMovements.add(new Movement(f7, f6));

        gameMovements.add(new Movement(f4, f5));
        gameMovements.add(new Movement(e8, f7));

        gameMovements.add(new Movement(e2, e4));
        gameMovements.add(new Movement(g7, g5));

        gameMovements.add(new Movement(f5, g6));
        gameMovements.add(new Movement(d7, d5)); // fail
        gameMovements.add(new Movement(f7, e8));
    }

    @Test
    public void t9() {
        castlingLogicCrashTest4();
        int fail = 0;
        int ok   = 0;
        for (Movement movement : gameMovements) {
            try {
                testGame.tryToMakeMovement(movement);
            } catch (PromotionException pe) {
                testGame.promotion("Queen");
                ok++;
            } catch (MoveOnEmptyCageException
                    | BeatFigureException
                    | CastlingException
                    | PawnEnPassantException ce) {
                ok++;
                System.out.println(movement + " IS OK");
            } catch (ChessException ignored) {
                fail++;
                System.out.println(movement + " ---- FAILED");
            }
            try {
                testGame.checkForCheckMate();
            } catch (CheckMateException ignored) {
                System.out.println(" ---------------  CHECKMATE  --------------- ");
            }
        }
        System.out.println("\n ---------------  FAILS  : " + fail + "  --------------- ");
        System.out.println(  " ---------------  PASSED : " + ok +   "  --------------- ");
        assertEquals(fail, 1);
    }

    public void gameInitShortestDraw() {
        gameMovements.add(new Movement(e2, e3));
        gameMovements.add(new Movement(a7, a5));

        gameMovements.add(new Movement(d1, h5));
        gameMovements.add(new Movement(a8, a6));

        gameMovements.add(new Movement(h5, a5));
        gameMovements.add(new Movement(h7, h5));

        gameMovements.add(new Movement(a5, c7));
        gameMovements.add(new Movement(a6, h6));

        gameMovements.add(new Movement(h2, h4));
        gameMovements.add(new Movement(f7, f6));

        gameMovements.add(new Movement(c7, d7));
        gameMovements.add(new Movement(e8, f7));

        gameMovements.add(new Movement(d7, b7));
        gameMovements.add(new Movement(d8, d3));

        gameMovements.add(new Movement(b7, b8));
        gameMovements.add(new Movement(d3, h7));

        gameMovements.add(new Movement(b8, c8));
        gameMovements.add(new Movement(f7, g6));

        gameMovements.add(new Movement(c8, e6));
    }

    @Test
    public void t10() {
        gameInitShortestDraw();
        int fail = 0;
        int ok   = 0;
        for (Movement movement : gameMovements) {
            try {
                testGame.tryToMakeMovement(movement);
            } catch (PromotionException pe) {
                testGame.promotion("Queen");
                ok++;
            } catch (MoveOnEmptyCageException
                    | BeatFigureException
                    | CastlingException
                    | PawnEnPassantException ce) {
                ok++;
                System.out.println(movement + " IS OK");
            } catch (ChessException ignored) {
                fail++;
                System.out.println(movement + " ---- FAILED");
            }
            try {
                testGame.checkForCheckMate();
            } catch (CheckMateException ignored) {
                System.out.println(" ---------------  CHECKMATE  --------------- ");
            }
        }
        System.out.println("\n ---------------  FAILS  : " + fail + "  --------------- ");
        System.out.println(  " ---------------  PASSED : " + ok +   "  --------------- ");
        try {
            testGame.checkForPat();
            Assert.fail();
        } catch (DrawException e) {
            System.out.println(" -------------------  DRAW  -------------------------- ");
        }
    }


}
