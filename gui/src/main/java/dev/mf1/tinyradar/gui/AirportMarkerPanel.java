package dev.mf1.tinyradar.gui;

import com.github.weisj.jsvg.SVGDocument;
import com.github.weisj.jsvg.parser.SVGLoader;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

final class AirportMarkerPanel extends TransparentPanel {

    private final SVGDocument document;

    AirportMarkerPanel() {
        var loader = new SVGLoader();
        document = loader.load(Resources.getAsStream("/airport_icon.svg"));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        document.render(this, g2d);
    }

}
