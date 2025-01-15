package dev.mf1.tinyradar.gui;

import com.github.weisj.jsvg.SVGDocument;
import dev.mf1.tinyradar.core.TinyRadar;
import dev.mf1.tinyradar.core.al.Aircraft;
import dev.mf1.tinyradar.core.event.AircraftSelectionEvent;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;

public class AircraftMarkerPanel extends TransparentPanel {

    private final SVGDocument document;

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

    public void setDegrees(double degrees) {
        this.degrees = degrees;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        Gui.applyQualityRenderingHints(g2d);

        AffineTransform oldTransform = g2d.getTransform();
        int cx = getWidth() / 2;
        int cy = getHeight() / 2;
        g2d.rotate(Math.toRadians(degrees), cx, cy);

        document.render(this, g2d);

        g2d.setTransform(oldTransform);
        g2d.dispose();
    }
}
