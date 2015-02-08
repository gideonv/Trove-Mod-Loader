package com.xigbclutchix.trove;

import java.io.File;

public class TroveModLoader {

    private static TroveModLoaderGUI troveModLoaderGUI;

    public static void main(String[] args) {
        File troveModLoaderDirectory = new File(System.getenv("APPDATA") + File.separator + "Trove-Mod-Loader-Java");
        File loadModsFile = new File(troveModLoaderDirectory + File.separator + "loadmods.txt");

        TroveUtils.setTroveFolderLocationAtStart(troveModLoaderDirectory);
        TroveUtils.addModsFromTextFile(loadModsFile);

        troveModLoaderGUI = new TroveModLoaderGUI();
        troveModLoaderGUI.setVisible(true);
        troveModLoaderGUI.setSize(450, 190);

        TroveUtils.setTroveModText();
    }

    public static TroveModLoaderGUI getTroveModLoaderGUI() {
        return troveModLoaderGUI;
    }
}
