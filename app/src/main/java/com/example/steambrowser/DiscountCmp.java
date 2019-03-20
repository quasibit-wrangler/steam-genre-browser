package com.example.steambrowser;

import java.util.Comparator;

public class DiscountCmp implements Comparator<SteamUtils.Game> {
    public int compare(SteamUtils.Game game1, SteamUtils.Game game2) {
        return Integer.compare(Integer.parseInt(game2.discount), Integer.parseInt(game1.discount));
    }
}
