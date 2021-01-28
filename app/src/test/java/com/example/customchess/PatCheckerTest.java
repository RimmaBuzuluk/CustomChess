package com.example.customchess;

import com.example.customchess.engine.Board;
import com.example.customchess.engine.figures.Bishop;
import com.example.customchess.engine.figures.King;
import com.example.customchess.engine.figures.Knight;
import com.example.customchess.engine.figures.Pawn;
import com.example.customchess.engine.figures.Piece;
import com.example.customchess.engine.figures.Queen;
import com.example.customchess.engine.figures.Rook;
import com.example.customchess.engine.misc.Color;
import com.example.customchess.engine.misc.Verticals;
import com.example.customchess.engine.movements.BoardPosition;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PatCheckerTest extends FigureMoveTest {
    Piece[][] matrix = new Piece[][] {
            new Piece[] { new King(Color.Black), null, null, null, null, null, null, null },
            new Piece[] { null, null, new Queen(Color.White), null, null, null, null, null },
            new Piece[] { null, null, new King(Color.White), null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null }
    };
    Piece[][] matrix1 = new Piece[][] {
            new Piece[] { null, null, null, null, null, null, null, new King(Color.Black) },
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, new King(Color.White), null, null, null, new Queen(Color.White), null },
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null }
    };
    Piece[][] matrix2 = new Piece[][] {
            new Piece[] { new King(Color.White), null, null, null, null, null, null, new King(Color.Black) },
            new Piece[] { null, null, new Queen(Color.Black), null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, new Pawn(Color.White), null, null, null, null, null, null },
            new Piece[] { null, new Pawn(Color.Black), new Pawn(Color.White), null, null, null, null, null },
            new Piece[] { null, null, new Pawn(Color.Black), null, null, null, null, null },
            new Piece[] { new Knight(Color.Black), null, null, null, null, null, null, null }
    };
    Piece[][] matrixRofl = new Piece[][] {
            new Piece[] { new Rook(Color.White), new Knight(Color.White), new Bishop(Color.White), new King(Color.White), new Queen(Color.White), new Bishop(Color.White), new Knight(Color.White), new Rook(Color.White)},
            new Piece[] { new Pawn(Color.White), new Pawn(Color.White),new Pawn(Color.White),new Pawn(Color.White),new Pawn(Color.White),new Pawn(Color.White),new Pawn(Color.White),new Pawn(Color.White)},
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { new Pawn(Color.Black), new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black)},
            new Piece[] { new Rook(Color.Black), new Knight(Color.Black), new Bishop(Color.Black), new King(Color.Black), new Queen(Color.Black), new Bishop(Color.Black), new Knight(Color.Black), new Rook(Color.Black)}
    };
    Board board  = new Board(matrix);
    Board board1 = new Board(matrix1);
    Board board2 = new Board(matrix2);
    Board boardRofl = new Board(matrixRofl);


    @Test
    public void t1() {
        assertTrue(board.checkForDraw(Color.Black));
    }

    @Test
    public void t2() {
        assertTrue(board1.checkForDraw(Color.Black));
    }

    @Test
    public void t3() {
        assertFalse(boardRofl.checkForDraw(Color.Black));
    }

    @Test
    public void t4() {
        assertTrue(board2.checkForDraw(Color.White));
    }


}
