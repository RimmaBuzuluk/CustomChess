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

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EndGameCheckerTest extends FigureMoveTest {

    List<Piece> blackTeam = new LinkedList<>();
    List<Piece> whiteTeam = new LinkedList<>();
    Board board = new Board(blackTeam, whiteTeam);
    EndGameChecker gameChecker;

    public void init1() {
        whiteTeam.add(new King(Color.White, h8));
        whiteTeam.add(new Pawn(Color.White, h5));
        whiteTeam.add(new Pawn(Color.White, g8));
        blackTeam.add(new Knight(Color.Black, g6));
        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board, whiteTeam, blackTeam);
    }

    public void init2() {
        whiteTeam.add(new King(Color.White, h8));
        blackTeam.add(new Queen(Color.Black, f6));
        blackTeam.add(new Rook(Color.Black, f8));
        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board, whiteTeam, blackTeam);
    }

    public void init3() {
        whiteTeam.add(new King(Color.White, g7));
        blackTeam.add(new Queen(Color.Black, h4));
        blackTeam.add(new Rook(Color.Black, f8));
        blackTeam.add(new Rook(Color.Black, h8));
        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board, whiteTeam, blackTeam);
    }

    public void init4() {
        whiteTeam.add(new King(Color.White, h1));
        blackTeam.add(new Queen(Color.Black, g1));
        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board, whiteTeam, blackTeam);
    }

    public void init6() {
        whiteTeam.add(new King(Color.White, h1));
        whiteTeam.add(new Rook(Color.White, g2));
        blackTeam.add(new Queen(Color.Black, e1));
        blackTeam.add(new Rook(Color.Black, h4));
        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board, whiteTeam, blackTeam);
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
        whiteTeam.add(new King(Color.White, h1));
        whiteTeam.add(new Rook(Color.White, g2));

        blackTeam.add(new Queen(Color.Black, e1));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board, whiteTeam, blackTeam);
    }

    @Test
    public void tIsFigureToCover1() {
        init5();
        assertTrue(gameChecker.isPieceToCoverKingFromCheck(Color.White, h1, e1));
    }

    public void init27() {
        whiteTeam.add(new King(Color.White, f6));
        whiteTeam.add(new Pawn(Color.White, e6));

        blackTeam.add(new Bishop(Color.Black, d8));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board, whiteTeam, blackTeam);
    }

    @Test
    public void t27() {
        init27();
        assertTrue(gameChecker.isPieceToCoverKingFromCheck(Color.White, f6, d8));
    }

    public void init28() {
        whiteTeam.add(new King(Color.White, f6));
        whiteTeam.add(new Pawn(Color.White, e6));

        blackTeam.add(new Knight(Color.Black, g8));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board, whiteTeam, blackTeam);
    }

    @Test
    public void t28() {
        init28();
        assertFalse(gameChecker.isPieceToCoverKingFromCheck(Color.White, f6, g8));
    }

    public void init29() {
        whiteTeam.add(new King(Color.White, f6));
        whiteTeam.add(new Knight(Color.White, f5));

        blackTeam.add(new Pawn(Color.Black, e7));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board, whiteTeam, blackTeam);
    }

    @Test
    public void t29() {
        init29();
        assertTrue(gameChecker.isFigureToBeatAttackingPiece(Color.White, e7));
    }

    public void init30() {
        whiteTeam.add(new King(Color.White, d1));
        whiteTeam.add(new Bishop(Color.White, g1));

        blackTeam.add(new Rook(Color.Black, h1));
        blackTeam.add(new Queen(Color.Black, d4));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board, whiteTeam, blackTeam);
    }

    @Test
    public void t30() {
        init30();
        assertFalse(gameChecker.isFigureToBeatAttackingPiece(Color.White, d4));
    }

//  ----------- checkmate tests --------------------------------------------------------------------
    public void init7() {
        whiteTeam.add(new King(Color.White, h1));
        whiteTeam.add(new Pawn(Color.White, h2));
        whiteTeam.add(new Pawn(Color.White, g2));

        blackTeam.add(new Queen(Color.Black, e1));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board, whiteTeam, blackTeam);
    }

    @Test
    public void t7() {
        init7();
        assertTrue(gameChecker.isCheckMate(Color.White));
    }

    public void init8() {
        whiteTeam.add(new King(Color.White, h1));
        whiteTeam.add(new Pawn(Color.White, h2));
        whiteTeam.add(new Pawn(Color.White, g2));
        whiteTeam.add(new Rook(Color.White, g1));

        blackTeam.add(new Knight(Color.Black, f2));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board, whiteTeam, blackTeam);
    }

    @Test
    public void t8() {
        init8();
        assertTrue(gameChecker.isCheckMate(Color.White));
    }

    public void init9() {
        whiteTeam.add(new King(Color.White, a1));

        blackTeam.add(new Knight(Color.Black, b4));
        blackTeam.add(new Pawn(Color.Black, b3));
        blackTeam.add(new Bishop(Color.Black, f6));
        blackTeam.add(new Bishop(Color.Black, g6));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board, whiteTeam, blackTeam);
    }

    @Test
    public void t9() {
        init9();
        boolean res = gameChecker.isCheckMate(Color.White);
        assertTrue(res);
    }

    public void init10() {
        whiteTeam.add(new King(Color.White, a1));

        blackTeam.add(new Knight(Color.Black, b4));
        blackTeam.add(new Knight(Color.Black, b4));
        blackTeam.add(new Bishop(Color.Black, f6));
        blackTeam.add(new Bishop(Color.Black, g6));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board, whiteTeam, blackTeam);
    }

    @Test
    public void t10() {
        init10();
        assertTrue(gameChecker.isCheckMate(Color.White));
    }

    public void init11() {
        whiteTeam.add(new King(Color.White, c6));
        whiteTeam.add(new Queen(Color.White, b7));

        blackTeam.add(new King(Color.Black, b8));


        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board, whiteTeam, blackTeam);
    }

    @Test
    public void t11() {
        init11();
        assertTrue(gameChecker.isCheckMate(Color.Black));
    }

    public void init12() {
        whiteTeam.add(new Queen(Color.White, h7));
        whiteTeam.add(new Bishop(Color.White, d3));
        whiteTeam.add(new Pawn(Color.White, a3));
        whiteTeam.add(new Pawn(Color.White, b2));
        whiteTeam.add(new Rook(Color.White, c1));
        whiteTeam.add(new Rook(Color.White, f1));
        whiteTeam.add(new King(Color.White, g1));
        whiteTeam.add(new Pawn(Color.White, f2));
        whiteTeam.add(new Pawn(Color.White, g2));
        whiteTeam.add(new Pawn(Color.White, h2));

        blackTeam.add(new King(Color.Black, h8));
        blackTeam.add(new Rook(Color.Black, a8));
        blackTeam.add(new Rook(Color.Black, f8));
        blackTeam.add(new Pawn(Color.Black, b7));
        blackTeam.add(new Pawn(Color.Black, g7));
        blackTeam.add(new Pawn(Color.Black, f7));
        blackTeam.add(new Pawn(Color.Black, a6));
        blackTeam.add(new Queen(Color.Black, b6));
        blackTeam.add(new Bishop(Color.Black, f6));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board, whiteTeam, blackTeam);
    }

    @Test
    public void t12() {
        init12();
        assertTrue(gameChecker.isCheckMate(Color.Black));
    }

    public void init13() {
        whiteTeam.add(new Queen(Color.White, g7));
        whiteTeam.add(new Pawn(Color.White, g7));
        whiteTeam.add(new Pawn(Color.White, f6));
        whiteTeam.add(new Pawn(Color.White, a3));
        whiteTeam.add(new Pawn(Color.White, b2));
        whiteTeam.add(new Pawn(Color.White, g2));
        whiteTeam.add(new Pawn(Color.White, h2));
        whiteTeam.add(new King(Color.White, h1));
        whiteTeam.add(new Rook(Color.White, c1));
        whiteTeam.add(new Rook(Color.White, f1));

        blackTeam.add(new King(Color.Black, g8));
        blackTeam.add(new Pawn(Color.Black, f7));
        blackTeam.add(new Pawn(Color.Black, g6));
        blackTeam.add(new Pawn(Color.Black, h7));
        blackTeam.add(new Pawn(Color.Black, b7));
        blackTeam.add(new Pawn(Color.Black, a6));
        blackTeam.add(new Queen(Color.Black, b6));
        blackTeam.add(new Rook(Color.Black, a8));
        blackTeam.add(new Rook(Color.Black, d8));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board, whiteTeam, blackTeam);
    }

    @Test
    public void t13() {
        init13();
        assertTrue(gameChecker.isCheckMate(Color.Black));
    }

    public void init14() {
        whiteTeam.add(new King(Color.White, d6));
        whiteTeam.add(new Rook(Color.White, g8));

        blackTeam.add(new King(Color.Black, d8));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board, whiteTeam, blackTeam);
    }

    @Test
    public void t14() {
        init14();
        assertTrue(gameChecker.isCheckMate(Color.Black));
    }

    public void init15() {
        whiteTeam.add(new King(Color.White, g1));
        whiteTeam.add(new Rook(Color.White, g7));
        whiteTeam.add(new Queen(Color.White, d7));
        whiteTeam.add(new Bishop(Color.White, d6));

        blackTeam.add(new King(Color.Black, e8));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board, whiteTeam, blackTeam);
    }

    @Test
    public void t15() {
        init15();
        assertTrue(gameChecker.isCheckMate(Color.Black));
    }

    public void init16() {
        whiteTeam.add(new King(Color.White, g1));
        whiteTeam.add(new Queen(Color.White, c8));
        whiteTeam.add(new Pawn(Color.White, a3));
        whiteTeam.add(new Pawn(Color.White, b4));
        whiteTeam.add(new Pawn(Color.White, f2));
        whiteTeam.add(new Pawn(Color.White, g2));
        whiteTeam.add(new Pawn(Color.White, h3));
        whiteTeam.add(new Bishop(Color.White, e3));

        blackTeam.add(new King(Color.Black, g8));
        blackTeam.add(new Bishop(Color.Black, g6));
        blackTeam.add(new Queen(Color.Black, e2));
        blackTeam.add(new Pawn(Color.Black, h7));
        blackTeam.add(new Pawn(Color.Black, g7));
        blackTeam.add(new Pawn(Color.Black, f7));
        blackTeam.add(new Pawn(Color.Black, b5));
        blackTeam.add(new Pawn(Color.Black, c4));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board, whiteTeam, blackTeam);
    }

    @Test
    public void t16() {
        init16();
        assertTrue(gameChecker.isCheckMate(Color.Black));
    }

    public void init17() {
        whiteTeam.add(new King(Color.White, g1));
        whiteTeam.add(new Bishop(Color.White, e5));
        whiteTeam.add(new Pawn(Color.White, a3));
        whiteTeam.add(new Pawn(Color.White, b2));
        whiteTeam.add(new Pawn(Color.White, f2));
        whiteTeam.add(new Pawn(Color.White, g2));
        whiteTeam.add(new Pawn(Color.White, h3));
        whiteTeam.add(new Rook(Color.White, d8));

        blackTeam.add(new King(Color.Black, h8));
        blackTeam.add(new Rook(Color.Black, g8));
        blackTeam.add(new Bishop(Color.Black, c5));
        blackTeam.add(new Pawn(Color.Black, g6));
        blackTeam.add(new Pawn(Color.Black, h7));
        blackTeam.add(new Pawn(Color.Black, b6));
        blackTeam.add(new Pawn(Color.Black, a4));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board, whiteTeam, blackTeam);
    }

    @Test
    public void t17() {
        init17();
        assertTrue(gameChecker.isCheckMate(Color.Black));
    }

    public void init18() {
        whiteTeam.add(new King(Color.White, g1));
        whiteTeam.add(new Pawn(Color.White, a3));
        whiteTeam.add(new Pawn(Color.White, b2));
        whiteTeam.add(new Pawn(Color.White, f2));
        whiteTeam.add(new Pawn(Color.White, g2));
        whiteTeam.add(new Pawn(Color.White, g7));
        whiteTeam.add(new Rook(Color.White, d8));
        whiteTeam.add(new Knight(Color.White, f5));

        blackTeam.add(new King(Color.Black, h8));
        blackTeam.add(new Rook(Color.Black, g8));
        blackTeam.add(new Bishop(Color.Black, c5));
        blackTeam.add(new Pawn(Color.Black, h7));
        blackTeam.add(new Pawn(Color.Black, b6));
        blackTeam.add(new Pawn(Color.Black, a4));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board, whiteTeam, blackTeam);
    }

    @Test
    public void t18() {
        init18();
        assertTrue(gameChecker.isCheckMate(Color.Black));
    }

    public void init19() {
        whiteTeam.add(new King(Color.White, h1));

        blackTeam.add(new King(Color.Black, a8));
        blackTeam.add(new Queen(Color.Black, h3));
        blackTeam.add(new Rook(Color.Black, e1));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board, whiteTeam, blackTeam);
    }

    @Test
    public void t19() {
        init19();
        assertTrue(gameChecker.isCheckMate(Color.White));
    }

    public void init20() {
        whiteTeam.add(new King(Color.White, h1));

        blackTeam.add(new King(Color.Black, a8));
        blackTeam.add(new Queen(Color.Black, h4));
        blackTeam.add(new Rook(Color.Black, e1));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board, whiteTeam, blackTeam);
    }

    @Test
    public void t20() {
        init20();
        assertFalse(gameChecker.isCheckMate(Color.White));
    }

    public void init21() {
        whiteTeam.add(new King(Color.White, h1));

        blackTeam.add(new King(Color.Black, a8));
        blackTeam.add(new Queen(Color.Black, h2));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board, whiteTeam, blackTeam);
    }

    @Test
    public void t21() {
        init21();
        assertFalse(gameChecker.isCheckMate(Color.White));
    }

    public void init22() {
        whiteTeam.add(new King(Color.White, e4));
        whiteTeam.add(new Queen(Color.White, e5));

        blackTeam.add(new King(Color.Black, e8));
        blackTeam.add(new Rook(Color.Black, d8));
        blackTeam.add(new Rook(Color.Black, f8));
        blackTeam.add(new Pawn(Color.Black, d7));
        blackTeam.add(new Pawn(Color.Black, f7));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board, whiteTeam, blackTeam);
    }

    @Test
    public void t22() {
        init22();
        assertTrue(gameChecker.isCheckMate(Color.Black));
    }

    public void init23() {
        whiteTeam.add(new King(Color.White, e4));
        whiteTeam.add(new Queen(Color.White, e6));

        blackTeam.add(new King(Color.Black, e8));
        blackTeam.add(new Rook(Color.Black, d8));
        blackTeam.add(new Rook(Color.Black, f8));
        blackTeam.add(new Pawn(Color.Black, d7));
        blackTeam.add(new Pawn(Color.Black, f7));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board, whiteTeam, blackTeam);
    }

    @Test
    public void t23() {
        init23();
        assertFalse(gameChecker.isCheckMate(Color.Black));
    }

    public void init24() {
        whiteTeam.add(new King(Color.White, b6));
        whiteTeam.add(new Bishop(Color.White, d5));
        whiteTeam.add(new Bishop(Color.White, d6));

        blackTeam.add(new King(Color.Black, a8));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board, whiteTeam, blackTeam);
    }

    @Test
    public void t24() {
        init24();
        assertTrue(gameChecker.isCheckMate(Color.Black));
    }

    public void init25() {
        whiteTeam.add(new King(Color.White, c7));
        whiteTeam.add(new Bishop(Color.White, b7));
        whiteTeam.add(new Knight(Color.White, c6));

        blackTeam.add(new King(Color.Black, a8));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board, whiteTeam, blackTeam);
    }

    @Test
    public void t25() {
        init25();
        assertTrue(gameChecker.isCheckMate(Color.Black));
    }

    public void init26() {
        whiteTeam.add(new King(Color.White, c7));
        whiteTeam.add(new Knight(Color.White, b6));
        whiteTeam.add(new Knight(Color.White, c6));

        blackTeam.add(new King(Color.Black, a8));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board, whiteTeam, blackTeam);
    }

    @Test
    public void t26() {
        init26();
        assertTrue(gameChecker.isCheckMate(Color.Black));
    }

//  ------------------------  isKingUnderAttack test  ----------------------------------------------
    public void init31() {
        whiteTeam.add(new Queen(Color.White, c6));
        whiteTeam.add(new Queen(Color.White, e8));

        blackTeam.add(new King(Color.Black, c7));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board, whiteTeam, blackTeam);
    }

    @Test
    public void t31() {
        init31();
        assertTrue(gameChecker.isKingUnderAttack(Color.Black));
    }

    public void init32() {
        whiteTeam.add(new Queen(Color.White, c6));
        whiteTeam.add(new Queen(Color.White, e8));

        blackTeam.add(new King(Color.Black, b6));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board, whiteTeam, blackTeam);
    }

    @Test
    public void t32() {
        init32();
        assertTrue(gameChecker.isKingUnderAttack(Color.Black));
    }

    public void init33() {
        whiteTeam.add(new Queen(Color.White, c6));
        whiteTeam.add(new Queen(Color.White, e8));

        blackTeam.add(new King(Color.Black, b6));
        blackTeam.add(new Pawn(Color.Black, b7));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board, whiteTeam, blackTeam);
    }

    @Test
    public void t33() {
        init33();
        assertTrue(gameChecker.isKingUnderAttack(Color.Black));
    }

    public void init34() {
        whiteTeam.add(new King(Color.White, e2));
        whiteTeam.add(new Pawn(Color.White, f6));

        blackTeam.add(new King(Color.Black, e7));
        blackTeam.add(new Pawn(Color.Black, d3));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board, whiteTeam, blackTeam);
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
        whiteTeam.add(new King(Color.White, e2));

        blackTeam.add(new Queen(Color.Black, h2));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board, whiteTeam, blackTeam);
    }

    @Test
    public void t36() {
        init36();
        assertTrue(gameChecker.isKingUnderAttack(Color.White));
    }

    public void init37() {
        whiteTeam.add(new Pawn(Color.White, e3));
        whiteTeam.add(new Pawn(Color.White, f4));

        blackTeam.add(new Queen(Color.Black, e4));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board, whiteTeam, blackTeam);
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
        whiteTeam.add(new King(Color.White, g1));
        whiteTeam.add(new Rook(Color.White, g7));
        whiteTeam.add(new Queen(Color.White, d7));
        whiteTeam.add(new Bishop(Color.White, g6));

        blackTeam.add(new King(Color.Black, e8));
        blackTeam.add(new Pawn(Color.Black, f8));
        blackTeam.add(new Pawn(Color.Black, d8));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board, whiteTeam, blackTeam);
    }

    @Test
    public void t38() {
        init38();
        assertTrue(gameChecker.isCheckMate(Color.Black));
    }

    public void init39() {
        whiteTeam.add(new King(Color.White, g1));
        whiteTeam.add(new Rook(Color.White, f8));
        whiteTeam.add(new Rook(Color.White, h5));
        whiteTeam.add(new Bishop(Color.White, f6));

        blackTeam.add(new King(Color.Black, h8));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board, whiteTeam, blackTeam);
    }

    @Test
    public void t39() {
        init39();
        assertTrue(gameChecker.isCheckMate(Color.Black));
    }

    public void init40() {
        blackTeam.add(new Knight(Color.Black, g7));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board, whiteTeam, blackTeam);
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
        blackTeam.add(new Knight(Color.Black, g8));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board, whiteTeam, blackTeam);
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
        blackTeam.add(new Knight(Color.Black, e5));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board, whiteTeam, blackTeam);
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
        whiteTeam.add(new Rook(Color.White, h8));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board, whiteTeam, blackTeam);
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
        whiteTeam.add(new Rook(Color.White, h8));

        blackTeam.add(new Pawn(Color.Black, h6));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board, whiteTeam, blackTeam);
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

    public void init45() {
        whiteTeam.add(new Knight(Color.White, h8));
        whiteTeam.add(new Pawn(Color.White, g6));
        whiteTeam.add(new King(Color.White, a1));
        whiteTeam.add(new Pawn(Color.White, f7));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board, whiteTeam, blackTeam);
    }

    @Test
    public void t45() {
        init45();
        assertFalse(gameChecker.canMakeAnyMove(new Knight(Color.White, h8)));
    }

    public void init46() {
        whiteTeam.add(new Knight(Color.White, h8));
        whiteTeam.add(new Pawn(Color.White, g6));
        whiteTeam.add(new King(Color.White, a1));

        blackTeam.add(new Pawn(Color.Black, f7));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board, whiteTeam, blackTeam);
    }

    @Test
    public void t46() {
        init46();
        assertTrue(gameChecker.canMakeAnyMove(new Knight(Color.White, h8)));
    }

    public void init47() {
        whiteTeam.add(new King(Color.White, a1));
        whiteTeam.add(new Knight(Color.White, h8));
        whiteTeam.add(new Pawn(Color.White, g6));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board, whiteTeam, blackTeam);
    }

    @Test
    public void t47() {
        init47();
        assertTrue(gameChecker.canMakeAnyMove(new Knight(Color.White, h8)));
    }

    public void init48() {
        blackTeam.add(new King(Color.Black, a1));
        blackTeam.add(new Bishop(Color.Black, d1));
        blackTeam.add(new Knight(Color.Black, h8));
        blackTeam.add(new Pawn(Color.Black, f7));
        blackTeam.add(new Pawn(Color.Black, g6));

        whiteTeam.add(new Pawn(Color.White, g5));
        whiteTeam.add(new Pawn(Color.White, f6));
        whiteTeam.add(new Queen(Color.White, b3));
        whiteTeam.add(new Rook(Color.White, h1));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board, whiteTeam, blackTeam);
    }

    @Test
    public void t48() {
        init48();
        assertTrue(gameChecker.checkForDraw(Color.Black));
    }

    public void init49() {
        blackTeam.add(new King(Color.Black, a1));

        whiteTeam.add(new Queen(Color.White, b3));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board, whiteTeam, blackTeam);
    }

    @Test
    public void t49() {
        init49();
        assertTrue(gameChecker.canMakeAnyMove(new King(Color.Black, a1)));
    }

    public void init50() {
        blackTeam.add(new King(Color.Black, h3));

        whiteTeam.add(new Queen(Color.White, f4));
        whiteTeam.add(new Bishop(Color.White, e4));
        whiteTeam.add(new King(Color.White, c3));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board, whiteTeam, blackTeam);
    }

    @Test
    public void t50() {
        init50();
        assertTrue(gameChecker.checkForDraw(Color.Black));
    }

    public void init51() {
        blackTeam.add(new King(Color.Black, h8));
        blackTeam.add(new Bishop(Color.Black, d8));
        blackTeam.add(new Pawn(Color.Black, b3));
        blackTeam.add(new Pawn(Color.Black, c2));
        blackTeam.add(new Knight(Color.Black, a1));

        whiteTeam.add(new Rook(Color.White, a8));
        whiteTeam.add(new Bishop(Color.White, c1));
        whiteTeam.add(new King(Color.White, g6));
        whiteTeam.add(new Pawn(Color.White, h7));
        whiteTeam.add(new Pawn(Color.White, b2));

        board = new Board(blackTeam, whiteTeam);
        gameChecker = new EndGameChecker(board, whiteTeam, blackTeam);
    }

    @Test
    public void t51() {
        init51();
        assertTrue(gameChecker.checkForDraw(Color.Black));
    }

}
