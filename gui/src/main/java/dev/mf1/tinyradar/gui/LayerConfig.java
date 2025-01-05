package dev.mf1.tinyradar.gui;

import java.awt.BorderLayout;

final class LayerConfig extends TransparentPanel {

    LayerConfig() {
        setLayout(new BorderLayout());

        UtcTimePanel utcPanel = new UtcTimePanel();
        var eastPanel = new TransparentPanel(new BorderLayout());
        eastPanel.add(utcPanel, BorderLayout.NORTH);

        add(eastPanel, BorderLayout.EAST);
    }
}
