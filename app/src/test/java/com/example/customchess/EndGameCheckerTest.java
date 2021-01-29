package com.example.customchess;

import com.example.customchess.engine.Board;
import com.example.customchess.engine.EndGameChecker;
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

    public void init5() {
        whiteTeam.add(new King(Color.White, h1));
        whiteTeam.add(new Rook(Color.White, g2));
        blackTeam.add(new Queen(Color.Black, e1));
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
        boolean fuck = gameChecker.isNoCageToMoveKingAway(cagesAroundKing, h8);
        assertTrue(fuck);
    }

    @Test
    public void t2() {
        init2();
        List<Position> cagesAroundKing = gameChecker.getEmptyPositionsAround(h8);
        boolean fuck = gameChecker.isNoCageToMoveKingAway(cagesAroundKing, h8);
        assertTrue(fuck);
    }

    @Test
    public void t3() {
        init3();
        List<Position> cagesAroundKing = gameChecker.getEmptyPositionsAround(g7);
        boolean fuck = gameChecker.isNoCageToMoveKingAway(cagesAroundKing, g7);
        assertTrue(fuck);
    }

    @Test
    public void t4() {
        init1();
        boolean fuck = gameChecker.isNoFigureToBeatAttackingPiece(Color.White, g6);
        assertTrue(fuck);
    }

    @Test
    public void t5() {
        init4();
        boolean fuck = gameChecker.isNoFigureToBeatAttackingPiece(Color.White, g1);
        assertTrue(fuck);
    }

    @Test
    public void t6() {
        init6();
        boolean fuck = gameChecker.isNoPieceToCoverKingFromCheck(Color.White, h1, e1);
        assertFalse(fuck);
    }

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
        init7();
        assertTrue(gameChecker.isCheckMate(Color.White));
    }

}
