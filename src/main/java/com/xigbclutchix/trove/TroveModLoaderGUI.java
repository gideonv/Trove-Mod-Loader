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
    }

    private void thisWindowClosing(WindowEvent event) {
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
        }
    }

    // Remove Mod
    private void button2MousePressed(MouseEvent event) {
        if (list1.getSelectedIndex() >= 0) {
            TroveMods.removeMod(list1.getSelectedIndex());
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
            TroveUtils.addModsFromTextFile(chooser.getSelectedFile());
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

    // Run Button
    private void button3MousePressed(MouseEvent event) {
        if (TroveMods.getMods().size() > 0) {
            if (button3.getText().equalsIgnoreCase("Start")) {
                disableButtons();
                TroveTimer.init();
            } else if (button3.getText().equalsIgnoreCase("Stop")) {
                TroveTimer.stop();
                enabledButtons();
            }
        }
    }

    public static void enabledButtons() {
        button3.setText("Start");
        button1.setEnabled(true);
        button2.setEnabled(true);
        button4.setEnabled(true);
        button5.setEnabled(true);
    }

    public static void disableButtons() {
        button3.setText("Stop");
        button1.setEnabled(false);
        button2.setEnabled(false);
        button4.setEnabled(false);
        button5.setEnabled(false);
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

        //======== this ========
        setResizable(false);
        setTitle("Trove Mod Loader v1.1.0");
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                thisWindowClosing(e);
            }
        });
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- button3 ----
        button3.setText("Start");
        button3.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                button3MousePressed(e);
            }
        });
        contentPane.add(button3);
        button3.setBounds(305, 125, 135, button3.getPreferredSize().height);

        //======== scrollPane1 ========
        {

            //---- list1 ----
            list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            scrollPane1.setViewportView(list1);
        }
        contentPane.add(scrollPane1);
        scrollPane1.setBounds(5, 5, 295, 150);

        //---- button1 ----
        button1.setText("Add Mod");
        button1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                button1MousePressed(e);
            }
        });
        contentPane.add(button1);
        button1.setBounds(305, 5, 135, button1.getPreferredSize().height);

        //---- button2 ----
        button2.setText("Remove Mod");
        button2.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                button2MousePressed(e);
            }
        });
        contentPane.add(button2);
        button2.setBounds(305, 35, 135, button2.getPreferredSize().height);

        //---- button4 ----
        button4.setText("Save Mod List");
        button4.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                button4MousePressed(e);
            }
        });
        contentPane.add(button4);
        button4.setBounds(305, 95, 135, button4.getPreferredSize().height);

        //---- button5 ----
        button5.setText("Load Mod List");
        button5.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                button5MousePressed(e);
            }
        });
        contentPane.add(button5);
        button5.setBounds(305, 65, 135, button5.getPreferredSize().height);

        contentPane.setPreferredSize(new Dimension(460, 195));
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Cameron Weaver
    private static JButton button3;
    private JScrollPane scrollPane1;
    private JList list1;
    private static JButton button1;
    private static JButton button2;
    private static JButton button4;
    private static JButton button5;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
