package com.example.customchess;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class ChessBoardFragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerAdapter;
    private RecyclerView.LayoutManager recyclerManager;
    private ArrayList<Integer> colorList;


    public ChessBoardFragment() {

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

        recyclerAdapter = new RecyclerAdapter(this.getContext(), colorList);
        recyclerView.setAdapter(recyclerAdapter);

        recyclerManager = new GridLayoutManager(this.getContext(), 8, GridLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(recyclerManager);
    }

    public void cagesInit() {
        int brown = getResources().getColor(R.color.brown);
        int beige = getResources().getColor(R.color.beige);

        colorList = new ArrayList<>(64);

        for (int j = 0; j < 8; j++) {
            if (j % 2 == 0) {
                for (int i = 0; i < 8; i++) {
                    if (i % 2 == 1) {
                        colorList.add(brown);
                    } else {
                        colorList.add(beige);
                    }
                }
            } else {
                for (int i = 8; i < 16; i++) {
                    if (i % 2 == 0) {
                        colorList.add(brown);
                    } else {
                        colorList.add(beige);
                    }
                }
            }
        }
    }

}





