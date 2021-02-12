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
import android.view.ViewStub;

import com.example.customchess.R;
import com.example.customchess.engine.Game;
import com.example.customchess.engine.OneDeviceGame;
import com.example.customchess.engine.movements.Position;
import com.example.customchess.ui.board.BlackPlayerViewBoard;
import com.example.customchess.ui.board.BoardPlayerView;
import com.example.customchess.ui.board.WhitePlayerViewBoard;


public class ChessBoardFragment extends Fragment implements CageAdapter.OnItemSelected {

    private View view;
    private RecyclerView recyclerView;
    private CageAdapter recyclerAdapter;
    private RecyclerView.LayoutManager recyclerManager;
    private BoardPlayerView boardPlayerView;
    private Game game;
    private MovementHandler movementHandler;
    private ViewStub topVerticals;
    private ViewStub bottomVerticals;
    private ViewStub leftHorizontals;
    private ViewStub rightHorizontals;


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
        view = inflater.inflate(R.layout.fragment_board, container, false);
        topVerticals = view.findViewById(R.id.top_verticals);
        bottomVerticals = view.findViewById(R.id.bottom_verticals);
        leftHorizontals = view.findViewById(R.id.left_horizontals);
        rightHorizontals = view.findViewById(R.id.right_horizontals);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView = view.findViewById(R.id.chess_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);

        // TODO: 13.02.21 magic is here
        whiteView();
        recyclerAdapter = new CageAdapter(this, boardPlayerView);
        recyclerView.setAdapter(recyclerAdapter);

        recyclerManager = new GridLayoutManager(this.getContext(), 8, GridLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(recyclerManager);

        game = new OneDeviceGame();
        movementHandler = new MovementHandler(this, game, recyclerView);
    }

    private void whiteView() {
        topVerticals.setLayoutResource(R.layout.white_player_verticals_flipped);
        topVerticals.inflate();
        bottomVerticals.setLayoutResource(R.layout.white_player_verticals);
        bottomVerticals.inflate();
        leftHorizontals.setLayoutResource(R.layout.white_player_horizontals);
        leftHorizontals.inflate();
        rightHorizontals.setLayoutResource(R.layout.white_player_horizontals_flipped);
        rightHorizontals.inflate();
        boardPlayerView = new WhitePlayerViewBoard(this.getContext());
    }

    private void blackView() {
        topVerticals.setLayoutResource(R.layout.black_player_verticals_flipped);
        topVerticals.inflate();
        bottomVerticals.setLayoutResource(R.layout.black_player_verticals);
        bottomVerticals.inflate();
        leftHorizontals.setLayoutResource(R.layout.black_player_horizontals);
        leftHorizontals.inflate();
        rightHorizontals.setLayoutResource(R.layout.black_player_horizontals_flipped);
        rightHorizontals.inflate();
        boardPlayerView = new BlackPlayerViewBoard(this.getContext());
    }

    public String getHistory() {
        return ((OneDeviceGame) game).getMovementsHistory();
    }
}
