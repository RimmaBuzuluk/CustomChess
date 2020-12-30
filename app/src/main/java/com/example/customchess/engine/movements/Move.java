package com.example.customchess.engine.movements;

public class Move implements Movable {

    private Position start;
    private Position destination;

    public Move(Position start, Position destination) {
        this.start = start;
        this.destination = destination;
    }

    @Override
    public Position getStart() {
        return start;
    }

    @Override
    public Position getDestination() {
        return destination;
    }

    // delete at the end
    // just for Toasts
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (start != null) {
            builder.append("start ").append(start.toString());
        }
        if (destination != null) {
            builder.append("\n dest ").append(destination.toString());
        }
        return new String(builder);
    }
}
