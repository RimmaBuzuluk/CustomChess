package com.example.customchess;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.customchess.engine.movements.BoardPosition;
import com.example.customchess.engine.movements.Movable;
import com.example.customchess.engine.movements.Move;
import com.example.customchess.engine.movements.Position;
import com.example.customchess.engine.movements.Verticals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;


public class ChessBoardFragment extends Fragment implements CageAdapter.OnItemSelected {

    private View view;
    private RecyclerView recyclerView;
    private CageAdapter recyclerAdapter;
    private RecyclerView.LayoutManager recyclerManager;

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

        move = new Move(start, position);

        CageAdapter.ViewHolder startHolder = (CageAdapter.ViewHolder)
                recyclerView.findViewHolderForAdapterPosition(startIndex);
        CageAdapter.ViewHolder destinationHolder = (CageAdapter.ViewHolder)
                recyclerView.findViewHolderForAdapterPosition(index);
        if (startHolder != null && destinationHolder != null) {
            startHolder.hide();
            destinationHolder.draw(imageResource);
        }

//        Toast.makeText(this.getContext(), ((Integer) R.drawable.black_bishop).toString(), Toast.LENGTH_SHORT).show();
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
        start = null;  // am I idiot ?

        recyclerView = view.findViewById(R.id.chess_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);

        recyclerAdapter = new CageAdapter(this, getTeamImagesMap(), cageList);
        recyclerView.setAdapter(recyclerAdapter);

        recyclerManager = new GridLayoutManager(this.getContext(), 8, GridLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(recyclerManager);

    }

    private Hashtable<Integer, Integer> getTeamImagesMap() {
        Hashtable<Integer, Integer> teamImages = new Hashtable<>(32);
        teamImages.put(0, R.drawable.white_rook);
        teamImages.put(56, R.drawable.white_rook);
        teamImages.put(8, R.drawable.white_knight);
        teamImages.put(48, R.drawable.white_knight);
        teamImages.put(16, R.drawable.white_bishop);
        teamImages.put(40, R.drawable.white_bishop);
        teamImages.put(24, R.drawable.white_king);
        teamImages.put(32, R.drawable.white_queen);
        for (int i = 0; i < 8; ++i) {
            teamImages.put((1 + i * 8), R.drawable.white_pawn);
        }
        teamImages.put(7, R.drawable.black_rook);
        teamImages.put(63, R.drawable.black_rook);
        teamImages.put(15, R.drawable.black_knight);
        teamImages.put(55, R.drawable.black_knight);
        teamImages.put(23, R.drawable.black_bishop);
        teamImages.put(47, R.drawable.black_bishop);
        teamImages.put(31, R.drawable.black_king);
        teamImages.put(39, R.drawable.black_queen);
        for (int i = 0; i < 8; ++i) {
            teamImages.put((6 + i * 8), R.drawable.black_pawn);
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
