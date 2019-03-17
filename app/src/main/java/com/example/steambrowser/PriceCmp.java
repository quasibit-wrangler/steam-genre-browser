package com.example.steambrowser;

import java.util.Comparator;

public class PriceCmp implements Comparator<SteamUtils.Game> {
    public int compare(SteamUtils.Game game1, SteamUtils.Game game2) {
        return (Integer.parseInt(game1.price) < Integer.parseInt(game2.price)) ? -1 : (Integer.parseInt(game1.price) > Integer.parseInt(game2.price)) ? 1 : 0;
    }
}
