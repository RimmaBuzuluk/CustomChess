package com.example.customchess.ui.figures;


import com.example.customchess.R;
import com.example.customchess.engine.misc.Color;

public abstract class Figure {

    protected int imageId;
    protected final Color team;


    public Figure(int imageId, Color color) {
        this.imageId = imageId;
        this.team = color;
    }

    protected static boolean isBlack(int image) {
        return image >= 2131099735 & image <= 2131099740;
    }

    protected static boolean isWhite(int image) {
        return image >= 2131099765 & image <= 2131099776;
    }

    public int getImageId() {
        return imageId;
    }

    public abstract Figure flip();

    public static class Pawn extends Figure {

        public Pawn(Color color) {
            super(color.equals(Color.Black) ? R.drawable.black_pawn : R.drawable.white_pawn, color);
        }

        @Override
        public Figure flip() {
            if (team.equals(Color.White)) {
                imageId = R.drawable.white_pawn_flipped;
            }
            return this;
        }
    }

    public static class Rook extends Figure {

        public Rook(Color color) {
            super(color.equals(Color.Black) ? R.drawable.black_rook : R.drawable.white_rook,
                    color);
        }

        public Rook(int color) {
            super(Figure.isBlack(color) ? R.drawable.black_rook : R.drawable.white_rook,
                    Figure.isBlack(color) ? Color.Black : Color.White);
        }

        @Override
        public Figure flip() {
            if (team.equals(Color.White)) {
                imageId = R.drawable.white_rook_flipped;
            }
            return this;
        }
    }

    public static class Knight extends Figure {

        public Knight(Color color) {
            super(color.equals(Color.Black) ? R.drawable.black_knight : R.drawable.white_knight,
                    color);
        }

        public Knight(int color) {
            super(Figure.isBlack(color) ? R.drawable.black_knight : R.drawable.white_knight,
                    Figure.isBlack(color) ? Color.Black : Color.White);
        }

        @Override
        public Figure flip() {
            if (team.equals(Color.White)) {
                imageId = R.drawable.white_knight_flipped;
            }
            return this;
        }
    }

    public static class Bishop extends Figure {

        public Bishop(Color color) {
            super(color.equals(Color.Black) ? R.drawable.black_bishop : R.drawable.white_bishop,
                    color);
        }

        public Bishop(int color) {
            super(Figure.isBlack(color) ? R.drawable.black_bishop : R.drawable.white_bishop,
                    Figure.isBlack(color) ? Color.Black : Color.White);
        }

        @Override
        public Figure flip() {
            if (team.equals(Color.White)) {
                imageId = R.drawable.white_bishop_flipped;
            }
            return this;
        }
    }

    public static class Queen extends Figure {

        public Queen(Color color) {
            super(color.equals(Color.Black) ? R.drawable.black_queen : R.drawable.white_queen,
                    color);
        }

        public Queen(int color) {
            super(Figure.isBlack(color) ? R.drawable.black_queen : R.drawable.white_queen,
                    Figure.isBlack(color) ? Color.Black : Color.White);
        }

        @Override
        public Figure flip() {
            if (team.equals(Color.White)) {
                imageId = R.drawable.white_queen_flipped;
            }
            return this;
        }
    }

    public static class King extends Figure {

        public King(Color color) {
            super(color.equals(Color.Black) ? R.drawable.black_king : R.drawable.white_king,
                    color);
        }

        @Override
        public Figure flip() {
            if (team.equals(Color.White)) {
                imageId = R.drawable.white_king_flipped;
            }
            return this;
        }
    }
}
