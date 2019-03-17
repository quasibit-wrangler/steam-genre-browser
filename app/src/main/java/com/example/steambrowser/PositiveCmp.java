package com.example.steambrowser;

import java.util.Comparator;

public class PositiveCmp implements Comparator<SteamUtils.Game> {

    public int compare(SteamUtils.Game game1, SteamUtils.Game game2) {
        return (game1.positive > game2.positive) ? -1 : (game1.positive < game2.positive) ? 1 : 0;
    }

}
