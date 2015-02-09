package com.xigbclutchix.trove;

import java.util.Timer;
import java.util.TimerTask;

public class TroveTimer {

    private static Timer timer;
    private static boolean troveRan = false;

    public static void init() {
        timer = new Timer();
        TroveTimerTask troveTimerTask = new TroveTimerTask();
        timer.schedule(troveTimerTask, 0, 2 * 1000);
    }

    public static class TroveTimerTask extends TimerTask {
        @Override
        public void run() {
            if (TroveUtils.getTroveInstallLocation() == null) {
                TroveModLoader.getTroveModLoaderGUI().disableButtons(false);
            }

            boolean isTroveRunning = TroveUtils.isProcessRunning("Trove.exe");
            TroveModLoader.getTroveModLoaderGUI().setTroveStatusLabel("Trove is " + (isTroveRunning ? "" : "not ") + "running");

            if (!troveRan && isTroveRunning) {
                TroveUtils.addModsToInstallation();
            }
            troveRan = isTroveRunning;
        }
    }

    public static void stop() {
        timer.cancel();
    }
}

