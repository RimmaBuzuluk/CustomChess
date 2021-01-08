package com.example.customchess.engine;

import android.util.Log;

import com.example.customchess.engine.exceptions.ChessException;
import com.example.customchess.engine.exceptions.InvalidMoveException;
import com.example.customchess.engine.figures.*;
import com.example.customchess.engine.misc.Color;
import com.example.customchess.engine.misc.Verticals;
import com.example.customchess.engine.movements.BoardPosition;
import com.example.customchess.engine.movements.Movable;
import com.example.customchess.engine.movements.Position;


public class Board {

    private Piece[][] matrix;


    public Board() {
        matrix = new Piece[8][8];
        initTeam(Color.White);
        initTeam(Color.Black);
    }

    public boolean tryToMove(Movable movement) throws ChessException {
        Position start = movement.getStart();
        Position destination = movement.getDestination();

        Piece figure = findBy(start);

        if (figure.isTrajectoryValid(movement)) {
            swapFigures(start, destination);
        } else {
            throw new InvalidMoveException("Invalid move\n" + start + " - " + destination);
        }
        return true;
    }

    public Piece findBy(Position position) {
        int vertical = position.getVertical().ordinal();
        int horizontal = position.getHorizontal() - 1;

        if (isCageEmpty(matrix[horizontal][vertical])) {
            return null;
        }

        return matrix[horizontal][vertical];
    }

    private void LOG() {
        for (Integer i = 0; i < 8; i++) {
            for (Integer j = 0; j < 8; j++) {
                if (matrix[i][j] != null) {
                    Verticals[] fuck = Verticals.values();
                    Position position = new BoardPosition(fuck[j], i + 1);
                    Log.d("move", matrix[i][j] + " on position " + position + "\n");
                }
            }
        }
        Log.d("move", "------------------------------------------------------------------------------+-+");
    }

    private void swapFigures(Position start, Position destination) {
        int startVertical = start.getVertical().ordinal();
        int startHorizontal = start.getHorizontal() - 1;
        int destVertical = destination.getVertical().ordinal();
        int destHorizontal = destination.getHorizontal() - 1;

        Piece figure = matrix[startHorizontal][startVertical];

        matrix[startHorizontal][startVertical] = matrix[destHorizontal][destVertical];
        matrix[destHorizontal][destVertical] = figure;
//        LOG();
    }

    private boolean isCageEmpty(Piece figure) {
        return figure == null;
    }

    private void initTeam(Color color) {
        int pawnRow = 1;
        int kingRow = 0;
        if (Color.Black.equals(color)) {
            pawnRow = 6;
            kingRow = 7;
        }
        matrix[kingRow][0] = new Rook(color);
        matrix[kingRow][7] = new Rook(color);
        matrix[kingRow][1] = new Knight(color);
        matrix[kingRow][6] = new Knight(color);
        matrix[kingRow][2] = new Bishop(color);
        matrix[kingRow][5] = new Bishop(color);
        matrix[kingRow][3] = new King(color);
        matrix[kingRow][4] = new Queen(color);
        for (int i = 0; i < 8; i++) {
            matrix[pawnRow][i] = new Pawn(color);
        }
    }
}
