package com.xigbclutchix.trove;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class TroveUtils {

    private static String troveInstallLocation;

    public static void saveMods() {
        File troveModLoaderDirectory = new File(System.getenv("APPDATA") + File.separator + "Trove-Mod-Loader-Java");
        File loadModsFile = new File(troveModLoaderDirectory + File.separator + "loadmods.txt");
        TroveUtils.saveModsToTextFile(loadModsFile);
    }

    public static void setTroveModText() {
        if (TroveMods.getMods().size() <= 0) {
            return;
        }
        if (TroveModLoader.getTroveModLoaderGUI() == null) {
            return;
        }
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        String textLabel = simpleDateFormat.format(date) + " - " + TroveMods.getMods().size() + (TroveMods.getMods().size() == 1 ? " mod" : " mods") + " added!";
        TroveModLoader.getTroveModLoaderGUI().setModLabel(textLabel);
    }

    public static void addModsFromTextFile(File modList) {
        String modListName = modList.getName();
        if (modListName.endsWith(".txt")) {
            TroveMods.getMods().clear();
            TroveMods.getListModel().clear();
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(modList));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    if (!line.isEmpty() && line.endsWith(".zip")) {
                        File file = new File(line);
                        if (file.exists()) {
                            TroveMods.addMod(file);
                        }
                    }
                }
                bufferedReader.close();
            } catch (FileNotFoundException ignored) {
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        setTroveModText();
    }

    public static void saveModsToTextFile(File file) {
        String filePath = String.valueOf(file);
        if (!filePath.endsWith(".txt")) {
            filePath = filePath + ".txt";
        }
        File saveFile = new File(filePath);

        StringBuilder stringBuilder = new StringBuilder();
        for (File mod : TroveMods.getMods()) {
            stringBuilder.append(mod);
            stringBuilder.append(System.getProperty("line.separator"));
        }

        try {
            PrintStream out = new PrintStream(new FileOutputStream(saveFile));
            out.print(stringBuilder);
            out.close();
        } catch (FileNotFoundException ignored) {
        }
    }

    public static void addModsToInstallation() {
        TroveModLoader.getTroveModLoaderGUI().disableButtons();
        if (getTroveInstallLocation() != null && !getTroveInstallLocation().isEmpty()) {
            File installDirectory = new File(getTroveInstallLocation());
            for (File mod : TroveMods.getMods()) {
                try {
                    unzipFileIntoDirectory(new ZipFile(mod), installDirectory);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        TroveModLoader.getTroveModLoaderGUI().enabledButtons();
        if (TroveMods.getMods().size() <= 0) {
            return;
        }
        saveMods();
        setTroveModText();
    }

    public static boolean isProcessRunning(String process) {
        boolean found = false;
        try {
            File file = File.createTempFile("checkIfRunning", ".vbs");
            file.deleteOnExit();
            FileWriter fileWriter = new java.io.FileWriter(file);
            String vbs = "Set WshShell = WScript.CreateObject(\"WScript.Shell\")\n"
                    + "Set locator = CreateObject(\"WbemScripting.SWbemLocator\")\n"
                    + "Set service = locator.ConnectServer()\n"
                    + "Set processes = service.ExecQuery _\n"
                    + " (\"select * from Win32_Process where name='" + process + "'\")\n"
                    + "For Each process in processes\n"
                    + "wscript.echo process.Name \n"
                    + "Next\n"
                    + "Set WSHShell = Nothing\n";
            fileWriter.write(vbs);
            fileWriter.close();
            Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            line = input.readLine();
            if (line != null) {
                if (line.equals(process)) {
                    found = true;
                }
            }
            input.close();
        } catch (FileNotFoundException ignored) {
        } catch (Exception e) {
            e.printStackTrace();
        }
        return found;
    }

    public static void unzipFileIntoDirectory(ZipFile zipFile, File directory) {
        Enumeration files = zipFile.entries();
        while (files.hasMoreElements()) {
            try {
                ZipEntry entry = (ZipEntry) files.nextElement();
                InputStream inputStream = zipFile.getInputStream(entry);
                byte[] buffer = new byte[1024];
                int bytesRead;
                File file = new File(directory.getAbsolutePath() + File.separator + entry.getName());

                if (entry.isDirectory()) {
                    file.mkdirs();
                    continue;
                } else {
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                }

                FileOutputStream fileOutputStream = new FileOutputStream(file);
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, bytesRead);
                }
                fileOutputStream.close();
            } catch (FileNotFoundException ignored) {
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            zipFile.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String getInstallLocation64() throws InvocationTargetException, IllegalAccessException {
        return WinRegistry.readString(WinRegistry.HKEY_LOCAL_MACHINE, "SOFTWARE\\Wow6432node\\Microsoft\\Windows\\CurrentVersion\\Uninstall\\Glyph Trove", "InstallLocation");
    }

    public static String getInstallLocation32() throws InvocationTargetException, IllegalAccessException {
        return WinRegistry.readString(WinRegistry.HKEY_LOCAL_MACHINE, "SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Uninstall\\Glyph Trove", "InstallLocation");
    }

    public static void setTroveInstallLocation(String newTroveInstallLocation) {
        troveInstallLocation = newTroveInstallLocation;
    }

    public static String getTroveInstallLocation() {
        return troveInstallLocation;
    }

    public static void saveTextToFile(String text, File file) {
        try {
            PrintStream out = new PrintStream(new FileOutputStream(file));
            out.print(text);
            out.close();
        } catch (FileNotFoundException ignored) {
        }
    }

    public static String readTextFromFile(File file) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            return reader.readLine();
        } catch (Exception ignored) {
        }
        return null;
    }

    public static void createTroveModLoaderFolder() {
        File directory = new File(System.getenv("APPDATA"));
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

    public static void setTroveFolderLocationAtStart(File troveModLoaderDirectory) {
        File file = new File(troveModLoaderDirectory + File.separator + "settings.txt");
        String installLocation = null;

        createTroveModLoaderFolder();

        if (!file.exists()) {
            try {
                file.createNewFile();
                installLocation = getInstallLocation32();
                if (installLocation == null) {
                    installLocation = getInstallLocation64();
                    if (installLocation != null) {
                        saveTextToFile(installLocation, file);
                    }
                }
            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
        } else {
            installLocation = readTextFromFile(file);
        }
        if (installLocation != null) {
            troveInstallLocation = installLocation;
        }
    }
}
