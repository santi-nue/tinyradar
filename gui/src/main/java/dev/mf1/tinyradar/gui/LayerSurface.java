package dev.mf1.tinyradar.gui;

import dev.mf1.tinyradar.core.TinyRadar;
import dev.mf1.tinyradar.core.WGS84;
import dev.mf1.tinyradar.gui.map.MapUtils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class LayerSurface extends TransparentPanel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

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

            g2d.fillRect(proj[0], proj[1], 3, 3);
            g2d.drawString(a.getIdent(), proj[0] + 4, proj[1] + 4);
        });

    }

}
