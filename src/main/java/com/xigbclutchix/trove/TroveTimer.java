package com.xigbclutchix.trove;

import java.text.SimpleDateFormat;
import java.util.Date;
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
            boolean isTroveRunning = TroveUtils.isProcessRunning("Trove.exe");

            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
            String textLabel =  simpleDateFormat.format(date) + " - " + "Trove is " + (isTroveRunning ? "" : "not ") + "running";
            TroveModLoader.getTroveModLoaderGUI().setTroveLabel(textLabel);

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

