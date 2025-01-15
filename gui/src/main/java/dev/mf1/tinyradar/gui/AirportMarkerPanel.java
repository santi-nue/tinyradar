package dev.mf1.tinyradar.gui;

import com.github.weisj.jsvg.SVGDocument;
import com.github.weisj.jsvg.parser.SVGLoader;

import java.awt.Graphics;
import java.awt.Graphics2D;

final class AirportMarkerPanel extends TransparentPanel {

    private static final SVGDocument doc = new SVGLoader().load(Resources.getAsStream("/airport_icon.svg"));

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        Gui.applyQualityRenderingHints(g2d);

        doc.render(this, g2d);
        g2d.dispose();
    }

}
