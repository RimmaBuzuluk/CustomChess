package com.example.customchess.ui.fragments;

import android.content.Context;
import android.content.res.Configuration;
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
import com.example.customchess.ui.CageAdapter;
import com.example.customchess.ui.MovementHandler;
import com.example.customchess.ui.NetMovementHandler;
import com.example.customchess.ui.OneDeviceMovementHandler;
import com.example.customchess.ui.Team;
import com.example.customchess.ui.board.BlackPlayerViewBoard;
import com.example.customchess.ui.board.BoardPlayerView;
import com.example.customchess.ui.board.WhitePlayerViewBoard;
import com.example.customchess.ui.figures.TempPosition;

import java.util.Locale;


public class ChessBoardFragment extends Fragment
        implements CageAdapter.OnItemSelected {
    public interface Flipper { }

    private Flipper flipper;
    private View view;
    private RecyclerView recyclerView;
    private Team         playerChosen;
    private CageAdapter  recyclerAdapter;
    private RecyclerView.LayoutManager recyclerManager;
    private BoardPlayerView boardPlayerView;
    private Game game;
    private MovementHandler movementHandler;
    private ViewStub topVerticals;
    private ViewStub bottomVerticals;
    private ViewStub leftHorizontals;
    private ViewStub rightHorizontals;


    public ChessBoardFragment(@NonNull Team team, @NonNull Game game) {
        playerChosen = team;
        this.game = game;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            flipper = (Flipper) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        Locale locale = new Locale("en");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config,
                context.getResources().getDisplayMetrics());
    }

    @Override
    public void onItemClicked(final Position position, final int index, final int imageResourceId) {
        // todo maybe add a thread pool
        movementHandler.handle(new TempPosition(position, index, imageResourceId));
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

        if (playerChosen.equals(Team.White)) {
            whiteView();
        } else {
            blackView();
        }
        if (flipper != null) {
            boardPlayerView.flipWhiteTeam();
        }
        topVerticals.inflate();
        bottomVerticals.inflate();
        leftHorizontals.inflate();
        rightHorizontals.inflate();

        recyclerAdapter = new CageAdapter(this, boardPlayerView);
        recyclerView.setAdapter(recyclerAdapter);

        recyclerManager = new GridLayoutManager(this.getContext(), 8, GridLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(recyclerManager);

        if (game instanceof OneDeviceGame) {
            movementHandler = new OneDeviceMovementHandler(this, game, recyclerView, boardPlayerView);
        } else {
            movementHandler = new NetMovementHandler(this, game, recyclerView, boardPlayerView);
        }
    }

    private void whiteView() {
        topVerticals.setLayoutResource(R.layout.white_player_verticals_flipped);
        bottomVerticals.setLayoutResource(R.layout.white_player_verticals);
        leftHorizontals.setLayoutResource(R.layout.white_player_horizontals);
        rightHorizontals.setLayoutResource(R.layout.white_player_horizontals_flipped);
        boardPlayerView = new WhitePlayerViewBoard(this.getContext());
    }

    private void blackView() {
        topVerticals.setLayoutResource(R.layout.black_player_verticals_flipped);
        bottomVerticals.setLayoutResource(R.layout.black_player_verticals);
        leftHorizontals.setLayoutResource(R.layout.black_player_horizontals);
        rightHorizontals.setLayoutResource(R.layout.black_player_horizontals_flipped);
        boardPlayerView = new BlackPlayerViewBoard(this.getContext());
    }

    public String getHistory() {
        return ((OneDeviceGame) game).getMovementsHistory();
    }
}
