package com.example.customchess.engine.movements;

public class Movement implements Movable {

    private Position from;
    private Position where;

    public Movement(Position from, Position where) {
        this.from = from;
        this.where = where;
    }

    @Override
    public Position getPositionFrom() {
        return from;
    }

    @Override
    public Position getPositionWhere() {
        return where;
    }
}
