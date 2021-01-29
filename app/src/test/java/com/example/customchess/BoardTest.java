package com.example.customchess;

import com.example.customchess.engine.Board;
import com.example.customchess.engine.figures.ChessPiece;
import com.example.customchess.engine.figures.Piece;

public class BoardTest {

    public Piece[][] matrix;
    public Board original;

    public BoardTest(Piece[][] board) {
        original = new Board(board);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if ((matrix[i][j] == null & matrix[i][j] != null)
                        | (matrix[i][j] != null & matrix[i][j] == null)) {
                    return false;
                } else if (matrix[i][j] != null & matrix[i][j] != null) {
                    if (matrix[i][j].getClass() != matrix[i][j].getClass()) {
                        return false;
                    }
                    if (matrix[i][j].getClass() == matrix[i][j].getClass()
                            & ((ChessPiece) matrix[i][j]).color != ((ChessPiece) matrix[i][j]).color) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}