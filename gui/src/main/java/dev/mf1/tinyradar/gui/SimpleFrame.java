package dev.mf1.tinyradar.gui;

import net.miginfocom.swing.MigLayout;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.Dimension;

public final class SimpleFrame extends JFrame {

    public final LayeredDisplayPane display = new LayeredDisplayPane();

    public SimpleFrame() {
        ControlsPanel controls = new ControlsPanel();

        setLayout(new MigLayout("insets 0, gap 0", "[grow, fill][300!, fill]", "[]"));
        add(display, "cell 0 0, grow, push");
        add(controls, "cell 1 0, grow, push");

        setTitle("TinyRadar 1.0");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(new Dimension(1280, 720));
        setMinimumSize(new Dimension(640, 480));
        setLocationRelativeTo(null);
        setVisible(true);
    }

}
