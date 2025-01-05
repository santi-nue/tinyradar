package dev.mf1.tinyradar.gui;

import net.miginfocom.swing.MigLayout;

import javax.swing.JFrame;

@SuppressWarnings("unused")
public final class SpecAircraftFrame extends JFrame {

    SpecAircraftFrame() {
        setLayout(new MigLayout("fill"));

        pack();
        setResizable(false);
        setTitle("Geo preset");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(Gui.rootFrame);
        setVisible(true);
    }

}
