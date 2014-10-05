package com.xigbclutchix.trove;

import java.util.Timer;
import java.util.TimerTask;

public class TroveTimer {

    private static Timer timer;

    public static void init() {
        timer = new Timer();
        TroveTimerTask troveTimerTask = new TroveTimerTask();
        timer.schedule(troveTimerTask, 0, 3 * 1000);
    }

    public static class TroveTimerTask extends TimerTask {
        @Override
        public void run() {
            if (TroveUtils.isProcessRunning("Trove.exe")) {
                TroveUtils.addModsToInstallation();
                stop();
            }
        }
    }

    public static void stop() {
        timer.cancel();
    }
}

