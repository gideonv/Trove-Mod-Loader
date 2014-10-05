package com.xigbclutchix.trove;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;

public class TroveMods {

    private static ArrayList<File> mods = new ArrayList<File>();
    private static DefaultListModel<String> listModel = new DefaultListModel<String>();

    public static void addMod(File mod) {
        if (!mods.contains(mod)) {
            String modName = mod.getName();
            if (modName.endsWith(".zip")) {
                mods.add(mod);
                modName = modName.substring(0, modName.length() - 4);
                listModel.addElement(modName);
            }
        }
    }

    public static void removeMod(int mod) {
        mods.remove(mod);
        listModel.removeElementAt(mod);
    }

    public static ArrayList<File> getMods() {
        return mods;
    }

    public static DefaultListModel<String> getListModel() {
        return listModel;
    }
}
