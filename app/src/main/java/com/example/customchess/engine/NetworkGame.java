package com.example.customchess.engine;

import com.example.customchess.engine.automata.Player;
import com.example.customchess.engine.automata.WhitePlayer;
import com.example.customchess.engine.exceptions.*;
import com.example.customchess.engine.figures.*;
import com.example.customchess.engine.misc.Color;
import com.example.customchess.engine.misc.Pieces;
import com.example.customchess.engine.misc.Verticals;
import com.example.customchess.engine.movements.*;
import com.example.customchess.engine.notations.ChessNotation;
import com.example.customchess.engine.notations.InternationalNotation;
import com.example.customchess.networking.ChessNetMovementPacket;
import com.example.customchess.networking.ChessNetPacket;
import com.example.customchess.networking.Client;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class NetworkGame implements Game {
    private final Board board;
    private Player currentPlayer;
    private final Stack<MovementHistory> movementStack;
    private final List<Piece> blackTeam;
    private final List<Piece> whiteTeam;
    private final EndGameChecker    gameAnalyser;
    private final Client client;
    private ChessNotation internationalNotation;

    private MovementHistory currentMovementHeader;
    private Piece startFigure;
    private Piece destinationFigure;
    private Piece backUpPiece;

    public NetworkGame() {
        client = new Client("192.168.188.224", 3535);
        movementStack = new Stack<>();
        currentPlayer = new WhitePlayer(this);
        whiteTeam = new LinkedList<>();
        blackTeam = new LinkedList<>();
//        initTeam(blackTeam, Color.Black);
//        initTeam(whiteTeam, Color.White);
//        board = new Board(blackTeam, whiteTeam);
        board = null;
//        gameAnalyser  = new EndGameChecker(board, whiteTeam, blackTeam);
        gameAnalyser = null;
//        internationalNotation = new InternationalNotation();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                client.start();
//            }
//        }).start();
    }

    public boolean hasConnection() {
        synchronized (client) {
            return client.isConnected();
        }
    }

    // for debug
    public String getMovementsHistory() {
        if ( movementStack.isEmpty() ) return "empty";
        List<MovementHistory> correct = new LinkedList<>();

        StringBuilder stringBuilder = new StringBuilder();
        while ( ! movementStack.isEmpty()) {
            correct.add(movementStack.pop());
        }
        int j = 1;
        for (int i = correct.size() - 1; i >= 0; i--) {
            stringBuilder.append(j++).append(". ").append(correct.get(i).movement).append("\n");
        }
        for (int i = correct.size() - 1; i >= 0; i--) {
            movementStack.push(correct.get(i));
        }

        return stringBuilder.toString();
    }

    private void initTeam(List<Piece> team, Color color) {
        int pawnRow = 1;
        int kingRow = 0;
        if (Color.Black.equals(color)) {
            pawnRow = 6;
            kingRow = 7;
        }
//        team.add(new Rook(color, new BoardPosition(Verticals.h, kingRow + 1)));
//        team.add(new Rook(color, new BoardPosition(Verticals.a, kingRow + 1)));
//        team.add(new Knight(color, new BoardPosition(Verticals.g, kingRow + 1)));
//        team.add(new Knight(color, new BoardPosition(Verticals.b, kingRow + 1)));
//        team.add(new Bishop(color, new BoardPosition(Verticals.c, kingRow + 1)));
//        team.add(new Bishop(color, new BoardPosition(Verticals.f, kingRow + 1)));
//        team.add(new King(color, new BoardPosition(Verticals.e, kingRow + 1)));
//        team.add(new Queen(color, new BoardPosition(Verticals.d, kingRow + 1)));
//        for (int vertical = 0; vertical < 8; vertical++) {
//            team.add(new Pawn(color, new BoardPosition(vertical, pawnRow + 1)));
//        }
    }

    public void setCurrentPlayer(Player player) {
        currentPlayer = player;
    }

    public MovementHistory getLastMovement() {
        return ! movementStack.isEmpty() ? movementStack.peek() : null;
    }

    @Override
    public void checkForPat() throws DrawException {
//        if (gameAnalyser.checkForDraw(currentPlayer.getColor())) {
//            throw new DrawException("Draw");
//        }
    }

    @Override
    public void checkForCheckMate() throws CheckMateException {
//        if (gameAnalyser.isCheckMate(currentPlayer.getColor())) {
//            throw new CheckMateException("Mate on the board\n" + currentPlayer.getColor() + " is fucked");
//        }
    }

    @Override
    public void promotion(String choice) {
        Piece promotedPiece;
        Color team = ((ChessPiece) movementStack.peek().start).color;
        List<Piece> promTeam = team.equals(Color.White) ? whiteTeam : blackTeam;
        Position destination = movementStack.peek().movement.getDestination();

//        switch (choice) {
//            case "Queen":
//                promotedPiece = new Queen(team, destination);
//                break;
//            case "Bishop":
//                promotedPiece = new Bishop(team, destination);
//                break;
//            case "Rook":
//                promotedPiece = new Rook(team, destination);
//                break;
//            default:
//                promotedPiece = new Knight(team, destination);
//                break;
//        }

//        board.promoteTo(destination, promotedPiece);
    }

    public Movable getEnemyMove() throws ChessException {
        ChessNetPacket enemyMove = client.receive();
        if ( ! enemyMove.isMovementLegal() ) {
            tryToMakeMovement(enemyMove.getMovement());
            currentPlayer.changePlayer();
        }
        return enemyMove.getMovement();
    }

    public void approveMoveOnServer(Movable movement) throws ChessException {

    }

    @Override
    public void tryToMakeMovement(Movable movement) throws ChessException {
        Position start = movement.getStart();
        Position destination = movement.getDestination();
        startFigure = board.findBy(movement.getStart());
        destinationFigure = board.findBy(movement.getDestination());  // can be null
        currentMovementHeader = new MovementHistory(movement, startFigure, destinationFigure);
        backUpPiece = null;

        try {
            if (currentPlayer.isCorrectPlayerMove((ChessPiece) startFigure)) {
                try {
                    startFigure.tryToMove(movement, board, gameAnalyser, getLastMovement());

                } catch (MoveOnEmptyCageException mec) {
                    board.swapFigures(start, destination);
                    throw mec;
                } catch (BeatFigureException bfe) {
                    board.beatFigure(start, destination);
                    throw bfe;
                } catch (CastlingException ce) {
                    board.castling(start, destination);
                    throw ce;
                } catch (PawnEnPassantException ppe) {
                    board.pawnOnThePass(start, destination);
                    throw ppe;
                } catch (PromotionException pe) {
                    board.promotion(start, destination);
                    throw pe;
                }
            }

        } catch (MoveOnEmptyCageException
                | BeatFigureException
                | CastlingException
                | PromotionException
                | PawnEnPassantException ce) {
            if (gameAnalyser.isKingUnderAttack(currentPlayer.getColor())) {
                board.restorePreviousTurn(currentMovementHeader);
                throw new CheckKingException(currentPlayer.getColor() + " King under check");
            }

            movementStack.push(currentMovementHeader);
            throw ce;
        } catch (NullPointerException npe) {
            throw new FigureNotChosenException("Figure was not chosen");
        }
    }
}
