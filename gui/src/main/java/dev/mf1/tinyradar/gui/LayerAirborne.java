package dev.mf1.tinyradar.gui;

import com.google.common.eventbus.Subscribe;
import dev.mf1.tinyradar.core.TinyRadar;
import dev.mf1.tinyradar.core.WGS84;
import dev.mf1.tinyradar.core.al.Aircraft;
import dev.mf1.tinyradar.core.event.AircraftSelectionEvent;
import dev.mf1.tinyradar.core.event.FlightsUpdateEvent;
import dev.mf1.tinyradar.gui.map.MapUtils;

import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

final class LayerAirborne extends TransparentPanel {

    private final List<Aircraft> flights = new CopyOnWriteArrayList<>();
    private final Map<String, AircraftMarkerPanel> markers = new HashMap<>();
    private Aircraft selectedAircraft;

    LayerAirborne() {
        TinyRadar.BUS.register(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        markers.clear();

        final Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);

        if (flights.isEmpty()) {
            return;
        }

        removeAll();

        flights.forEach(item -> drawContact(g2d, item));
    }

    @Subscribe
    @SuppressWarnings("unused")
    public void onAircraftSelectionEvent(AircraftSelectionEvent event) {
        selectedAircraft = event.aircraft();
        repaint();
    }

    @Subscribe
    @SuppressWarnings("unused")
    public void onFlightsUpdateEvent(FlightsUpdateEvent event) {
        flights.clear();
        flights.addAll(event.flights());

        SwingUtilities.invokeLater(this::repaint);
    }

    private void drawContact(Graphics2D g2d, Aircraft contact) {
        var w = getWidth();
        var h = getHeight();
        var p = new WGS84(contact.getLat().floatValue(), contact.getLon().floatValue());
        var proj = MapUtils.getScreenPosition(p, TinyRadar.zoom, TinyRadar.pos, w, h);

        var doc = Markers.resolve(contact);
        AircraftMarkerPanel panel = new AircraftMarkerPanel(doc, contact);
        markers.put(contact.getHex(), panel);

        var x = proj[0] - (int) doc.size().getWidth() / 2;
        var y = proj[1] - (int) doc.size().getHeight() / 2;

        panel.setBounds(x, y, (int) doc.size().getWidth(), (int) doc.size().getHeight());
        panel.setDegrees(contact.getAnyHeading());
        add(panel);

        g2d.setColor(Color.decode("#F7F7F7"));
        if (contact.getFlight() != null) {

            if (selectedAircraft != null && selectedAircraft.equals(contact)) {
                g2d.setColor(Color.GREEN);
            }

            var font = g2d.getFont();
            var mono = new Font("Monospaced", font.getStyle(), font.getSize());
//            g2d.setFont(mono);

            g2d.drawString(
                    contact.getFlight(),
                    proj[0] + (int) doc.size().getWidth() / 2 + 3,
                    proj[1] + (int) doc.size().getHeight() / 2 + 3
            );

//            setFont(font);
        }
    }

}
