package com.xigbclutchix.trove;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * Created by JFormDesigner on Tue Sep 30 13:38:13 EDT 2014
 */

/**
 * @author unknown
 */
public class TroveModLoaderGUI extends JFrame {

    public TroveModLoaderGUI() {
        initComponents();
        list1.setModel(TroveMods.getListModel());
        TroveTimer.init();
    }

    private void thisWindowClosing(WindowEvent event) {
        TroveTimer.stop();
        System.exit(0);
    }

    // Add Mod Button
    private void button1MousePressed(MouseEvent event) {
        FileFilter filter = new FileNameExtensionFilter(null, "zip");

        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));
        chooser.setFileFilter(filter);

        int response = chooser.showOpenDialog(null);
        if (response == JFileChooser.APPROVE_OPTION) {
            TroveMods.addMod(chooser.getSelectedFile());
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
            String textLabel = simpleDateFormat.format(date) + " - 1 mod added!";
            setModLabel(textLabel);
        }
    }

    // Remove Mod
    private void button2MousePressed(MouseEvent event) {
        if (list1.getSelectedIndex() >= 0) {
            TroveMods.removeMod(list1.getSelectedIndex());
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
            String textLabel = simpleDateFormat.format(date) + " - 1 mod removed!";
            setModLabel(textLabel);
        }
    }

    // Load Mod List
    private void button5MousePressed(MouseEvent event) {
        FileFilter filter = new FileNameExtensionFilter(null, "txt");

        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Specify a file to load");
        chooser.setCurrentDirectory(new File("."));
        chooser.setFileFilter(filter);

        int response = chooser.showOpenDialog(null);
        if (response == JFileChooser.APPROVE_OPTION) {
            TroveUtils.addModsFromTextFile(chooser.getSelectedFile(), false);
        }
    }

    // Save Mod List
    private void button4MousePressed(MouseEvent event) {
        FileFilter filter = new FileNameExtensionFilter(null, "txt");

        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Specify a file to save");
        chooser.setCurrentDirectory(new File("."));
        chooser.setFileFilter(filter);

        int response = chooser.showSaveDialog(null);
        if (response == JFileChooser.APPROVE_OPTION) {
            TroveUtils.saveModsToTextFile(chooser.getSelectedFile());
        }
    }

    // Change location button
    private void button3MousePressed(MouseEvent event) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setDialogTitle("Trove Install Location");
        if (TroveUtils.getTroveInstallLocation() != null) {
            chooser.setCurrentDirectory(new File(TroveUtils.getTroveInstallLocation()));
        } else {
            chooser.setCurrentDirectory(new File("."));
        }

        int response = chooser.showOpenDialog(null);

        if (response == JFileChooser.OPEN_DIALOG) {
            TroveUtils.setTroveInstallLocation(String.valueOf(chooser.getSelectedFile()));

            File troveModLoaderDirectory = new File(System.getenv("APPDATA") + File.separator + "Trove-Mod-Loader-Java");
            File file = new File(troveModLoaderDirectory + File.separator + "settings.txt");
            TroveUtils.saveTextToFile(TroveUtils.getTroveInstallLocation(), file);

            TroveModLoader.getTroveModLoaderGUI().enabledButtons();
        }
    }

    public void enabledButtons() {
        button1.setEnabled(true);
        button2.setEnabled(true);
        button3.setEnabled(true);
        button4.setEnabled(true);
        button5.setEnabled(true);
    }

    public void disableButtons(boolean disableChangeFolder) {
        button1.setEnabled(false);
        button2.setEnabled(false);
        if (disableChangeFolder) {
            button3.setEnabled(false);
        }
        button4.setEnabled(false);
        button5.setEnabled(false);
    }

    public void setTroveStatusLabel(String text) {
        label2.setText(text);
    }

    public void setModLabel(String text) {
        label1.setText(text);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Cameron Weaver
        button3 = new JButton();
        scrollPane1 = new JScrollPane();
        list1 = new JList();
        button1 = new JButton();
        button2 = new JButton();
        button4 = new JButton();
        button5 = new JButton();
        label1 = new JLabel();
        label2 = new JLabel();

        //======== this ========
        setResizable(false);
        setTitle("Trove Mod Loader v1.3.1");
        setMinimumSize(new Dimension(475, 215));
        setIconImage(new ImageIcon(getClass().getResource("/com/xigbclutchix/trove/icon.png")).getImage());
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                thisWindowClosing(e);
            }
        });
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- button3 ----
        button3.setText("Change Trove Folder");
        button3.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                button3MousePressed(e);
            }
        });
        contentPane.add(button3);
        button3.setBounds(305, 125, 160, button3.getPreferredSize().height);

        //======== scrollPane1 ========
        {

            //---- list1 ----
            list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            list1.setMinimumSize(new Dimension(295, 147));
            list1.setMaximumSize(new Dimension(295, 147));
            scrollPane1.setViewportView(list1);
        }
        contentPane.add(scrollPane1);
        scrollPane1.setBounds(5, 5, 295, 147);

        //---- button1 ----
        button1.setText("Add Mod");
        button1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                button1MousePressed(e);
            }
        });
        contentPane.add(button1);
        button1.setBounds(305, 5, 160, button1.getPreferredSize().height);

        //---- button2 ----
        button2.setText("Remove Mod");
        button2.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                button2MousePressed(e);
            }
        });
        contentPane.add(button2);
        button2.setBounds(305, 35, 160, button2.getPreferredSize().height);

        //---- button4 ----
        button4.setText("Save Mod List");
        button4.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                button4MousePressed(e);
            }
        });
        contentPane.add(button4);
        button4.setBounds(305, 95, 160, button4.getPreferredSize().height);

        //---- button5 ----
        button5.setText("Load Mod List");
        button5.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                button5MousePressed(e);
            }
        });
        contentPane.add(button5);
        button5.setBounds(305, 65, 160, button5.getPreferredSize().height);

        //---- label1 ----
        label1.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(label1);
        label1.setBounds(5, 155, 295, 20);

        //---- label2 ----
        label2.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(label2);
        label2.setBounds(306, 155, 159, 20);

        { // compute preferred size
            Dimension preferredSize = new Dimension();
            for(int i = 0; i < contentPane.getComponentCount(); i++) {
                Rectangle bounds = contentPane.getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = contentPane.getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            contentPane.setMinimumSize(preferredSize);
            contentPane.setPreferredSize(preferredSize);
        }
        setSize(485, 215);
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Cameron Weaver
    private JButton button3;
    private JScrollPane scrollPane1;
    private JList list1;
    private JButton button1;
    private JButton button2;
    private JButton button4;
    private JButton button5;
    private JLabel label1;
    private JLabel label2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
