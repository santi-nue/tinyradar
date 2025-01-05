package dev.mf1.tinyradar.gui;

import javax.swing.JPanel;
import java.awt.LayoutManager;

public class TransparentPanel extends JPanel {

    public TransparentPanel() {
        this.setOpaque(false);
    }

    public TransparentPanel(LayoutManager manager) {
        this.setOpaque(false);
        this.setLayout(manager);
    }

}
