package com.example.customchess.engine.movements;

import com.example.customchess.engine.misc.Verticals;

import java.util.ArrayList;
import java.util.List;


public class BoardPosition implements Position {

    private Verticals vertical;
    private String    horizontal;

    public BoardPosition(Verticals vertical, String horizontal) {
        this.vertical = vertical;
        this.horizontal = horizontal;
    }

    public BoardPosition(Integer vertical, Integer horizontal) {
        Verticals[] verticals = Verticals.values();
        this.vertical = verticals[vertical];
        this.horizontal = horizontal.toString();
    }

    public BoardPosition(Verticals vertical, Integer horizontal) {
        this.vertical = vertical;
        this.horizontal = horizontal.toString();
    }

    public Verticals getVertical() {
        return vertical;
    }

    public static boolean isShortSchemeCastling(Position destination) {
        return destination.getVertical().equals(Verticals.G);
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof BoardPosition) {
            if (object == this) {
                return true;
            }
            return ((BoardPosition) object).horizontal.equals(this.horizontal)
                    && ((BoardPosition) object).vertical.equals(this.vertical);
        }

        return false;
    }

    @Override
    public String toString() {
        return vertical + horizontal;
    }

    public Integer getHorizontal() {
        return Integer.parseInt(horizontal);
    }

    @Override
    public List<Position> getPositionsAround() {
        List<Position> cagesAround = new ArrayList<>(9);
        // for example original position is 'G7'
        int originalVertical   = vertical.ordinal();
        int originalHorizontal = getHorizontal();
        int[][] arr = new int[][] {
                new int[] { originalVertical - 1, originalHorizontal - 1 }, // H6
                new int[] { originalVertical - 1, originalHorizontal     }, // H7
                new int[] { originalVertical - 1, originalHorizontal + 1 }, // H8

                new int[] { originalVertical, originalHorizontal - 1 }, // G6
                new int[] { originalVertical, originalHorizontal + 1 }, // G8

                new int[] { originalVertical + 1, originalHorizontal - 1 }, // F6
                new int[] { originalVertical + 1, originalHorizontal     }, // F7
                new int[] { originalVertical + 1, originalHorizontal + 1 }, // F8
        };

        int currentVertical;
        int currentHorizontal;
        for (int[] ints : arr) {
            currentVertical = ints[0];
            currentHorizontal = ints[1];
            if (currentVertical >= 0 && currentVertical <= 7
                    && currentHorizontal >= 1 && currentHorizontal <= 8) {
                cagesAround.add(new BoardPosition(currentVertical, currentHorizontal));
            }
        }

        return cagesAround;
    }
}
