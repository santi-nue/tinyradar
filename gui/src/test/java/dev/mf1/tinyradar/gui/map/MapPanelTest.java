package dev.mf1.tinyradar.gui.map;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import dev.mf1.tinyradar.core.TinyRadar;
import dev.mf1.tinyradar.core.WGS84;
import dev.mf1.tinyradar.core.event.LocationChangeEvent;
import dev.mf1.tinyradar.gui.Markers;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.CountDownLatch;

class MapPanelTest {

    @Test
    @Disabled("Manual activation required")
    void demo() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        TinyRadar.pos = new WGS84(51.47002f, -0.45429f);
        FlatMacDarkLaf.setup();
        TinyRadar.of().setup();
        TinyRadar.of().updateAirportsInRange();
        Markers.load();

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setTitle("Map Test");
        frame.setPreferredSize(new Dimension(640, 480));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.pack();
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                latch.countDown();
            }
        });

        MapPanel mapPanel = new MapPanel();
        frame.add(mapPanel);

        SwingUtilities.invokeLater(() -> {
            frame.setVisible(true);
            TinyRadar.BUS.post(new LocationChangeEvent(TinyRadar.pos));
            frame.repaint();
        });

        latch.await();
    }

}