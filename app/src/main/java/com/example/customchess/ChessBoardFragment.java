package com.example.customchess;

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

import com.example.customchess.engine.movements.BoardPosition;
import com.example.customchess.engine.movements.Verticals;

import java.util.ArrayList;


public class ChessBoardFragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerAdapter;
    private RecyclerView.LayoutManager recyclerManager;
    private ArrayList<Cage> cageList;

    public ChessBoardFragment() {
        // requires empty constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chess_board, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        cagesInit();

        recyclerView = view.findViewById(R.id.chess_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);

        recyclerAdapter = new CageAdapter(this.getContext(), cageList);
        recyclerView.setAdapter(recyclerAdapter);

        recyclerManager = new GridLayoutManager(this.getContext(), 8, GridLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(recyclerManager);
    }

    private void cagesInit() {
        int brown = getResources().getColor(R.color.brown);
        int beige = getResources().getColor(R.color.beige);

        cageList = new ArrayList<>(64);

        for (int j = 0; j < 8; j++) {
            if (j % 2 == 0) {
                for (int i = 0; i < 8; i++) {
                    if (i % 2 == 1) {
                        cageList.add(new Cage(brown));
                    } else {
                        cageList.add(new Cage(beige));
                    }
                }
            } else {
                for (int i = 0; i < 8; i++) {
                    if (i % 2 == 0) {
                        cageList.add(new Cage(brown));
                    } else {
                        cageList.add(new Cage(beige));
                    }
                }
            }
        }

        Verticals [] verticals = Verticals.values();
        int currentVerticalInteger = 0;
        Verticals currentVertical = verticals[currentVerticalInteger];
        int count = 1;

        for (Cage cage : cageList) {
            cage.setPosition(new BoardPosition(currentVertical, count));

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

    }

}





