package dev.mf1.tinyradar.gui;

import com.github.weisj.jsvg.SVGDocument;
import dev.mf1.tinyradar.core.TinyRadar;
import dev.mf1.tinyradar.core.al.Aircraft;
import dev.mf1.tinyradar.core.event.AircraftSelectionEvent;
import lombok.Setter;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;

public class AircraftMarkerPanel extends TransparentPanel {

    private final SVGDocument document;

    @Setter
    private double degrees;

    AircraftMarkerPanel(SVGDocument document, Aircraft aircraft) {
        this.document = document;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                TinyRadar.BUS.post(new AircraftSelectionEvent(aircraft));
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        AffineTransform oldTransform = g2d.getTransform();
        int cx = getWidth() / 2;
        int cy = getHeight() / 2;
        g2d.rotate(Math.toRadians(degrees), cx, cy);

        document.render(this, g2d);

        g2d.setTransform(oldTransform);
    }
}
