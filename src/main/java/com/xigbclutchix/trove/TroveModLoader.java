package com.xigbclutchix.trove;

import java.io.File;

public class TroveModLoader {

    private static TroveModLoaderGUI troveModLoaderGUI;

    public static void main(String[] args) {
        troveModLoaderGUI = new TroveModLoaderGUI();
        troveModLoaderGUI.setVisible(true);
        troveModLoaderGUI.setSize(450, 190);

        TroveUtils.addModsFromTextFile(new File("loadmods.txt"));
    }

    public static TroveModLoaderGUI getTroveModLoaderGUI() {
        return troveModLoaderGUI;
    }
}
