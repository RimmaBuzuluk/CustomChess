package com.example.customchess.ui.figures;


import com.example.customchess.R;
import com.example.customchess.engine.misc.Color;

public abstract class Figure {

    protected int imageId;
    protected final Color team;
    protected boolean flipped;


    public Figure(int imageId, Color color) {
        this.imageId = imageId;
        this.team = color;
        flipped = false;
    }

    public static boolean isBlack(int image) {
        return image == R.drawable.black_bishop |
                image == R.drawable.black_king |
                image == R.drawable.black_queen |
                image == R.drawable.black_knight |
                image == R.drawable.black_rook |
                image == R.drawable.black_pawn;
    }

    public static boolean isFlipped(int image) {
        return image == R.drawable.white_king_flipped |
                image == R.drawable.white_queen_flipped |
                image == R.drawable.white_rook_flipped |
                image == R.drawable.white_bishop_flipped |
                image == R.drawable.white_knight_flipped |
                image == R.drawable.white_pawn_flipped;
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
                flipped = true;
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
            super(isBlack(color) ? R.drawable.black_rook:
                            (isFlipped(color) ? R.drawable.white_rook_flipped : R.drawable.white_rook),
                    isBlack(color) ? Color.Black : Color.White);
        }

        @Override
        public Figure flip() {
            if (team.equals(Color.White)) {
                imageId = R.drawable.white_rook_flipped;
                flipped = true;
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
            super(isBlack(color) ? R.drawable.black_knight:
                            (isFlipped(color) ? R.drawable.white_knight_flipped : R.drawable.white_knight),
                    isBlack(color) ? Color.Black : Color.White);
        }

        @Override
        public Figure flip() {
            if (team.equals(Color.White)) {
                imageId = R.drawable.white_knight_flipped;
                flipped = true;
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
            super(isBlack(color) ? R.drawable.black_bishop :
                            (isFlipped(color) ? R.drawable.white_bishop_flipped : R.drawable.white_bishop),
                    isBlack(color) ? Color.Black : Color.White);
        }

        @Override
        public Figure flip() {
            if (team.equals(Color.White)) {
                imageId = R.drawable.white_bishop_flipped;
                flipped = true;
            }
            return this;
        }
    }

    public static class Queen extends Figure {

        public Queen(Color color) {
            super(color.equals(Color.Black) ? R.drawable.black_queen : R.drawable.white_queen,
                    color);
        }

        public Queen(int piece) {
            super(isBlack(piece) ? R.drawable.black_queen :
                            (isFlipped(piece) ? R.drawable.white_queen_flipped : R.drawable.white_queen),
                    isBlack(piece) ? Color.Black : Color.White);
        }

        @Override
        public Figure flip() {
            if (team.equals(Color.White)) {
                imageId = R.drawable.white_queen_flipped;
                flipped = true;
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
                flipped = true;
            }
            return this;
        }
    }
}
