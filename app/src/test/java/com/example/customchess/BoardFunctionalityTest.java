//package com.example.customchess;
//
//import com.example.customchess.engine.Board;
//import com.example.customchess.engine.exceptions.CheckMateException;
//import com.example.customchess.engine.figures.Bishop;
//import com.example.customchess.engine.figures.King;
//import com.example.customchess.engine.figures.Knight;
//import com.example.customchess.engine.figures.Pawn;
//import com.example.customchess.engine.figures.Piece;
//import com.example.customchess.engine.figures.Queen;
//import com.example.customchess.engine.figures.Rook;
//import com.example.customchess.engine.misc.Color;
//import com.example.customchess.engine.misc.Verticals;
//import com.example.customchess.engine.movements.BoardPosition;
//import com.example.customchess.engine.movements.Position;
//
//import org.junit.Assert;
//import org.junit.Test;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.fail;
//
//public class BoardFunctionalityTest extends FigureMoveTest {
//    Piece[][] matrix = new Piece[][] {
//            new Piece[] { new Rook(Color.White), new Knight(Color.White), new Bishop(Color.White), new King(Color.White), new Queen(Color.White), new Bishop(Color.White), new Knight(Color.White), new Rook(Color.White)},
//            new Piece[] { new Pawn(Color.White), new Pawn(Color.White),new Pawn(Color.White),new Pawn(Color.White),new Pawn(Color.White),new Pawn(Color.White),new Pawn(Color.White),new Pawn(Color.White)},
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { new Pawn(Color.Black), new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black)},
//            new Piece[] { new Rook(Color.Black), new Knight(Color.Black), new Bishop(Color.Black), new King(Color.Black), new Queen(Color.Black), new Bishop(Color.Black), new Knight(Color.Black), new Rook(Color.Black)}
//    };
//    Piece[][] matrix1 = new Piece[][] {
//            new Piece[] { new Rook(Color.White), new Knight(Color.White), new Bishop(Color.White), new King(Color.White), new Queen(Color.White), new Bishop(Color.White), new Knight(Color.White), new Rook(Color.White)},
//            new Piece[] { new Pawn(Color.White), new Pawn(Color.White),new Pawn(Color.White),      null,                  null                  ,new Pawn(Color.White),new Pawn(Color.White),new Pawn(Color.White)},
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { null, null, null, new Queen(Color.Black), null, null, null, new Bishop(Color.Black) },
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { new Pawn(Color.Black), new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black)},
//            new Piece[] { new Rook(Color.Black), new Knight(Color.Black), new Bishop(Color.Black), new King(Color.Black), new Queen(Color.Black), new Bishop(Color.Black), new Knight(Color.Black), new Rook(Color.Black)}
//    };
//    Piece[][] matrix2 = new Piece[][] {
//            new Piece[] { new Rook(Color.White), new Knight(Color.White), new Bishop(Color.White), new King(Color.White), new Queen(Color.White), new Bishop(Color.White), new Knight(Color.White), new Rook(Color.White)},
//            new Piece[] { new Pawn(Color.White), new Pawn(Color.White),   new Pawn(Color.White),   new Pawn(Color.White), null                  , new Pawn(Color.White), new Pawn(Color.White),new Pawn(Color.White)},
//            new Piece[] { null, null, null, null, null, null, null, new Queen(Color.White) },
//            new Piece[] { null, null, null, null, null, null, null, null },
//
//            new Piece[] { null, null, null, new Queen(Color.Black), null, null, null, new Bishop(Color.Black) },
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { new Pawn(Color.Black), new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black)},
//            new Piece[] { new Rook(Color.Black), new Knight(Color.Black), new Bishop(Color.Black), new King(Color.Black), new Queen(Color.Black), new Bishop(Color.Black), new Knight(Color.Black), new Rook(Color.Black)}
//    };
//    Piece[][] matrix5 = new Piece[][] {
//            new Piece[] { new Rook(Color.White), new Knight(Color.White), new Bishop(Color.White), new King(Color.White), new Queen(Color.White), new Bishop(Color.White), new Knight(Color.White), new Rook(Color.White)},
//            new Piece[] { new Pawn(Color.White), new Pawn(Color.White),   new Pawn(Color.White),   new Pawn(Color.White), new Pawn(Color.White) , new Pawn(Color.White),new Pawn(Color.White),new Pawn(Color.White)},
//            new Piece[] { null, null, new Knight(Color.Black), null, null, null, null, null },
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { null, null, null, new Queen(Color.Black), null, null, null, new Bishop(Color.Black) },
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { new Pawn(Color.Black), new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black)},
//            new Piece[] { new Rook(Color.Black), new Knight(Color.Black), new Bishop(Color.Black), new King(Color.Black), new Queen(Color.Black), new Bishop(Color.Black), new Knight(Color.Black), new Rook(Color.Black)}
//    };
//    Piece[][] matrix7 = new Piece[][] {
//            new Piece[] { new King(Color.White), new Knight(Color.White), new Bishop(Color.White), new Pawn(Color.White), new Queen(Color.White), new Bishop(Color.White), new Knight(Color.White), new Rook(Color.White)},
//            new Piece[] { new Rook(Color.Black), new Pawn(Color.Black),   new Pawn(Color.Black),   new Pawn(Color.White), new Pawn(Color.Black) , new Pawn(Color.White),   new Pawn(Color.White),   new Pawn(Color.White)},
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { new Pawn(Color.Black), new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black)},
//            new Piece[] { new Rook(Color.Black), new Knight(Color.Black), new Bishop(Color.Black), new King(Color.Black), new Queen(Color.Black), new Bishop(Color.Black), new Knight(Color.Black), new Rook(Color.Black)}
//    };
//    Piece[][] matrix8 = new Piece[][] {
//            new Piece[] { new Rook(Color.White), new Knight(Color.White), new Bishop(Color.White), new King(Color.White), new Queen(Color.White), new Bishop(Color.White), new Knight(Color.White), new Rook(Color.White)},
//            new Piece[] { new Rook(Color.Black), new Pawn(Color.Black),   new Pawn(Color.Black),   new Pawn(Color.White), new Pawn(Color.Black) , new Pawn(Color.White),   new Pawn(Color.White),   new Pawn(Color.White)},
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { new Pawn(Color.Black), new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black)},
//            new Piece[] { new Rook(Color.Black), new Knight(Color.Black), new Bishop(Color.Black), new King(Color.Black), new Queen(Color.Black), new Bishop(Color.Black), new Knight(Color.Black), new Rook(Color.Black)}
//    };
//    Piece[][] matrix9 = new Piece[][] {
//            new Piece[] { new Rook(Color.White), new Knight(Color.White), new Bishop(Color.White), new Pawn(Color.White), new Queen(Color.White), new Bishop(Color.White), new Knight(Color.White), new Rook(Color.White)},
//            new Piece[] { new Rook(Color.Black), new Pawn(Color.Black),   new Pawn(Color.Black),   new Pawn(Color.White), new Pawn(Color.Black) , new Pawn(Color.White),   new Pawn(Color.White),   new Pawn(Color.White)},
//            new Piece[] { null, new Pawn(Color.White), null, null, null, null, null, null },
//            new Piece[] { null, new King(Color.White), null, new Queen(Color.Black), null, null, null, null },
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { new Rook(Color.Black), null, null, new Bishop(Color.Black), null, null, null, null },
//            new Piece[] { new Pawn(Color.Black), new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black)},
//            new Piece[] { new Rook(Color.Black), new Knight(Color.Black), new Bishop(Color.Black), new King(Color.Black), new Queen(Color.Black), new Bishop(Color.Black), new Knight(Color.Black), new Rook(Color.Black)}
//    };
//    Piece[][] matrix10 = new Piece[][] {
//            new Piece[] { new Rook(Color.White), new Knight(Color.White), new Bishop(Color.White), new Pawn(Color.White), new Queen(Color.White), new Bishop(Color.White), new Knight(Color.White), new Rook(Color.White)},
//            new Piece[] { new Rook(Color.Black), new Pawn(Color.Black),   new Pawn(Color.Black),   new Pawn(Color.White), new Pawn(Color.Black) , new Pawn(Color.White),   new Pawn(Color.White),   new Pawn(Color.White)},
//            new Piece[] { null, null, new Queen(Color.Black), null, null, null, null, null },
//            new Piece[] { new King(Color.White), null, null, null, null, null, null, null },
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { null, null, new Bishop(Color.Black), null, null, null, null, null },
//            new Piece[] { new Pawn(Color.Black), new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black)},
//            new Piece[] { new Rook(Color.Black), new Knight(Color.Black), new Bishop(Color.Black), new King(Color.Black), new Queen(Color.Black), new Bishop(Color.Black), new Knight(Color.Black), new Rook(Color.Black)}
//    };
//    Piece[][] matrix11 = new Piece[][] {
//            new Piece[] { new Rook(Color.White), new Knight(Color.White), new Bishop(Color.White), new Pawn(Color.White), new Queen(Color.White), new Bishop(Color.White), new Knight(Color.White), new Rook(Color.White)},
//            new Piece[] { new Rook(Color.Black), new Pawn(Color.Black),   new Pawn(Color.Black),   new Pawn(Color.White), new Pawn(Color.Black) , new Pawn(Color.White),   new Pawn(Color.White),   new Pawn(Color.White)},
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { null, new King(Color.White), null, new Knight(Color.Black), null, null, null, null },
//            new Piece[] { null, null, null, new Knight(Color.Black), null, null, null, null },
//            new Piece[] { new Rook(Color.Black), new Rook(Color.Black), new Rook(Color.Black), null, null, null, null, null },
//            new Piece[] { new Pawn(Color.Black), new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black)},
//            new Piece[] { new Rook(Color.Black), new Knight(Color.Black), new Bishop(Color.Black), new King(Color.Black), new Queen(Color.Black), new Bishop(Color.Black), new Knight(Color.Black), new Rook(Color.Black)}
//    };
//    Piece[][] matrix12 = new Piece[][] {
//            new Piece[] { new Rook(Color.White), new Knight(Color.White), new Bishop(Color.White), new Pawn(Color.White), new Queen(Color.White), new Bishop(Color.White), new Knight(Color.White), new Rook(Color.White)},
//            new Piece[] { new Rook(Color.Black), new Pawn(Color.Black),   new Pawn(Color.Black),   new Pawn(Color.White), new Pawn(Color.Black) , new Pawn(Color.White),   new Pawn(Color.White),   new Pawn(Color.White)},
//            new Piece[] { null, null, null, new Knight(Color.Black), null, null, null, null },
//            new Piece[] { null, new King(Color.White), null, new Rook(Color.Black), null, null, null, null },
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { new Rook(Color.Black), null, null, null, null, null, null, null },
//            new Piece[] { new Pawn(Color.Black), new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black)},
//            new Piece[] { new Rook(Color.Black), new Knight(Color.Black), new Bishop(Color.Black), new King(Color.Black), new Queen(Color.Black), new Bishop(Color.Black), new Knight(Color.Black), new Rook(Color.Black)}
//    };
//    Piece[][] matrix13 = new Piece[][] {
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { null, null, null, null, new Rook(Color.White), null, null, null },
//            new Piece[] { null, null, null, null, null, null, new King(Color.Black), null },
//
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { null, null, null, null, null, null, new Rook(Color.White), null },
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { null, null, null, null, null, null, null, null }
//    };
//    Piece[][] matrix14 = new Piece[][] {
//            new Piece[] { null, new King(Color.Black), null, null, null, null, null, null },
//            new Piece[] { null, new Queen(Color.White), null, null, null, null, null, null },
//            new Piece[] { null, null, new King(Color.White), null, null, null, null, null },
//            new Piece[] { null, null, null, null, null, null, null, null },
//
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { null, null, null, null, null, null, null, null }
//    };
//    Piece[][] matrix15 = new Piece[][] {
//            new Piece[] { new Rook(Color.White), new Knight(Color.White), new Bishop(Color.White), new King(Color.White), new Queen(Color.White), new Bishop(Color.White), null, new Rook(Color.White)},
//            new Piece[] { new Rook(Color.White), new Pawn(Color.White),   new Pawn(Color.White),   new Pawn(Color.White), null                  , new Pawn(Color.White),   new Pawn(Color.White),   new Pawn(Color.White)},
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { null, null, null, null, null, null, new Bishop(Color.Black), null },
//
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { new Pawn(Color.Black), new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black)},
//            new Piece[] { new Rook(Color.Black), new Knight(Color.Black), new Bishop(Color.Black), new King(Color.Black), new Queen(Color.Black), new Bishop(Color.Black), new Knight(Color.Black), new Rook(Color.Black)}
//    };
//    Piece[][] matrix16 = new Piece[][] {
//            new Piece[] { new Rook(Color.White), new Knight(Color.White), new Bishop(Color.White), new King(Color.White), new Queen(Color.White), new Bishop(Color.White), new Knight(Color.White), new Rook(Color.White)},
//            new Piece[] { new Rook(Color.White), new Pawn(Color.White),   new Pawn(Color.White),   new Pawn(Color.White), null                  , new Pawn(Color.White),   new Pawn(Color.White),   new Pawn(Color.White)},
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { null, null, null, null, null, null, new Bishop(Color.Black), null },
//
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { new Pawn(Color.Black), new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black)},
//            new Piece[] { new Rook(Color.Black), new Knight(Color.Black), new Bishop(Color.Black), new King(Color.Black), new Queen(Color.Black), new Bishop(Color.Black), new Knight(Color.Black), new Rook(Color.Black)}
//    };
//    Piece[][] matrix17 = new Piece[][] {
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { new King(Color.Black), null, null, new Queen(Color.Black), null, null, null, null },
//
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { null, new Knight(Color.White), null, null, null, null, null, null },
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { null, null, null, null, null, null, null, null }
//    };
//    Piece[][] matrix18 = new Piece[][] {
//            new Piece[] { null, new King(Color.Black), null, null, null, null, null, null },
//            new Piece[] { null, new Queen(Color.White), null, null, new Rook(Color.Black), null, null, null },
//            new Piece[] { null, null, new King(Color.White), null, null, null, null, null },
//            new Piece[] { null, null, null, null, null, null, null, null },
//
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { null, null, null, null, null, null, null, null }
//    };
//    Piece[][] matrix19 = new Piece[][] {
//            new Piece[] { null, new King(Color.Black), null, null, null, null, null, null },
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { null, null, null, null, null, new Rook(Color.Black), null, null },
//            new Piece[] { null, new Queen(Color.White), null, null, null, null, null, null },
//
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { null, null, null, null, null, null, null, null }
//    };
//    Piece[][] matrix20 = new Piece[][] {
//            new Piece[] { new Rook(Color.Black), new King(Color.Black), new Rook(Color.Black), null, null, null, null, null },
//            new Piece[] { new Pawn(Color.Black), new Pawn(Color.Black), null, null, null, null, null, null },
//            new Piece[] { null, null, null,  new Queen(Color.White), null, null, null },
//            new Piece[] { null, null, null, null, null, null, null, null },
//
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { null, null, null, null, null, null, null, null }
//    };
//    Piece[][] matrix21 = new Piece[][] {
//            new Piece[] { null, new King(Color.Black),  null, null, null, null, null, null },
//            new Piece[] { null, new Queen(Color.White), null, null, null, null, null, null },
//            new Piece[] { null, null, new King(Color.White), null, null, null, null, null },
//            new Piece[] { null, null, null, null, null, null, null, null },
//
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { null, null, null, null, null, null, null, null }
//    };
//    Piece[][] matrix22 = new Piece[][] {
//            new Piece[] { new Rook(Color.Black), null, null, null, null, new Rook(Color.Black), null, new King(Color.Black) },
//            new Piece[] { null, new Pawn(Color.Black), null, null, null, new Pawn(Color.Black), new Pawn(Color.Black), new Queen(Color.White) },
//            new Piece[] { new Pawn(Color.Black), new Queen(Color.Black), null, null, null, new Bishop(Color.Black), null, null },
//            new Piece[] { null, null, null, null, null, null, null, null },
//
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { new Pawn(Color.White), null, null, new Bishop(Color.White), null, null, null, null },
//            new Piece[] { null, new Pawn(Color.White), null, null, null, new Pawn(Color.White), new Pawn(Color.White), new Pawn(Color.White) },
//            new Piece[] { null, null, null, null, null, null, null, null }
//    };
//    Piece[][] matrix23 = new Piece[][] {
//            new Piece[] { new King(Color.White), null, new Rook(Color.White), null, null, new Rook(Color.White), null, null },
//            new Piece[] { new Pawn(Color.White), new Pawn(Color.White), null, null, null, null, new Pawn(Color.White), null },
//            new Piece[] { null, null, null, null, null, null, null, new Pawn(Color.White) },
//            new Piece[] { null, null, null, null, null, null, null, null },
//
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { null, new Pawn(Color.Black), new Pawn(Color.White), null, null, null, null, null },
//            new Piece[] { new Pawn(Color.Black), new Queen(Color.White), new Pawn(Color.Black), null, null, null, null, null },
//            new Piece[] { null, new King(Color.Black), null, null, new Rook(Color.Black), null, null, new Rook(Color.Black) }
//    };
//    Piece[][] matrix24 = new Piece[][] {
//            new Piece[] { new Rook(Color.White), new Knight(Color.White), new Bishop(Color.White), null, null, new Bishop(Color.White), new Knight(Color.White), new Rook(Color.White) },
//            new Piece[] { null, null, new Pawn(Color.White), null, null, null, new Pawn(Color.White), null },
//            new Piece[] { new Pawn(Color.White), null, null, new Rook(Color.Black), null, new Pawn(Color.White), null, new Pawn(Color.White) },
//            new Piece[] { null, new Pawn(Color.White), null, null, null, null, null, null },
//
//            new Piece[] { new King(Color.White), null, new Knight(Color.Black), null, null, null, null, null },
//            new Piece[] { new Pawn(Color.Black), new Pawn(Color.Black), null, null, null, new Bishop(Color.Black), null, null },
//            new Piece[] { null, null, new Pawn(Color.Black), null, null, new Pawn(Color.Black), new Pawn(Color.Black), new Pawn(Color.Black) },
//            new Piece[] { null, null, null, null, null, null, null, null }
//    };
//    Piece[][] matrix25 = new Piece[][] {
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { null, new Pawn(Color.White), null, null, null, null, null, null },
//
//            new Piece[] { new King(Color.White), null, new Knight(Color.Black), null, null, null, null, null },
//            new Piece[] { new Pawn(Color.Black), new Pawn(Color.Black), null, null, null, null, null, null },
//            new Piece[] { null, null, new Pawn(Color.Black), null, null, null, null, null },
//            new Piece[] { null, new King(Color.Black), null, null, null, null, null, null }
//    };
//    Piece[][] matrix26 = new Piece[][] {
//            new Piece[] { new Rook(Color.White), new Knight(Color.White), new Bishop(Color.White), new King(Color.White), null, null, new Knight(Color.White), new Rook(Color.White)},
//            new Piece[] { new Pawn(Color.White), new Pawn(Color.White),new Pawn(Color.White),new Pawn(Color.White),new Pawn(Color.White),new Pawn(Color.White),new Pawn(Color.White),new Pawn(Color.White)},
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { new Pawn(Color.Black), new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black),new Pawn(Color.Black)},
//            new Piece[] { new Rook(Color.Black), new Knight(Color.Black), new Bishop(Color.Black), new King(Color.Black), new Queen(Color.Black), new Bishop(Color.Black), new Knight(Color.Black), new Rook(Color.Black)}
//    };
//    Piece[][] matrix27 = new Piece[][] {
//            new Piece[] { null, null, null, new King(Color.White), null, null, new Rook(Color.Black), null },
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { null, null, null, new King(Color.Black), null, null, null, null },
//            new Piece[] { null, null, null, null, null, null, null, null },
//
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { null, null, null, null, null, null, null, null }
//    };
//    Piece[][] matrix28 = new Piece[][] {
//            new Piece[] { null, null, new King(Color.White), null, new Queen(Color.Black), null, null, null },
//            new Piece[] { null, null, null, null, null, null, new Rook(Color.Black), null },
//            new Piece[] { null, null, null, new King(Color.Black), null, null, null, null },
//            new Piece[] { null, null, null, null, null, null, null, null },
//
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { null, null, null, null, null, null, null, null }
//    };
//    Piece[][] matrix29 = new Piece[][] {
//            new Piece[] { null, null, new Queen(Color.Black), null, null, null, new King(Color.White), null },
//            new Piece[] { null, null, null, null, null, new Pawn(Color.White), new Pawn(Color.White), new Pawn(Color.White) },
//            new Piece[] { null, null, null, null, null, null, new Bishop(Color.White), null },
//            new Piece[] { null, null, null, null, null, null, null, null },
//
//            new Piece[] { null, new Pawn(Color.White), null, null, null, null, null, null },
//            new Piece[] { null, new Pawn(Color.Black), new Pawn(Color.White), null, null, null, null, null },
//            new Piece[] { new Pawn(Color.Black), null, null, new King(Color.Black), null, null, null, null },
//            new Piece[] { null, null, null, null, null, null, null, null }
//    };
//    Piece[][] matrix30 = new Piece[][] {
//            new Piece[] { null, null, null, null, null, null, new Rook(Color.White), new King(Color.White) },
//            new Piece[] { null, null, null, null, null, new Knight(Color.Black), new Pawn(Color.White), null },
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { null, null, null, null, null, null, null, null },
//
//            new Piece[] { null, new Pawn(Color.White), null, null, null, null, null, null },
//            new Piece[] { null, new Pawn(Color.Black), null, new Bishop(Color.Black), null, null, null, null },
//            new Piece[] { new Pawn(Color.Black), null, null, new King(Color.Black), null, null, null, null },
//            new Piece[] { null, null, null, null, null, null, null, null }
//    };
//    Piece[][] matrix31 = new Piece[][] {
//            new Piece[] { new Rook(Color.White), new King(Color.White), new Bishop(Color.White), null, null, null, null, null },
//            new Piece[] { new Pawn(Color.White), null, null, new Pawn(Color.White), null, null, null, null },
//            new Piece[] { null, null, new Pawn(Color.Black), null, null, null, null, null },
//            new Piece[] { new Bishop(Color.Black), new Bishop(Color.Black), null, null, new Queen(Color.Black), null, null, null },
//
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { null, null, null, null, null, null, null, null },
//            new Piece[] { null, null, null, new King(Color.Black), null, null, null, null },
//            new Piece[] { null, null, null, null, null, null, null, null }
//    };
//    BoardTest board2 = new BoardTest(matrix);
//    BoardTest board3 = new BoardTest(matrix1);
//    BoardTest board4 = new BoardTest(matrix2);
//    BoardTest board5 = new BoardTest(matrix5);
//    BoardTest board7 = new BoardTest(matrix7);
//    BoardTest board8 = new BoardTest(matrix8);
//    BoardTest board9 = new BoardTest(matrix9);
//    BoardTest board10 = new BoardTest(matrix10);
//    BoardTest board11 = new BoardTest(matrix11);
//    BoardTest board12 = new BoardTest(matrix12);
//    BoardTest board13 = new BoardTest(matrix13);
//    BoardTest board14 = new BoardTest(matrix14);
//    BoardTest board15 = new BoardTest(matrix15);
//    BoardTest board16 = new BoardTest(matrix16);
//    BoardTest board17 = new BoardTest(matrix17);
//    BoardTest board18 = new BoardTest(matrix18);
//    BoardTest board19 = new BoardTest(matrix19);
//    BoardTest board20 = new BoardTest(matrix20);
//    BoardTest board21 = new BoardTest(matrix21);
//    BoardTest board22 = new BoardTest(matrix22);
//    BoardTest board23 = new BoardTest(matrix23);
//    BoardTest board24 = new BoardTest(matrix24);
//    BoardTest board25 = new BoardTest(matrix25);
//    BoardTest board27 = new BoardTest(matrix27);
//    BoardTest board28 = new BoardTest(matrix28);
//    BoardTest board29 = new BoardTest(matrix29);
//    BoardTest board30 = new BoardTest(matrix30);
//    BoardTest board31 = new BoardTest(matrix31);
//
//
//    public void equalBoards(BoardTest b1, BoardTest b2) {
//        assertEquals(b1, b2);
//    }
//
//    @Test
//    public void t3() {
//        assertTrue(board3.original.isPositionUnderAttack(Color.White, e1));
//    }
//
//    @Test
//    public void t4() {
//        assertTrue(board3.original.isCheckMate(Color.White));
//    }
//
//    @Test
//    public void t5() {
//        assertFalse(board4.original.isCheckMate(Color.White));
//    }
//
//    @Test
//    public void t6() {
//        assertFalse(board5.original.isCheckMate(Color.White));
//    }
//
//    @Test
//    public void t7() {
//        assertTrue(board7.original.isCheckMate(Color.White));
//    }
//
//    @Test
//    public void t8() {
//        assertTrue(board8.original.isCheckMate(Color.White));
//    }
//
//    @Test
//    public void t9() {
//        assertFalse(board9.original.isCheckMate(Color.White));
//    }
//
//    @Test
//    public void t10() {
//        assertTrue(board10.original.isCheckMate(Color.White));
//    }
//
//    @Test
//    public void t11() {
//        assertTrue(board11.original.isCheckMate(Color.White));
//    }
//
//    @Test
//    public void t12() {
//        assertFalse(board12.original.isCheckMate(Color.White));
//    }
//
//    @Test
//    public void t13() {
//        assertFalse(board13.original.isCheckMate(Color.Black));
//    }
//
//    @Test
//    public void t14() {
//        assertTrue(board14.original.isCheckMate(Color.Black));
//    }
//
//    @Test
//    public void t15() {
//        assertFalse(board15.original.isCheckMate(Color.White));
//    }
//
//    @Test
//    public void t16() {
//        assertFalse(board16.original.isCheckMate(Color.White));
//    }
//
//    @Test
//    public void t17() {
//        assertFalse(board17.original.isCheckMate(Color.Black));
//    }
//
//    @Test
//    public void t18() {
//        assertFalse(board18.original.isCheckMate(Color.Black));
//    }
//
//    @Test
//    public void t19() {
//        assertFalse(board19.original.isCheckMate(Color.Black));
//    }
//
//    @Test
//    public void t20() {
//        assertFalse(board20.original.isCheckMate(Color.Black));
//    }
//
//    @Test
//    public void t21() {
//        assertTrue(board21.original.isCheckMate(Color.Black));
//    }
//
//    @Test
//    public void t22() {
//        assertTrue(board22.original.isCheckMate(Color.Black));
//    }
//
//    @Test
//    public void t23() {
//        assertTrue(board23.original.isCheckMate(Color.Black));
//    }
//
//    @Test
//    public void t24() {
//        assertTrue(board24.original.isCheckMate(Color.White));
//    }
//
//    @Test
//    public void t25() {
//        assertTrue(board25.original.isCheckMate(Color.White));
//    }
//
//    @Test
//    public void t27() {
//        assertTrue(board27.original.isCheckMate(Color.White));
//    }
//
//    @Test
//    public void t28() {
//        assertTrue(board28.original.isCheckMate(Color.White));
//    }
//
//    @Test
//    public void t29() {
//        assertTrue(board29.original.isCheckMate(Color.White));
//    }
//
//    @Test
//    public void t30() {
//        assertTrue(board30.original.isCheckMate(Color.White));
//    }
//
//    @Test
//    public void t31() {
//        assertFalse(board31.original.isCheckMate(Color.White));
//    }
//
//}