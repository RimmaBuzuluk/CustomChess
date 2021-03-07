package com.example.customchess;

import com.example.customchess.engine.Board;
import com.example.customchess.engine.EndGameChecker;
import com.example.customchess.engine.figures.Bishop;
import com.example.customchess.engine.figures.King;
import com.example.customchess.engine.figures.Knight;
import com.example.customchess.engine.figures.Pawn;
import com.example.customchess.engine.figures.Piece;
import com.example.customchess.engine.figures.Queen;
import com.example.customchess.engine.figures.Rook;
import com.example.customchess.engine.misc.Color;
import com.example.customchess.engine.movements.Position;

import org.junit.Test;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EndGameCheckerTest extends FigureMoveTest {

    Hashtable<Position, Piece> blackTeam = new Hashtable<>();
    Hashtable<Position, Piece> whiteTeam = new Hashtable<>();
    Board board = new Board(blackTeam, whiteTeam);
    EndGameChecker gameChecker;

    public void init1() {
        whiteTeam.put(h8, new King(Color.White));
        whiteTeam.put(h5, new Pawn(Color.White));
        whiteTeam.put(g8, new Pawn(Color.White));
        blackTeam.put(g6, new Knight(Color.Black));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board);
    }

    public void init2() {
        whiteTeam.put(h8, new King(Color.White));
        blackTeam.put(f6, new Queen(Color.Black));
        blackTeam.put(f8, new Rook(Color.Black));
        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board);
    }

    public void init3() {
        whiteTeam.put(g7, new King(Color.White));
        blackTeam.put(h4, new Queen(Color.Black));
        blackTeam.put(f8, new Rook(Color.Black));
        blackTeam.put(h8, new Rook(Color.Black));
        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board);
    }

    public void init4() {
        whiteTeam.put(h1, new King(Color.White));
        blackTeam.put(g1, new Queen(Color.Black));
        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board);
    }

    public void init6() {
        whiteTeam.put(h1, new King(Color.White));
        whiteTeam.put(g2, new Rook(Color.White));
        blackTeam.put(e1, new Queen(Color.Black));
        blackTeam.put(h4, new Rook(Color.Black));
        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board);
    }

    @Test
    public void t1() {
        init1();
        List<Position> cagesAroundKing = gameChecker.getEmptyPositionsAround(h8);
        boolean fuck = gameChecker.isCageToMoveKingAway(cagesAroundKing, h8);
        assertTrue(fuck);
    }

    @Test
    public void t2() {
        init2();
        List<Position> cagesAroundKing = gameChecker.getEmptyPositionsAround(h8);
        boolean fuck = gameChecker.isCageToMoveKingAway(cagesAroundKing, h8);
        assertTrue(fuck);
    }

    @Test
    public void t3() {
        init3();
        List<Position> cagesAroundKing = gameChecker.getEmptyPositionsAround(g7);
        boolean fuck = gameChecker.isCageToMoveKingAway(cagesAroundKing, g7);
        assertTrue(fuck);
    }

    @Test
    public void t4() {
        init1();
        boolean fuck = gameChecker.isFigureToBeatAttackingPiece(Color.White, g6);
        assertTrue(fuck);
    }

    @Test
    public void t5() {
        init4();
        boolean fuck = gameChecker.isFigureToBeatAttackingPiece(Color.White, g1);
        assertTrue(fuck);
    }

    @Test
    public void t6() {
        init6();
        boolean fuck = gameChecker.isPieceToCoverKingFromCheck(Color.White, h1, e1);
        assertFalse(fuck);
    }


    public void init5() {
        whiteTeam.put(h1, new King(Color.White));
        whiteTeam.put(g2, new Rook(Color.White));
        blackTeam.put(e1, new Queen(Color.Black));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board);
    }

    @Test
    public void tIsFigureToCover1() {
        init5();
        assertTrue(gameChecker.isPieceToCoverKingFromCheck(Color.White, h1, e1));
    }

    public void init27() {
        whiteTeam.put(f6, new King(Color.White));
        whiteTeam.put(e6, new Pawn(Color.White));
        blackTeam.put(d8, new Bishop(Color.Black));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board);
    }

    @Test
    public void t27() {
        init27();
        assertTrue(gameChecker.isPieceToCoverKingFromCheck(Color.White, f6, d8));
    }

    public void init28() {
        whiteTeam.put(f6, new King(Color.White));
        whiteTeam.put(e6, new Pawn(Color.White));
        blackTeam.put(g8, new Knight(Color.Black));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board);
    }

    @Test
    public void t28() {
        init28();
        assertFalse(gameChecker.isPieceToCoverKingFromCheck(Color.White, f6, g8));
    }

    public void init29() {
        whiteTeam.put(f6, new King(Color.White));
        whiteTeam.put(f5, new Knight(Color.White));
        blackTeam.put(e7, new Pawn(Color.Black));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board);
    }

    @Test
    public void t29() {
        init29();
        assertTrue(gameChecker.isFigureToBeatAttackingPiece(Color.White, e7));
    }

    public void init30() {
        whiteTeam.put(d1, new King(Color.White));
        whiteTeam.put(g1, new Bishop(Color.White));
        blackTeam.put(h1, new Rook(Color.Black));
        blackTeam.put(d4, new Queen(Color.Black));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board);
    }

    @Test
    public void t30() {
        init30();
        assertFalse(gameChecker.isFigureToBeatAttackingPiece(Color.White, d4));
    }

//  ----------- checkmate tests --------------------------------------------------------------------
    public void init7() {
        whiteTeam.put(h1, new King(Color.White));
        whiteTeam.put(h2, new Pawn(Color.White));
        whiteTeam.put(g2, new Pawn(Color.White));
        blackTeam.put(e1, new Queen(Color.Black));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board);
    }

    @Test
    public void t7() {
        init7();
        assertTrue(gameChecker.isCheckMate(Color.White));
    }

    public void init8() {
        whiteTeam.put(h1, new King(Color.White));
        whiteTeam.put(h2, new Pawn(Color.White));
        whiteTeam.put(g2, new Pawn(Color.White));
        whiteTeam.put(g1, new Rook(Color.White));
        blackTeam.put(f2, new Knight(Color.Black));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board);
    }

    @Test
    public void t8() {
        init8();
        assertTrue(gameChecker.isCheckMate(Color.White));
    }

    public void init9() {
        whiteTeam.put(a1, new King(Color.White));
        blackTeam.put(b4, new Knight(Color.Black));
        blackTeam.put(b3, new Pawn(Color.Black));
        blackTeam.put(f6, new Bishop(Color.Black));
        blackTeam.put(g6, new Bishop(Color.Black));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board);
    }

    @Test
    public void t9() {
        init9();
        boolean res = gameChecker.isCheckMate(Color.White);
        assertTrue(res);
    }

    public void init10() {
        whiteTeam.put(a1, new King(Color.White));
        blackTeam.put(b4, new Knight(Color.Black));
        blackTeam.put(b4, new Knight(Color.Black));
        blackTeam.put(f6, new Bishop(Color.Black));
        blackTeam.put(g6, new Bishop(Color.Black));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board);
    }

    @Test
    public void t10() {
        init10();
        assertTrue(gameChecker.isCheckMate(Color.White));
    }

    public void init11() {
        whiteTeam.put(c6, new King(Color.White));
        whiteTeam.put(b7, new Queen(Color.White));
        blackTeam.put(b8, new King(Color.Black));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board);
    }

    @Test
    public void t11() {
        init11();
        assertTrue(gameChecker.isCheckMate(Color.Black));
    }

    public void init12() {
        whiteTeam.put(h7, new Queen(Color.White));
        whiteTeam.put(d3, new Bishop(Color.White));
        whiteTeam.put(a3, new Pawn(Color.White));
        whiteTeam.put(b2, new Pawn(Color.White));
        whiteTeam.put(c1, new Rook(Color.White));
        whiteTeam.put(f1, new Rook(Color.White));
        whiteTeam.put(g1, new King(Color.White));
        whiteTeam.put(f2, new Pawn(Color.White));
        whiteTeam.put(g2, new Pawn(Color.White));
        whiteTeam.put(h2, new Pawn(Color.White));
        blackTeam.put(h8, new King(Color.Black));
        blackTeam.put(a8, new Rook(Color.Black));
        blackTeam.put(f8, new Rook(Color.Black));
        blackTeam.put(b7, new Pawn(Color.Black));
        blackTeam.put(g7, new Pawn(Color.Black));
        blackTeam.put(f7, new Pawn(Color.Black));
        blackTeam.put(a6, new Pawn(Color.Black));
        blackTeam.put(b6, new Queen(Color.Black));
        blackTeam.put(f6, new Bishop(Color.Black));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board);
    }

    @Test
    public void t12() {
        init12();
        assertTrue(gameChecker.isCheckMate(Color.Black));
    }

    public void init13() {
        whiteTeam.put(g7, new Queen(Color.White));
        whiteTeam.put(f6, new Pawn(Color.White));
        whiteTeam.put(a3, new Pawn(Color.White));
        whiteTeam.put(b2, new Pawn(Color.White));
        whiteTeam.put(g2, new Pawn(Color.White));
        whiteTeam.put(h2, new Pawn(Color.White));
        whiteTeam.put(h1, new King(Color.White));
        whiteTeam.put(c1, new Rook(Color.White));
        whiteTeam.put(f1, new Rook(Color.White));

        blackTeam.put(g8, new King(Color.Black));
        blackTeam.put(f7, new Pawn(Color.Black));
        blackTeam.put(g6, new Pawn(Color.Black));
        blackTeam.put(h7, new Pawn(Color.Black));
        blackTeam.put(b7, new Pawn(Color.Black));
        blackTeam.put(a6, new Pawn(Color.Black));
        blackTeam.put(b6, new Queen(Color.Black));
        blackTeam.put(a8, new Rook(Color.Black));
        blackTeam.put(d8, new Rook(Color.Black));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board);
    }

    @Test
    public void t13() {
        init13();
        assertTrue(gameChecker.isCheckMate(Color.Black));
    }

    public void init14() {
        whiteTeam.put(d6, new King(Color.White));
        whiteTeam.put(g8, new Rook(Color.White));
        blackTeam.put(d8, new King(Color.Black));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board);
    }

    @Test
    public void t14() {
        init14();
        assertTrue(gameChecker.isCheckMate(Color.Black));
    }

    public void init15() {
        whiteTeam.put(g1, new King(Color.White));
        whiteTeam.put(g7, new Rook(Color.White));
        whiteTeam.put(d7, new Queen(Color.White));
        whiteTeam.put(d6, new Bishop(Color.White));
        blackTeam.put(e8, new King(Color.Black));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board);
    }

    @Test
    public void t15() {
        init15();
        assertTrue(gameChecker.isCheckMate(Color.Black));
    }

    public void init16() {
        whiteTeam.put(g1, new King(Color.White));
        whiteTeam.put(c8, new Queen(Color.White));
        whiteTeam.put(a3, new Pawn(Color.White));
        whiteTeam.put(b4, new Pawn(Color.White));
        whiteTeam.put(f2, new Pawn(Color.White));
        whiteTeam.put(g2, new Pawn(Color.White));
        whiteTeam.put(h3, new Pawn(Color.White));
        whiteTeam.put(e3, new Bishop(Color.White));
        blackTeam.put(g8, new King(Color.Black));
        blackTeam.put(g6, new Bishop(Color.Black));
        blackTeam.put(e2, new Queen(Color.Black));
        blackTeam.put(h7, new Pawn(Color.Black));
        blackTeam.put(g7, new Pawn(Color.Black));
        blackTeam.put(f7, new Pawn(Color.Black));
        blackTeam.put(b5, new Pawn(Color.Black));
        blackTeam.put(c4, new Pawn(Color.Black));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board);
    }

    @Test
    public void t16() {
        init16();
        assertTrue(gameChecker.isCheckMate(Color.Black));
    }

    public void init17() {
        whiteTeam.put(g1, new King(Color.White));
        whiteTeam.put(e5, new Bishop(Color.White));
        whiteTeam.put(a3, new Pawn(Color.White));
        whiteTeam.put(b2, new Pawn(Color.White));
        whiteTeam.put(f2, new Pawn(Color.White));
        whiteTeam.put(g2, new Pawn(Color.White));
        whiteTeam.put(h3, new Pawn(Color.White));
        whiteTeam.put(d8, new Rook(Color.White));
        blackTeam.put(h8, new King(Color.Black));
        blackTeam.put(g8, new Rook(Color.Black));
        blackTeam.put(c5, new Bishop(Color.Black));
        blackTeam.put(g6, new Pawn(Color.Black));
        blackTeam.put(h7, new Pawn(Color.Black));
        blackTeam.put(b6, new Pawn(Color.Black));
        blackTeam.put(a4, new Pawn(Color.Black));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board);
    }

    @Test
    public void t17() {
        init17();
        assertTrue(gameChecker.isCheckMate(Color.Black));
    }

    public void init18() {
        whiteTeam.put(g1, new King(Color.White));
        whiteTeam.put(a3, new Pawn(Color.White));
        whiteTeam.put(b2, new Pawn(Color.White));
        whiteTeam.put(f2, new Pawn(Color.White));
        whiteTeam.put(g2, new Pawn(Color.White));
        whiteTeam.put(g7, new Pawn(Color.White));
        whiteTeam.put(d8, new Rook(Color.White));
        whiteTeam.put(f5, new Knight(Color.White));
        blackTeam.put(h8, new King(Color.Black));
        blackTeam.put(g8, new Rook(Color.Black));
        blackTeam.put(c5, new Bishop(Color.Black));
        blackTeam.put(h7, new Pawn(Color.Black));
        blackTeam.put(b6, new Pawn(Color.Black));
        blackTeam.put(a4, new Pawn(Color.Black));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board);
    }

    @Test
    public void t18() {
        init18();
        assertTrue(gameChecker.isCheckMate(Color.Black));
    }

    public void init19() {
        whiteTeam.put(h1, new King(Color.White));
        blackTeam.put(a8, new King(Color.Black));
        blackTeam.put(h3, new Queen(Color.Black));
        blackTeam.put(e1, new Rook(Color.Black));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board);
    }

    @Test
    public void t19() {
        init19();
        assertTrue(gameChecker.isCheckMate(Color.White));
    }

    public void init20() {
        whiteTeam.put(h1, new King(Color.White));
        blackTeam.put(a8, new King(Color.Black));
        blackTeam.put(h4, new Queen(Color.Black));
        blackTeam.put(e1, new Rook(Color.Black));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board);
    }

    @Test
    public void t20() {
        init20();
        assertFalse(gameChecker.isCheckMate(Color.White));
    }

    public void init21() {
        whiteTeam.put(h1, new King(Color.White));
        blackTeam.put(a8, new King(Color.Black));
        blackTeam.put(h2, new Queen(Color.Black));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board);
    }

    @Test
    public void t21() {
        init21();
        assertFalse(gameChecker.isCheckMate(Color.White));
    }

    public void init22() {
        whiteTeam.put(e4, new King(Color.White));
        whiteTeam.put(e5, new Queen(Color.White));
        blackTeam.put(e8, new King(Color.Black));
        blackTeam.put(d8, new Rook(Color.Black));
        blackTeam.put(f8, new Rook(Color.Black));
        blackTeam.put(d7, new Pawn(Color.Black));
        blackTeam.put(f7, new Pawn(Color.Black));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board);
    }

    @Test
    public void t22() {
        init22();
        assertTrue(gameChecker.isCheckMate(Color.Black));
    }

    public void init23() {
        whiteTeam.put(e4, new King(Color.White));
        whiteTeam.put(e6, new Queen(Color.White));
        blackTeam.put(e8, new King(Color.Black));
        blackTeam.put(d8, new Rook(Color.Black));
        blackTeam.put(f8, new Rook(Color.Black));
        blackTeam.put(d7, new Pawn(Color.Black));
        blackTeam.put(f7, new Pawn(Color.Black));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board);
    }

    @Test
    public void t23() {
        init23();
        assertFalse(gameChecker.isCheckMate(Color.Black));
    }

    public void init24() {
        whiteTeam.put(b6, new King(Color.White));
        whiteTeam.put(d5, new Bishop(Color.White));
        whiteTeam.put(d6, new Bishop(Color.White));
        blackTeam.put(a8, new King(Color.Black));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board);
    }

    @Test
    public void t24() {
        init24();
        assertTrue(gameChecker.isCheckMate(Color.Black));
    }

    public void init25() {
        whiteTeam.put(c7, new King(Color.White));
        whiteTeam.put(b7, new Bishop(Color.White));
        whiteTeam.put(c6, new Knight(Color.White));
        blackTeam.put(a8, new King(Color.Black));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board);
    }

    @Test
    public void t25() {
        init25();
        assertTrue(gameChecker.isCheckMate(Color.Black));
    }

    public void init26() {
        whiteTeam.put(c7, new King(Color.White));
        whiteTeam.put(b6, new Knight(Color.White));
        whiteTeam.put(c6, new Knight(Color.White));
        blackTeam.put(a8, new King(Color.Black));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board);
    }

    @Test
    public void t26() {
        init26();
        assertTrue(gameChecker.isCheckMate(Color.Black));
    }

//  ------------------------  isKingUnderAttack test  ----------------------------------------------
    public void init31() {
        whiteTeam.put(c6, new Queen(Color.White));
        whiteTeam.put(e8, new Queen(Color.White));
        blackTeam.put(c7, new King(Color.Black));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board);
    }

    @Test
    public void t31() {
        init31();
        assertTrue(gameChecker.isKingUnderAttack(Color.Black));
    }

    public void init32() {
        whiteTeam.put(c6, new Queen(Color.White));
        whiteTeam.put(e8, new Queen(Color.White));
        blackTeam.put(b6, new King(Color.Black));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board);
    }

    @Test
    public void t32() {
        init32();
        assertTrue(gameChecker.isKingUnderAttack(Color.Black));
    }

    public void init33() {
        whiteTeam.put(c6, new Queen(Color.White));
        whiteTeam.put(e8, new Queen(Color.White));
        blackTeam.put(b6, new King(Color.Black));
        blackTeam.put(b7, new Pawn(Color.Black));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board);
    }

    @Test
    public void t33() {
        init33();
        assertTrue(gameChecker.isKingUnderAttack(Color.Black));
    }

    public void init34() {
        whiteTeam.put(e2, new King(Color.White));
        whiteTeam.put(f6, new Pawn(Color.White));
        blackTeam.put(e7, new King(Color.Black));
        blackTeam.put(d3, new Pawn(Color.Black));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board);
    }

    @Test
    public void t34() {
        init34();
        assertTrue(gameChecker.isKingUnderAttack(Color.Black));
    }

    @Test
    public void t35() {
        init34();
        assertTrue(gameChecker.isKingUnderAttack(Color.White));
    }

    public void init36() {
        whiteTeam.put(e2, new King(Color.White));
        blackTeam.put(h2, new Queen(Color.Black));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board);
    }

    @Test
    public void t36() {
        init36();
        assertTrue(gameChecker.isKingUnderAttack(Color.White));
    }

    public void init37() {
        whiteTeam.put(e3, new Pawn(Color.White));
        whiteTeam.put(f4, new Pawn(Color.White));
        blackTeam.put(e4, new Queen(Color.Black));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board);
    }

    @Test
    public void t37() {
        init37();
        int cagesUnderAttack = 0;
        for (Position position : allPositions) {
            if (gameChecker.isPositionUnderAttackByEnemyTeam(Color.White, position)) {
                cagesUnderAttack++;
            }
        }
        assertEquals(cagesUnderAttack, 23);
    }

    public void init38() {
        whiteTeam.put(g1, new King(Color.White));
        whiteTeam.put(g7, new Rook(Color.White));
        whiteTeam.put(d7, new Queen(Color.White));
        whiteTeam.put(g6, new Bishop(Color.White));
        blackTeam.put(e8, new King(Color.Black));
        blackTeam.put(f8, new Pawn(Color.Black));
        blackTeam.put(d8, new Pawn(Color.Black));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board);
    }

    @Test
    public void t38() {
        init38();
        assertTrue(gameChecker.isCheckMate(Color.Black));
    }

    public void init39() {
        whiteTeam.put(g1, new King(Color.White));
        whiteTeam.put(f8, new Rook(Color.White));
        whiteTeam.put(h5, new Rook(Color.White));
        whiteTeam.put(f6, new Bishop(Color.White));
        blackTeam.put(h8, new King(Color.Black));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board);
    }

    @Test
    public void t39() {
        init39();
        assertTrue(gameChecker.isCheckMate(Color.Black));
    }

    public void init40() {
        blackTeam.put(g7, new Knight(Color.Black));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board);
    }

    @Test
    public void t40() {
        init40();
        int cagesUnderAttack = 0;
        for (Position position : allPositions) {
            if (gameChecker.isPositionUnderAttackByEnemyTeam(Color.White, position)) {
                cagesUnderAttack++;
            }
        }
        assertEquals(cagesUnderAttack, 4);
    }

    public void init41() {
        blackTeam.put(g8, new Knight(Color.Black));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board);
    }

    @Test
    public void t41() {
        init41();
        int cagesUnderAttack = 0;
        for (Position position : allPositions) {
            if (gameChecker.isPositionUnderAttackByEnemyTeam(Color.White, position)) {
                cagesUnderAttack++;
            }
        }
        assertEquals(cagesUnderAttack, 3);
    }

    public void init42() {
        blackTeam.put(e5, new Knight(Color.Black));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board);
    }

    @Test
    public void t42() {
        init42();
        int cagesUnderAttack = 0;
        for (Position position : allPositions) {
            if (gameChecker.isPositionUnderAttackByEnemyTeam(Color.White, position)) {
                cagesUnderAttack++;
            }
        }
        assertEquals(cagesUnderAttack, 8);
    }

    public void init43() {
        whiteTeam.put(h8, new Rook(Color.White));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board);
    }

    @Test
    public void t43() {
        init43();
        int cagesUnderAttack = 0;
        for (Position position : allPositions) {
            if (gameChecker.isPositionUnderAttackByEnemyTeam(Color.Black, position)) {
                cagesUnderAttack++;
            }
        }
        assertEquals(cagesUnderAttack, 14);
    }

    public void init44() {
        whiteTeam.put(h8, new Rook(Color.White));
        blackTeam.put(h6, new Pawn(Color.Black));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board);
    }

    @Test
    public void t44() {
        init44();
        int cagesUnderAttack = 0;
        for (Position position : allPositions) {
            if (gameChecker.isPositionUnderAttackByEnemyTeam(Color.Black, position)) {
                cagesUnderAttack++;
            }
        }
        assertEquals(cagesUnderAttack, 9);
    }

//  ---------  DRAW CHECK  -------------------------------------------------------------------------

//    public void init45() {
//        whiteTeam.add(new Knight(Color.White, h8));
//        whiteTeam.add(new Pawn(Color.White, g6));
//        whiteTeam.add(new King(Color.White, a1));
//        whiteTeam.add(new Pawn(Color.White, f7));
//
//        board = new Board(blackTeam, whiteTeam);
//        gameChecker = new EndGameChecker(board, whiteTeam, blackTeam);
//    }
//
//    @Test
//    public void t45() {
//        init45();
//        assertFalse(gameChecker.canMakeAnyMove(new Knight(Color.White, h8)));
//    }
//
//    public void init46() {
//        whiteTeam.add(new Knight(Color.White, h8));
//        whiteTeam.add(new Pawn(Color.White, g6));
//        whiteTeam.add(new King(Color.White, a1));
//
//        blackTeam.add(new Pawn(Color.Black, f7));
//
//        board = new Board(blackTeam, whiteTeam);
//        gameChecker = new EndGameChecker(board, whiteTeam, blackTeam);
//    }
//
//    @Test
//    public void t46() {
//        init46();
//        assertTrue(gameChecker.canMakeAnyMove(new Knight(Color.White, h8)));
//    }

//    public void init47() {
//        whiteTeam.add(new King(Color.White, a1));
//        whiteTeam.add(new Knight(Color.White, h8));
//        whiteTeam.add(new Pawn(Color.White, g6));
//
//        board = new Board(blackTeam, whiteTeam);
//        gameChecker = new EndGameChecker(board, whiteTeam, blackTeam);
//    }
//
//    @Test
//    public void t47() {
//        init47();
//        assertTrue(gameChecker.canMakeAnyMove(new Knight(Color.White, h8)));
//    }
//
//    public void init48() {
//        blackTeam.add(new King(Color.Black, a1));
//        blackTeam.add(new Bishop(Color.Black, d1));
//        blackTeam.add(new Knight(Color.Black, h8));
//        blackTeam.add(new Pawn(Color.Black, f7));
//        blackTeam.add(new Pawn(Color.Black, g6));
//
//        whiteTeam.add(new Pawn(Color.White, g5));
//        whiteTeam.add(new Pawn(Color.White, f6));
//        whiteTeam.add(new Queen(Color.White, b3));
//        whiteTeam.add(new Rook(Color.White, h1));
//
//        board = new Board(blackTeam, whiteTeam);
//        gameChecker = new EndGameChecker(board, whiteTeam, blackTeam);
//    }

//    @Test
//    public void t48() {
//        init48();
//        assertTrue(gameChecker.checkForDraw(Color.Black));
//    }
//
//    public void init49() {
//        blackTeam.add(new King(Color.Black, a1));
//
//        whiteTeam.add(new Queen(Color.White, b3));
//
//        board = new Board(blackTeam, whiteTeam);
//        gameChecker = new EndGameChecker(board, whiteTeam, blackTeam);
//    }
//
//    @Test
//    public void t49() {
//        init49();
//        assertTrue(gameChecker.canMakeAnyMove(new King(Color.Black, a1)));
//    }
//
//    public void init50() {
//        blackTeam.add(new King(Color.Black, h3));
//
//        whiteTeam.add(new Queen(Color.White, f4));
//        whiteTeam.add(new Bishop(Color.White, e4));
//        whiteTeam.add(new King(Color.White, c3));
//
//        board = new Board(blackTeam, whiteTeam);
//        gameChecker = new EndGameChecker(board, whiteTeam, blackTeam);
//    }
//
//    @Test
//    public void t50() {
//        init50();
//        assertTrue(gameChecker.checkForDraw(Color.Black));
//    }
//
//    public void init51() {
//        blackTeam.add(new King(Color.Black, h8));
//        blackTeam.add(new Bishop(Color.Black, d8));
//        blackTeam.add(new Pawn(Color.Black, b3));
//        blackTeam.add(new Pawn(Color.Black, c2));
//        blackTeam.add(new Knight(Color.Black, a1));
//
//        whiteTeam.add(new Rook(Color.White, a8));
//        whiteTeam.add(new Bishop(Color.White, c1));
//        whiteTeam.add(new King(Color.White, g6));
//        whiteTeam.add(new Pawn(Color.White, h7));
//        whiteTeam.add(new Pawn(Color.White, b2));
//
//        board = new Board(blackTeam, whiteTeam);
//        gameChecker = new EndGameChecker(board, whiteTeam, blackTeam);
//    }
//
//    @Test
//    public void t51() {
//        init51();
//        assertTrue(gameChecker.checkForDraw(Color.Black));
//    }

}
