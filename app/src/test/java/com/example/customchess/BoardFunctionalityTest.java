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
import com.example.customchess.engine.misc.Verticals;
import com.example.customchess.engine.movements.BoardPosition;
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
            new Piece[] { new Pawn(Color.White), new Pawn(Color.White),   new Pawn(Color.White),   new Pawn(Color.White), null                  , new Pawn(Color.White), new Pawn(Color.White),new Pawn(Color.White)},
            new Piece[] { null, null, null, null, null, null, null, new Queen(Color.White) },
            new Piece[] { null, null, null, null, null, null, null, null },

            new Piece[] { null, null, null, new Queen(Color.Black), null, null, null, new Bishop(Color.Black) },
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { new Pawn(Color.Black), new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black)},
            new Piece[] { new Rook(Color.Black), new Knight(Color.Black), new Bishop(Color.Black), new King(Color.Black), new Queen(Color.Black), new Bishop(Color.Black), new Knight(Color.Black), new Rook(Color.Black)}
    };
    Piece[][] matrix5 = new Piece[][] {
            new Piece[] { new Rook(Color.White), new Knight(Color.White), new Bishop(Color.White), new King(Color.White), new Queen(Color.White), new Bishop(Color.White), new Knight(Color.White), new Rook(Color.White)},
            new Piece[] { new Pawn(Color.White), new Pawn(Color.White),   new Pawn(Color.White),   new Pawn(Color.White), new Pawn(Color.White) , new Pawn(Color.White),new Pawn(Color.White),new Pawn(Color.White)},
            new Piece[] { null, null, new Knight(Color.Black), null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, null, new Queen(Color.Black), null, null, null, new Bishop(Color.Black) },
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { new Pawn(Color.Black), new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black)},
            new Piece[] { new Rook(Color.Black), new Knight(Color.Black), new Bishop(Color.Black), new King(Color.Black), new Queen(Color.Black), new Bishop(Color.Black), new Knight(Color.Black), new Rook(Color.Black)}
    };
    Piece[][] matrix7 = new Piece[][] {
            new Piece[] { new King(Color.White), new Knight(Color.White), new Bishop(Color.White), new Pawn(Color.White), new Queen(Color.White), new Bishop(Color.White), new Knight(Color.White), new Rook(Color.White)},
            new Piece[] { new Rook(Color.Black), new Pawn(Color.Black),   new Pawn(Color.Black),   new Pawn(Color.White), new Pawn(Color.Black) , new Pawn(Color.White),   new Pawn(Color.White),   new Pawn(Color.White)},
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { new Pawn(Color.Black), new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black)},
            new Piece[] { new Rook(Color.Black), new Knight(Color.Black), new Bishop(Color.Black), new King(Color.Black), new Queen(Color.Black), new Bishop(Color.Black), new Knight(Color.Black), new Rook(Color.Black)}
    };
    Piece[][] matrix8 = new Piece[][] {
            new Piece[] { new Rook(Color.White), new Knight(Color.White), new Bishop(Color.White), new King(Color.White), new Queen(Color.White), new Bishop(Color.White), new Knight(Color.White), new Rook(Color.White)},
            new Piece[] { new Rook(Color.Black), new Pawn(Color.Black),   new Pawn(Color.Black),   new Pawn(Color.White), new Pawn(Color.Black) , new Pawn(Color.White),   new Pawn(Color.White),   new Pawn(Color.White)},
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { new Pawn(Color.Black), new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black)},
            new Piece[] { new Rook(Color.Black), new Knight(Color.Black), new Bishop(Color.Black), new King(Color.Black), new Queen(Color.Black), new Bishop(Color.Black), new Knight(Color.Black), new Rook(Color.Black)}
    };
    Piece[][] matrix9 = new Piece[][] {
            new Piece[] { new Rook(Color.White), new Knight(Color.White), new Bishop(Color.White), new Pawn(Color.White), new Queen(Color.White), new Bishop(Color.White), new Knight(Color.White), new Rook(Color.White)},
            new Piece[] { new Rook(Color.Black), new Pawn(Color.Black),   new Pawn(Color.Black),   new Pawn(Color.White), new Pawn(Color.Black) , new Pawn(Color.White),   new Pawn(Color.White),   new Pawn(Color.White)},
            new Piece[] { null, new Pawn(Color.White), null, null, null, null, null, null },
            new Piece[] { null, new King(Color.White), null, new Queen(Color.Black), null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { new Rook(Color.Black), null, null, new Bishop(Color.Black), null, null, null, null },
            new Piece[] { new Pawn(Color.Black), new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black)},
            new Piece[] { new Rook(Color.Black), new Knight(Color.Black), new Bishop(Color.Black), new King(Color.Black), new Queen(Color.Black), new Bishop(Color.Black), new Knight(Color.Black), new Rook(Color.Black)}
    };
    Piece[][] matrix10 = new Piece[][] {
            new Piece[] { new Rook(Color.White), new Knight(Color.White), new Bishop(Color.White), new Pawn(Color.White), new Queen(Color.White), new Bishop(Color.White), new Knight(Color.White), new Rook(Color.White)},
            new Piece[] { new Rook(Color.Black), new Pawn(Color.Black),   new Pawn(Color.Black),   new Pawn(Color.White), new Pawn(Color.Black) , new Pawn(Color.White),   new Pawn(Color.White),   new Pawn(Color.White)},
            new Piece[] { null, null, new Queen(Color.Black), null, null, null, null, null },
            new Piece[] { new King(Color.White), null, null, null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, new Bishop(Color.Black), null, null, null, null, null },
            new Piece[] { new Pawn(Color.Black), new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black)},
            new Piece[] { new Rook(Color.Black), new Knight(Color.Black), new Bishop(Color.Black), new King(Color.Black), new Queen(Color.Black), new Bishop(Color.Black), new Knight(Color.Black), new Rook(Color.Black)}
    };
    Piece[][] matrix11 = new Piece[][] {
            new Piece[] { new Rook(Color.White), new Knight(Color.White), new Bishop(Color.White), new Pawn(Color.White), new Queen(Color.White), new Bishop(Color.White), new Knight(Color.White), new Rook(Color.White)},
            new Piece[] { new Rook(Color.Black), new Pawn(Color.Black),   new Pawn(Color.Black),   new Pawn(Color.White), new Pawn(Color.Black) , new Pawn(Color.White),   new Pawn(Color.White),   new Pawn(Color.White)},
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, new King(Color.White), null, new Knight(Color.Black), null, null, null, null },
            new Piece[] { null, null, null, new Knight(Color.Black), null, null, null, null },
            new Piece[] { new Rook(Color.Black), new Rook(Color.Black), new Rook(Color.Black), null, null, null, null, null },
            new Piece[] { new Pawn(Color.Black), new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black)},
            new Piece[] { new Rook(Color.Black), new Knight(Color.Black), new Bishop(Color.Black), new King(Color.Black), new Queen(Color.Black), new Bishop(Color.Black), new Knight(Color.Black), new Rook(Color.Black)}
    };
    Piece[][] matrix12 = new Piece[][] {
            new Piece[] { new Rook(Color.White), new Knight(Color.White), new Bishop(Color.White), new Pawn(Color.White), new Queen(Color.White), new Bishop(Color.White), new Knight(Color.White), new Rook(Color.White)},
            new Piece[] { new Rook(Color.Black), new Pawn(Color.Black),   new Pawn(Color.Black),   new Pawn(Color.White), new Pawn(Color.Black) , new Pawn(Color.White),   new Pawn(Color.White),   new Pawn(Color.White)},
            new Piece[] { null, null, null, new Knight(Color.Black), null, null, null, null },
            new Piece[] { null, new King(Color.White), null, new Rook(Color.Black), null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { new Rook(Color.Black), null, null, null, null, null, null, null },
            new Piece[] { new Pawn(Color.Black), new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black)},
            new Piece[] { new Rook(Color.Black), new Knight(Color.Black), new Bishop(Color.Black), new King(Color.Black), new Queen(Color.Black), new Bishop(Color.Black), new Knight(Color.Black), new Rook(Color.Black)}
    };
    Piece[][] matrix13 = new Piece[][] {
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, null, null, new Rook(Color.White), null, null, null },
            new Piece[] { null, null, null, null, null, null, new King(Color.Black), null },

            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, new Rook(Color.White), null },
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null }
    };
    Piece[][] matrix14 = new Piece[][] {
            new Piece[] { null, new King(Color.Black), null, null, null, null, null, null },
            new Piece[] { null, new Queen(Color.White), null, null, null, null, null, null },
            new Piece[] { null, null, new King(Color.White), null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null },

            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null }
    };
    Piece[][] matrix15 = new Piece[][] {
            new Piece[] { new Rook(Color.White), new Knight(Color.White), new Bishop(Color.White), new King(Color.White), new Queen(Color.White), new Bishop(Color.White), null, new Rook(Color.White)},
            new Piece[] { new Rook(Color.White), new Pawn(Color.White),   new Pawn(Color.White),   new Pawn(Color.White), null                  , new Pawn(Color.White),   new Pawn(Color.White),   new Pawn(Color.White)},
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, new Bishop(Color.Black), null },

            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { new Pawn(Color.Black), new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black)},
            new Piece[] { new Rook(Color.Black), new Knight(Color.Black), new Bishop(Color.Black), new King(Color.Black), new Queen(Color.Black), new Bishop(Color.Black), new Knight(Color.Black), new Rook(Color.Black)}
    };
    Piece[][] matrix16 = new Piece[][] {
            new Piece[] { new Rook(Color.White), new Knight(Color.White), new Bishop(Color.White), new King(Color.White), new Queen(Color.White), new Bishop(Color.White), new Knight(Color.White), new Rook(Color.White)},
            new Piece[] { new Rook(Color.White), new Pawn(Color.White),   new Pawn(Color.White),   new Pawn(Color.White), null                  , new Pawn(Color.White),   new Pawn(Color.White),   new Pawn(Color.White)},
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, new Bishop(Color.Black), null },

            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { new Pawn(Color.Black), new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black)},
            new Piece[] { new Rook(Color.Black), new Knight(Color.Black), new Bishop(Color.Black), new King(Color.Black), new Queen(Color.Black), new Bishop(Color.Black), new Knight(Color.Black), new Rook(Color.Black)}
    };
    Piece[][] matrix17 = new Piece[][] {
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { new King(Color.Black), null, null, new Queen(Color.Black), null, null, null, null },

            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, new Knight(Color.White), null, null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null }
    };
    Piece[][] matrix18 = new Piece[][] {
            new Piece[] { null, new King(Color.Black), null, null, null, null, null, null },
            new Piece[] { null, new Queen(Color.White), null, null, new Rook(Color.Black), null, null, null },
            new Piece[] { null, null, new King(Color.White), null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null },

            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null }
    };
    Piece[][] matrix19 = new Piece[][] {
            new Piece[] { null, new King(Color.Black), null, null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, null, null, null, new Rook(Color.Black), null, null },
            new Piece[] { null, new Queen(Color.White), null, null, null, null, null, null },

            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null }
    };
    Piece[][] matrix20 = new Piece[][] {
            new Piece[] { new Rook(Color.Black), new King(Color.Black), new Rook(Color.Black), null, null, null, null, null },
            new Piece[] { new Pawn(Color.Black), new Pawn(Color.Black), null, null, null, null, null, null },
            new Piece[] { null, null, null,  new Queen(Color.White), null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null },

            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null }
    };
    Piece[][] matrix21 = new Piece[][] {
            new Piece[] { null, new King(Color.Black),  null, null, null, null, null, null },
            new Piece[] { null, new Queen(Color.White), null, null, null, null, null, null },
            new Piece[] { null, null, new King(Color.White), null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null },

            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null }
    };
    Piece[][] matrix22 = new Piece[][] {
            new Piece[] { new Rook(Color.Black), null, null, null, null, new Rook(Color.Black), null, new King(Color.Black) },
            new Piece[] { null, new Pawn(Color.Black), null, null, null, new Pawn(Color.Black), new Pawn(Color.Black), new Queen(Color.White) },
            new Piece[] { new Pawn(Color.Black), new Queen(Color.Black), null, null, null, new Bishop(Color.Black), null, null },
            new Piece[] { null, null, null, null, null, null, null, null },

            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { new Pawn(Color.White), null, null, new Bishop(Color.White), null, null, null, null },
            new Piece[] { null, new Pawn(Color.White), null, null, null, new Pawn(Color.White), new Pawn(Color.White), new Pawn(Color.White) },
            new Piece[] { null, null, null, null, null, null, null, null }
    };
    Piece[][] matrix23 = new Piece[][] {
            new Piece[] { new King(Color.White), null, new Rook(Color.White), null, null, new Rook(Color.White), null, null },
            new Piece[] { new Pawn(Color.White), new Pawn(Color.White), null, null, null, null, new Pawn(Color.White), null },
            new Piece[] { null, null, null, null, null, null, null, new Pawn(Color.White) },
            new Piece[] { null, null, null, null, null, null, null, null },

            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, new Pawn(Color.Black), new Pawn(Color.White), null, null, null, null, null },
            new Piece[] { new Pawn(Color.Black), new Queen(Color.White), new Pawn(Color.Black), null, null, null, null, null },
            new Piece[] { null, new King(Color.Black), null, null, new Rook(Color.Black), null, null, new Rook(Color.Black) }
    };
    Piece[][] matrix24 = new Piece[][] {
            new Piece[] { new Rook(Color.White), new Knight(Color.White), new Bishop(Color.White), null, null, new Bishop(Color.White), new Knight(Color.White), new Rook(Color.White) },
            new Piece[] { null, null, new Pawn(Color.White), null, null, null, new Pawn(Color.White), null },
            new Piece[] { new Pawn(Color.White), null, null, new Rook(Color.Black), null, new Pawn(Color.White), null, new Pawn(Color.White) },
            new Piece[] { null, new Pawn(Color.White), null, null, null, null, null, null },

            new Piece[] { new King(Color.White), null, new Knight(Color.Black), null, null, null, null, null },
            new Piece[] { new Pawn(Color.Black), new Pawn(Color.Black), null, null, null, new Bishop(Color.Black), null, null },
            new Piece[] { null, null, new Pawn(Color.Black), null, null, new Pawn(Color.Black), new Pawn(Color.Black), new Pawn(Color.Black) },
            new Piece[] { null, null, null, null, null, null, null, null }
    };
    Piece[][] matrix25 = new Piece[][] {
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, new Pawn(Color.White), null, null, null, null, null, null },

            new Piece[] { new King(Color.White), null, new Knight(Color.Black), null, null, null, null, null },
            new Piece[] { new Pawn(Color.Black), new Pawn(Color.Black), null, null, null, null, null, null },
            new Piece[] { null, null, new Pawn(Color.Black), null, null, null, null, null },
            new Piece[] { null, new King(Color.Black), null, null, null, null, null, null }
    };
    Piece[][] matrix26 = new Piece[][] {
            new Piece[] { new Rook(Color.White), new Knight(Color.White), new Bishop(Color.White), new King(Color.White), null, null, new Knight(Color.White), new Rook(Color.White)},
            new Piece[] { new Pawn(Color.White), new Pawn(Color.White),new Pawn(Color.White),new Pawn(Color.White),new Pawn(Color.White),new Pawn(Color.White),new Pawn(Color.White),new Pawn(Color.White)},
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { new Pawn(Color.Black), new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black)},
            new Piece[] { new Rook(Color.Black), new Knight(Color.Black), new Bishop(Color.Black), new King(Color.Black), new Queen(Color.Black), new Bishop(Color.Black), new Knight(Color.Black), new Rook(Color.Black)}
    };
    Piece[][] matrix27 = new Piece[][] {
            new Piece[] { null, null, null, new King(Color.White), null, null, new Rook(Color.Black), null },
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, null, new King(Color.Black), null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null },

            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null }
    };
    Piece[][] matrix28 = new Piece[][] {
            new Piece[] { null, null, new King(Color.White), null, new Queen(Color.Black), null, null, null },
            new Piece[] { null, null, null, null, null, null, new Rook(Color.Black), null },
            new Piece[] { null, null, null, new King(Color.Black), null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null },

            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null }
    };
    Piece[][] matrix29 = new Piece[][] {
            new Piece[] { null, null, new Queen(Color.Black), null, null, null, new King(Color.White), null },
            new Piece[] { null, null, null, null, null, new Pawn(Color.White), new Pawn(Color.White), new Pawn(Color.White) },
            new Piece[] { null, null, null, null, null, null, new Bishop(Color.White), null },
            new Piece[] { null, null, null, null, null, null, null, null },

            new Piece[] { null, new Pawn(Color.White), null, null, null, null, null, null },
            new Piece[] { null, new Pawn(Color.Black), new Pawn(Color.White), null, null, null, null, null },
            new Piece[] { new Pawn(Color.Black), null, null, new King(Color.Black), null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null }
    };
    Piece[][] matrix30 = new Piece[][] {
            new Piece[] { null, null, null, null, null, null, new Rook(Color.White), new King(Color.White) },
            new Piece[] { null, null, null, null, null, new Knight(Color.Black), new Pawn(Color.White), null },
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null },

            new Piece[] { null, new Pawn(Color.White), null, null, null, null, null, null },
            new Piece[] { null, new Pawn(Color.Black), null, new Bishop(Color.Black), null, null, null, null },
            new Piece[] { new Pawn(Color.Black), null, null, new King(Color.Black), null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null }
    };
    Piece[][] matrix31 = new Piece[][] {
            new Piece[] { new Rook(Color.White), new King(Color.White), new Bishop(Color.White), null, null, null, null, null },
            new Piece[] { new Pawn(Color.White), null, null, new Pawn(Color.White), null, null, null, null },
            new Piece[] { null, null, new Pawn(Color.Black), null, null, null, null, null },
            new Piece[] { new Bishop(Color.Black), new Bishop(Color.Black), null, null, new Queen(Color.Black), null, null, null },

            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null },
            new Piece[] { null, null, null, new King(Color.Black), null, null, null, null },
            new Piece[] { null, null, null, null, null, null, null, null }
    };
    Board board1 = new Board();
    Board board2 = new Board(matrix);
    Board board3 = new Board(matrix1);
    Board board4 = new Board(matrix2);
    Board board5 = new Board(matrix5);
    Board board7 = new Board(matrix7);
    Board board8 = new Board(matrix8);
    Board board9 = new Board(matrix9);
    Board board10 = new Board(matrix10);
    Board board11 = new Board(matrix11);
    Board board12 = new Board(matrix12);
    Board board13 = new Board(matrix13);
    Board board14 = new Board(matrix14);
    Board board15 = new Board(matrix15);
    Board board16 = new Board(matrix16);
    Board board17 = new Board(matrix17);
    Board board18 = new Board(matrix18);
    Board board19 = new Board(matrix19);
    Board board20 = new Board(matrix20);
    Board board21 = new Board(matrix21);
    Board board22 = new Board(matrix22);
    Board board23 = new Board(matrix23);
    Board board24 = new Board(matrix24);
    Board board25 = new Board(matrix25);
    Board board27 = new Board(matrix27);
    Board board28 = new Board(matrix28);
    Board board29 = new Board(matrix29);
    Board board30 = new Board(matrix30);
    Board board31 = new Board(matrix31);


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

    @Test
    public void t6() {
        assertFalse(board5.isCheckMate(Color.White));
    }

    @Test
    public void t7() {
        assertTrue(board7.isCheckMate(Color.White));
    }

    @Test
    public void t8() {
        assertTrue(board8.isCheckMate(Color.White));
    }

    @Test
    public void t9() {
        assertFalse(board9.isCheckMate(Color.White));
    }

    @Test
    public void t10() {
        assertTrue(board10.isCheckMate(Color.White));
    }

    @Test
    public void t11() {
        assertTrue(board11.isCheckMate(Color.White));
    }

    @Test
    public void t12() {
        assertFalse(board12.isCheckMate(Color.White));
    }

    @Test
    public void t13() {
        assertFalse(board13.isCheckMate(Color.Black));
    }

    @Test
    public void t14() {
        assertTrue(board14.isCheckMate(Color.Black));
    }

    @Test
    public void t15() {
        assertFalse(board15.isCheckMate(Color.White));
    }

    @Test
    public void t16() {
        assertFalse(board16.isCheckMate(Color.White));
    }

    @Test
    public void t17() {
        assertFalse(board17.isCheckMate(Color.Black));
    }

    @Test
    public void t18() {
        assertFalse(board18.isCheckMate(Color.Black));
    }

    @Test
    public void t19() {
        assertFalse(board19.isCheckMate(Color.Black));
    }

    @Test
    public void t20() {
        assertFalse(board20.isCheckMate(Color.Black));
    }

    @Test
    public void t21() {
        assertTrue(board21.isCheckMate(Color.Black));
    }

    @Test
    public void t22() {
        assertTrue(board22.isCheckMate(Color.Black));
    }

    @Test
    public void t23() {
        assertTrue(board23.isCheckMate(Color.Black));
    }

    @Test
    public void t24() {
        assertTrue(board24.isCheckMate(Color.White));
    }

    @Test
    public void t25() {
        assertTrue(board25.isCheckMate(Color.White));
    }

    @Test
    public void t27() {
        assertTrue(board27.isCheckMate(Color.White));
    }

    @Test
    public void t28() {
        assertTrue(board28.isCheckMate(Color.White));
    }

    @Test
    public void t29() {
        assertTrue(board29.isCheckMate(Color.White));
    }

    @Test
    public void t30() {
        assertTrue(board30.isCheckMate(Color.White));
    }

    @Test
    public void t31() {
        assertFalse(board31.isCheckMate(Color.White));
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
