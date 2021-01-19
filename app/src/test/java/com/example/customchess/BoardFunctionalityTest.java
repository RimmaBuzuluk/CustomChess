package com.example.customchess;

import com.example.customchess.engine.Board;
import com.example.customchess.engine.exceptions.CheckMateException;
import com.example.customchess.engine.figures.Bishop;
import com.example.customchess.engine.figures.King;
import com.example.customchess.engine.figures.Knight;
import com.example.customchess.engine.figures.Pawn;
import com.example.customchess.engine.figures.Piece;
import com.example.customchess.engine.figures.Queen;
import com.example.customchess.engine.figures.Rook;
import com.example.customchess.engine.misc.Color;
import com.example.customchess.engine.movements.Position;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class BoardFunctionalityTest extends FigureMoveTest {
    Piece[][] matrix = new Piece[][] {
            new Piece[] { new Rook(Color.White), new Knight(Color.White), new Bishop(Color.White), new King(Color.White), new Queen(Color.White), new Bishop(Color.White), new Knight(Color.White), new Rook(Color.White)},
            new Piece[] { new Pawn(Color.White), new Pawn(Color.White),new Pawn(Color.White),new Pawn(Color.White),new Pawn(Color.White),new Pawn(Color.White),new Pawn(Color.White),new Pawn(Color.White)},
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { new Pawn(Color.Black), new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black)},
            new Piece[] { new Rook(Color.Black), new Knight(Color.Black), new Bishop(Color.Black), new King(Color.Black), new Queen(Color.Black), new Bishop(Color.Black), new Knight(Color.Black), new Rook(Color.Black)}
    };
    Piece[][] matrix1 = new Piece[][] {
            new Piece[] { new Rook(Color.White), new Knight(Color.White), new Bishop(Color.White), new King(Color.White), new Queen(Color.White), new Bishop(Color.White), new Knight(Color.White), new Rook(Color.White)},
            new Piece[] { new Pawn(Color.White), new Pawn(Color.White),new Pawn(Color.White),      null,                  null                  ,new Pawn(Color.White),new Pawn(Color.White),new Pawn(Color.White)},
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, null, new Queen(Color.Black), null, null, null, new Bishop(Color.Black) },
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { new Pawn(Color.Black), new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black)},
            new Piece[] { new Rook(Color.Black), new Knight(Color.Black), new Bishop(Color.Black), new King(Color.Black), new Queen(Color.Black), new Bishop(Color.Black), new Knight(Color.Black), new Rook(Color.Black)}
    };
    Piece[][] matrix2 = new Piece[][] {
            new Piece[] { new Rook(Color.White), new Knight(Color.White), new Bishop(Color.White), new King(Color.White), new Queen(Color.White), new Bishop(Color.White), new Knight(Color.White), new Rook(Color.White)},
            new Piece[] { new Pawn(Color.White), new Pawn(Color.White),   new Pawn(Color.White),   new Pawn(Color.White), null                  , new Pawn(Color.White),new Pawn(Color.White),new Pawn(Color.White)},
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, null, new Queen(Color.Black), null, null, null, new Bishop(Color.Black) },
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { new Pawn(Color.Black), new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black)},
            new Piece[] { new Rook(Color.Black), new Knight(Color.Black), new Bishop(Color.Black), new King(Color.Black), new Queen(Color.Black), new Bishop(Color.Black), new Knight(Color.Black), new Rook(Color.Black)}
    };
    Board board1 = new Board();
    Board board2 = new Board(matrix);
    Board board3 = new Board(matrix1);
    Board board4 = new Board(matrix2);

    public void equalBoards(Board b1, Board b2) {
        assertEquals(b1, b2);
    }

    @Test
    public void t2() {
        move(d2, d4);
        move(e7, e5);
        beat(d4, e5);

        equalBoards(board1, board2);
    }

    @Test
    public void t3() {
        assertTrue(board3.isPositionUnderAttack(Color.White, e1));
    }

    @Test
    public void t4() {
        assertTrue(board3.isCheckMate(Color.White));
    }

    @Test
    public void t5() {
        assertFalse(board4.isCheckMate(Color.White));
    }

    private void move(Position start, Position destination) {
        board1.swapFigures(start, destination);
        board2.swapFigures(start, destination);
    }

    private void beat(Position start, Position destination) {
        board1.beatFigure(start, destination);
        board2.beatFigure(start, destination);
    }

    private void castle(Position start, Position destination) {
        board1.castling(start, destination);
        board2.castling(start, destination);
    }
}
