package com.example.customchess.ui.board;

import android.content.Context;

import com.example.customchess.R;
import com.example.customchess.engine.misc.Color;
import com.example.customchess.engine.misc.Verticals;
import com.example.customchess.engine.movements.BoardPosition;
import com.example.customchess.engine.movements.Position;
import com.example.customchess.ui.Cage;
import com.example.customchess.ui.figures.Figure;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

public class BlackPlayerViewBoard implements BoardPlayerView {

    private Context context;

    public BlackPlayerViewBoard(Context context) {
        this.context = context;
    }

    @Override
    public List<Cage> getCages() {
        return cagesInit();
    }

    @Override
    public Hashtable<Integer, Figure> getTeamImages() {
        Hashtable<Integer, Figure> teamImages = new Hashtable<>(32);
        Figure whitePawn = new Figure.Pawn(Color.White);
        Figure blackPawn = new Figure.Pawn(Color.Black);
        Figure whiteRook = new Figure.Rook(Color.White);
        Figure blackRook = new Figure.Rook(Color.Black);
        Figure whiteKnight = new Figure.Knight(Color.White);
        Figure blackKnight = new Figure.Knight(Color.Black);
        Figure whiteBishop = new Figure.Bishop(Color.White);
        Figure blackBishop = new Figure.Bishop(Color.Black);
        Figure whiteKing = new Figure.King(Color.White);
        Figure whiteQueen = new Figure.Queen(Color.White);
        Figure blackKing = new Figure.King(Color.Black);
        Figure blackQueen = new Figure.Queen(Color.Black);

        teamImages.put(0,  whiteRook);
        teamImages.put(56, whiteRook);
        teamImages.put(8,  whiteKnight);
        teamImages.put(48, whiteKnight);
        teamImages.put(16, whiteBishop);
        teamImages.put(40, whiteBishop);
        teamImages.put(24, whiteKing);
        teamImages.put(32, whiteQueen);
        for (int i = 0; i < 8; ++i) {
            teamImages.put((1 + i * 8), whitePawn);
        }
        teamImages.put(7,  blackRook);
        teamImages.put(63, blackRook);
        teamImages.put(15, blackKnight);
        teamImages.put(55, blackKnight);
        teamImages.put(23, blackBishop);
        teamImages.put(47, blackBishop);
        teamImages.put(31, blackKing);
        teamImages.put(39, blackQueen);
        for (int i = 0; i < 8; ++i) {
            teamImages.put((6 + i * 8), blackPawn);
        }

        return teamImages;
    }

    public List<Cage> cagesInit() {
        List<Cage> cageList = new ArrayList<>(64);
        List<Position> positionArray = initPositions();
        Iterator<Position> positionIterator = positionArray.iterator();

        for (int j = 0; j < 8; j++) {
            if (j % 2 == 0) {
                initVerticalRow(cageList, positionIterator, 1);
            } else {
                initVerticalRow(cageList, positionIterator, 0);
            }
        }
        return cageList;
    }

    private void initVerticalRow(List<Cage> cageList, Iterator<Position> positionIterator, int vertical) {
        int brown = context.getResources().getColor(R.color.brown);
        int beige = context.getResources().getColor(R.color.beige);

        for (int i = 0; i < 8; i++) {
            if (i % 2 == vertical) {
                cageList.add(new Cage(brown, positionIterator.next()));
            } else {
                cageList.add(new Cage(beige, positionIterator.next()));
            }
        }
    }

    private List<Position> initPositions() {
        List<Position> positionList = new ArrayList<>(64);
        Verticals[] verticals = Verticals.values();
        int currentVerticalInteger = 0;
        Verticals currentVertical = verticals[currentVerticalInteger];
        int count = 1;

        for (int i = 0; i < 64; ++i) {
            positionList.add(new BoardPosition(currentVertical, count));

            if (count == 8) {
                count = 1;
                currentVerticalInteger++;
                try {
                    currentVertical = verticals[currentVerticalInteger];
                } catch (IndexOutOfBoundsException ignored) { }
            } else {
                count++;
            }
        }
        return positionList;
    }
}
