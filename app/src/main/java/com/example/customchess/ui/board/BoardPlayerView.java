package com.example.customchess.ui.board;

import com.example.customchess.ui.Cage;
import com.example.customchess.ui.figures.Figure;

import java.util.Hashtable;
import java.util.List;

public interface BoardPlayerView {
    List<Cage> getCages();
    Hashtable<Integer, Figure> getTeamImages();
    void flipWhiteTeam();
}
