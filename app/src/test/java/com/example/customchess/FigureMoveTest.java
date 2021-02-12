package com.example.customchess;

import com.example.customchess.engine.misc.Verticals;
import com.example.customchess.engine.movements.BoardPosition;
import com.example.customchess.engine.movements.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FigureMoveTest {

    Position a1 = new BoardPosition(Verticals.a, 1);
    Position a2 = new BoardPosition(Verticals.a, 2);
    Position a3 = new BoardPosition(Verticals.a, 3);
    Position a4 = new BoardPosition(Verticals.a, 4);
    Position a5 = new BoardPosition(Verticals.a, 5);
    Position a6 = new BoardPosition(Verticals.a, 6);
    Position a7 = new BoardPosition(Verticals.a, 7);
    Position a8 = new BoardPosition(Verticals.a, 8);

    Position b1 = new BoardPosition(Verticals.b, 1);
    Position b2 = new BoardPosition(Verticals.b, 2);
    Position b3 = new BoardPosition(Verticals.b, 3);
    Position b4 = new BoardPosition(Verticals.b, 4);
    Position b5 = new BoardPosition(Verticals.b, 5);
    Position b6 = new BoardPosition(Verticals.b, 6);
    Position b7 = new BoardPosition(Verticals.b, 7);
    Position b8 = new BoardPosition(Verticals.b, 8);

    Position f1 = new BoardPosition(Verticals.f, 1);
    Position f2 = new BoardPosition(Verticals.f, 2);
    Position f3 = new BoardPosition(Verticals.f, 3);
    Position f4 = new BoardPosition(Verticals.f, 4);
    Position f5 = new BoardPosition(Verticals.f, 5);
    Position f6 = new BoardPosition(Verticals.f, 6);
    Position f7 = new BoardPosition(Verticals.f, 7);
    Position f8 = new BoardPosition(Verticals.f, 8);

    Position e1 = new BoardPosition(Verticals.e, 1);
    Position e2 = new BoardPosition(Verticals.e, 2);
    Position e3 = new BoardPosition(Verticals.e, 3);
    Position e4 = new BoardPosition(Verticals.e, 4);
    Position e5 = new BoardPosition(Verticals.e, 5);
    Position e6 = new BoardPosition(Verticals.e, 6);
    Position e7 = new BoardPosition(Verticals.e, 7);
    Position e8 = new BoardPosition(Verticals.e, 8);

    Position g1 = new BoardPosition(Verticals.g, 1);
    Position g2 = new BoardPosition(Verticals.g, 2);
    Position g3 = new BoardPosition(Verticals.g, 3);
    Position g4 = new BoardPosition(Verticals.g, 4);
    Position g5 = new BoardPosition(Verticals.g, 5);
    Position g6 = new BoardPosition(Verticals.g, 6);
    Position g7 = new BoardPosition(Verticals.g, 7);
    Position g8 = new BoardPosition(Verticals.g, 8);

    Position c1 = new BoardPosition(Verticals.c, 1);
    Position c2 = new BoardPosition(Verticals.c, 2);
    Position c3 = new BoardPosition(Verticals.c, 3);
    Position c4 = new BoardPosition(Verticals.c, 4);
    Position c5 = new BoardPosition(Verticals.c, 5);
    Position c6 = new BoardPosition(Verticals.c, 6);
    Position c7 = new BoardPosition(Verticals.c, 7);
    Position c8 = new BoardPosition(Verticals.c, 8);

    Position d1 = new BoardPosition(Verticals.d, 1);
    Position d2 = new BoardPosition(Verticals.d, 2);
    Position d3 = new BoardPosition(Verticals.d, 3);
    Position d4 = new BoardPosition(Verticals.d, 4);
    Position d5 = new BoardPosition(Verticals.d, 5);
    Position d6 = new BoardPosition(Verticals.d, 6);
    Position d7 = new BoardPosition(Verticals.d, 7);
    Position d8 = new BoardPosition(Verticals.d, 8);

    Position h1 = new BoardPosition(Verticals.h, 1);
    Position h2 = new BoardPosition(Verticals.h, 2);
    Position h3 = new BoardPosition(Verticals.h, 3);
    Position h4 = new BoardPosition(Verticals.h, 4);
    Position h5 = new BoardPosition(Verticals.h, 5);
    Position h6 = new BoardPosition(Verticals.h, 6);
    Position h7 = new BoardPosition(Verticals.h, 7);
    Position h8 = new BoardPosition(Verticals.h, 8);

    Position[] cages = new Position[] {
            a1, b1, c1, d1, e1, f1, g1, h1,
            a2, b2, c2, d2, e2, f2, g2, h2,
            a3, b3, c3, d3, e3, f3, g3, h3,
            a4, b4, c4, d4, e4, f4, g4, h4,
            a5, b5, c5, d5, e5, f5, g5, h5,
            a6, b6, c6, d6, e6, f6, g6, h6,
            a7, b7, c7, d7, e7, f7, g7, h7,
            a8, b8, c8, d8, e8, f8, g8, h8,
    };
    List<Position> allPositions = new ArrayList<>(Arrays.asList(cages));
}
