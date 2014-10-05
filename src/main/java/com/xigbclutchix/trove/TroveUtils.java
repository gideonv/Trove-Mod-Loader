package com.xigbclutchix.trove;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class TroveUtils {

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
        String installLocation = null;
        try {
            installLocation = getInstallLocation();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (installLocation != null && !installLocation.isEmpty()) {
            File installDirectory = new File(installLocation);
            for (File mod : TroveMods.getMods()) {
                try {
                    unzipFileIntoDirectory(new ZipFile(mod), installDirectory);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        TroveModLoaderGUI.enabledButtons();
        TroveUtils.saveModsToTextFile(new File("loadmods.txt"));
        JOptionPane.showMessageDialog(TroveModLoader.getTroveModLoaderGUI(), "Mods installed!", "Trove Mod Loader", JOptionPane.INFORMATION_MESSAGE);
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

    public static String getInstallLocation() throws InvocationTargetException, IllegalAccessException {
        return WinRegistry.readString(WinRegistry.HKEY_LOCAL_MACHINE, "SOFTWARE\\Wow6432node\\Microsoft\\Windows\\CurrentVersion\\Uninstall\\Glyph Trove", "InstallLocation");
    }
}
