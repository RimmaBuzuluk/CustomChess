package com.example.customchess.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.customchess.R;
import com.example.customchess.engine.OneDeviceGame;
import com.example.customchess.engine.exceptions.CastlingException;
import com.example.customchess.engine.exceptions.ChessException;
import com.example.customchess.engine.exceptions.FigureNotChosenException;
import com.example.customchess.engine.exceptions.OneTeamPiecesSelectedException;
import com.example.customchess.engine.exceptions.PawnOnThePassException;
import com.example.customchess.engine.movements.BoardPosition;
import com.example.customchess.engine.movements.Movable;
import com.example.customchess.engine.movements.Movement;
import com.example.customchess.engine.movements.Position;
import com.example.customchess.engine.misc.Verticals;
import com.example.customchess.ui.figures.Figure;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;


public class ChessBoardFragment extends Fragment implements CageAdapter.OnItemSelected {

    private View view;
    private RecyclerView recyclerView;
    private CageAdapter recyclerAdapter;
    private RecyclerView.LayoutManager recyclerManager;
    private OneDeviceGame game;

    private Position start;
    private int      startIndex;
    private int      imageResource;

    public ChessBoardFragment() {
        // requires empty constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onItemClicked(final Position position, final int index, final int imageResourceId) {
        Movable move;

        if (start == null) {
            start = position;
            startIndex = index;
            imageResource = imageResourceId;
            return;
        }

        move = new Movement(start, position);

        CageAdapter.ViewHolder startHolder = (CageAdapter.ViewHolder)
                recyclerView.findViewHolderForAdapterPosition(startIndex);
        CageAdapter.ViewHolder destinationHolder = (CageAdapter.ViewHolder)
                recyclerView.findViewHolderForAdapterPosition(index);
        if (startHolder != null && destinationHolder != null) {
            try {
                game.canMakeMovement(move);
                startHolder.hide();
                destinationHolder.draw(imageResource);

            } catch (CastlingException ce) {
                CageAdapter.ViewHolder newRook;
                CageAdapter.ViewHolder oldRook;
                if (index < 24) {
                    newRook = (CageAdapter.ViewHolder)
                            recyclerView.findViewHolderForAdapterPosition(startIndex - 8);
                    oldRook = (CageAdapter.ViewHolder)
                            recyclerView.findViewHolderForAdapterPosition(index - 8);
                    newRook.draw(oldRook.getFigure().color);
                    destinationHolder.draw(startHolder.getFigure().color);
                    startHolder.hide(); // old king
                    oldRook.hide();
                } else if (index > 24) {
                    newRook = (CageAdapter.ViewHolder)
                            recyclerView.findViewHolderForAdapterPosition(startIndex + 8);
                    oldRook = (CageAdapter.ViewHolder)
                            recyclerView.findViewHolderForAdapterPosition(index + 16);
                    newRook.draw(oldRook.getFigure().color);
                    destinationHolder.draw(startHolder.getFigure().color);
                    startHolder.hide();
                    oldRook.hide();
                }
            } catch (OneTeamPiecesSelectedException | FigureNotChosenException otp) {
                start = position;
                startIndex = index;
                imageResource = imageResourceId;
                return;
            } catch (PawnOnThePassException ppe) {
                CageAdapter.ViewHolder passedPawn = (CageAdapter.ViewHolder)
                        recyclerView.findViewHolderForAdapterPosition(index + 1) ;

                if (startIndex % 2 == 0) {
                    passedPawn = (CageAdapter.ViewHolder)
                            recyclerView.findViewHolderForAdapterPosition(index - 1);
                }
                assert passedPawn != null;
                passedPawn.hide();
                destinationHolder.draw(startHolder.getFigure().color);
                startHolder.hide();

                Toast.makeText(this.getContext(), ppe.getMessage(), Toast.LENGTH_SHORT).show();
            } catch (ChessException e) {
                e.printStackTrace();
                Toast.makeText(this.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

        start = null;  // it looks disgusting
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chess_board, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayList<Cage> cageList = cagesInit();
        game  = new OneDeviceGame();

        recyclerView = view.findViewById(R.id.chess_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);

        recyclerAdapter = new CageAdapter(this, getTeamImagesMap(), cageList);
        recyclerView.setAdapter(recyclerAdapter);

        recyclerManager = new GridLayoutManager(this.getContext(), 8, GridLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(recyclerManager);
    }

    // maybe rewrite it
    private Hashtable<Integer, Figure> getTeamImagesMap() {
        Hashtable<Integer, Figure> teamImages = new Hashtable<>(32);
        Figure whitePawn = new Figure(R.drawable.white_pawn);
        Figure blackPawn = new Figure(R.drawable.black_pawn);
        Figure whiteRook = new Figure(R.drawable.white_rook);
        Figure blackRook = new Figure(R.drawable.black_rook);
        Figure whiteKnight = new Figure(R.drawable.white_knight);
        Figure blackKnight = new Figure(R.drawable.black_knight);
        Figure whiteBishop = new Figure(R.drawable.white_bishop);
        Figure blackBishop = new Figure(R.drawable.black_bishop);
        Figure whiteKing = new Figure(R.drawable.white_king);
        Figure whiteQueen = new Figure(R.drawable.white_queen);
        Figure blackKing = new Figure(R.drawable.black_king);
        Figure blackQueen = new Figure(R.drawable.black_queen);

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

    private ArrayList<Cage> cagesInit() {
        int brown = getResources().getColor(R.color.brown);
        int beige = getResources().getColor(R.color.beige);

        ArrayList<Cage> cageList = new ArrayList<>(64);
        ArrayList<Position> positionArray = initAllPositions();
        Iterator<Position> positionIterator = positionArray.iterator();

        for (int j = 0; j < 8; j++) {
            if (j % 2 == 0) {
                for (int i = 0; i < 8; i++) {
                    if (i % 2 == 1) {
                        cageList.add(new Cage(brown, positionIterator.next()));
                    } else {
                        cageList.add(new Cage(beige, positionIterator.next()));
                    }
                }
            } else {
                for (int i = 0; i < 8; i++) {
                    if (i % 2 == 0) {
                        cageList.add(new Cage(brown, positionIterator.next()));
                    } else {
                        cageList.add(new Cage(beige, positionIterator.next()));
                    }
                }
            }
        }
        return cageList;
    }

    // maybe move this to Application class ?
    private ArrayList<Position> initAllPositions() {
        ArrayList<Position> positionList = new ArrayList<>(64);
        Verticals [] verticals = Verticals.values();
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
                } catch (IndexOutOfBoundsException ignored) {

                }
            } else {
                count++;
            }
        }
        return positionList;
    }

}
