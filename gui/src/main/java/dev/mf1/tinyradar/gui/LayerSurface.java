package dev.mf1.tinyradar.gui;

import dev.mf1.tinyradar.core.TinyRadar;
import dev.mf1.tinyradar.core.WGS84;
import dev.mf1.tinyradar.gui.map.MapUtils;

import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LayerSurface extends TransparentPanel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        removeAll();

        if (TinyRadar.zoom < 8)
            return;

        final Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);

        g2d.setColor(Color.decode("#A020F0"));

        TinyRadar.of().getAirportInRangeList().forEach(a -> {
            var w = getWidth();
            var h = getHeight();
            var pos = new WGS84((float) a.getLatitude(), (float) a.getLongitude());
            var proj = MapUtils.getScreenPosition(pos, TinyRadar.zoom, TinyRadar.pos, w, h);

            var airportSvg = new AirportMarkerPanel();
            airportSvg.setBounds(proj[0] - 8, proj[1] - 8, 16, 16);
            airportSvg.setToolTipText("%s %n %s".formatted(a.getName(), a.getIdent()));
            add(airportSvg);
        });

    }

}
