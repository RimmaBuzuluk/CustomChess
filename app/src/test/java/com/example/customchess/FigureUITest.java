package com.example.customchess;

import com.example.customchess.engine.exceptions.CheckMateException;
import com.example.customchess.engine.exceptions.ChessException;
import com.example.customchess.engine.misc.Color;
import com.example.customchess.engine.movements.Movement;
import com.example.customchess.ui.figures.Figure;

import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;


public class FigureUITest {


    @Test
    public void t1() {
        Figure whiteQueen = new Figure.Queen(Color.White);
        assertFalse(Figure.isBlack(whiteQueen.getImageId()));
    }

    @Test
    public void t2() {
        Figure whiteQueen = new Figure.Queen(Color.White);
        assertFalse(Figure.isBlack(whiteQueen.getImageId()));
    }

    @Test
    public void t3() {
        Figure whiteQueen = new Figure.Queen(Color.White);
        assertFalse(Figure.isBlack(whiteQueen.getImageId()));
    }

    @Test
    public void t4() {
        Figure blackPawn = new Figure.Pawn(Color.Black);
        Figure blackQueen = new Figure.Queen(blackPawn.getImageId());
        assertTrue(Figure.isBlack(blackQueen.getImageId()));
    }

    @Test
    public void t5() {
        Figure whitePawn = new Figure.Pawn(Color.White).flip();
        Figure whiteQueen = new Figure.Queen(whitePawn.getImageId());
        assertTrue(Figure.isFlipped(whiteQueen.getImageId()));
    }
}
