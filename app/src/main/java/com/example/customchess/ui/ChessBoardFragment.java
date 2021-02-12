package com.example.customchess.ui;

import android.app.Application;
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

import com.example.customchess.R;
import com.example.customchess.engine.Game;
import com.example.customchess.engine.OneDeviceGame;
import com.example.customchess.engine.misc.Color;
import com.example.customchess.engine.movements.BoardPosition;
import com.example.customchess.engine.movements.Position;
import com.example.customchess.engine.misc.Verticals;
import com.example.customchess.ui.board.BlackPlayerViewBoard;
import com.example.customchess.ui.board.BoardPlayerView;
import com.example.customchess.ui.board.WhitePlayerViewBoard;
import com.example.customchess.ui.figures.Figure;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;


public class ChessBoardFragment extends Fragment implements CageAdapter.OnItemSelected {

    private View view;
    private RecyclerView recyclerView;
    private CageAdapter recyclerAdapter;
    private RecyclerView.LayoutManager recyclerManager;
    private BoardPlayerView boardPlayerView;
    private Game game;
    private MovementHandler movementHandler;


    public ChessBoardFragment() {
        // requires empty constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onItemClicked(final Position position, final int index, final int imageResourceId) {
        movementHandler.handle(position, index, imageResourceId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chess_board, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView = view.findViewById(R.id.chess_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);

        boardPlayerView = new BlackPlayerViewBoard(this.getContext());
        recyclerAdapter = new CageAdapter(this, boardPlayerView);
        recyclerView.setAdapter(recyclerAdapter);

        recyclerManager = new GridLayoutManager(this.getContext(), 8, GridLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(recyclerManager);

        game = new OneDeviceGame();
        movementHandler = new MovementHandler(this, game, recyclerView);
    }

    public String getHistory() {
        return ((OneDeviceGame) game).getMovementsHistory();
    }
}
