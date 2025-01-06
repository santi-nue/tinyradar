package dev.mf1.tinyradar.gui;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import dev.mf1.tinyradar.core.TinyRadar;
import dev.mf1.tinyradar.core.TinyRadarException;
import dev.mf1.tinyradar.core.WGS84;
import dev.mf1.tinyradar.core.event.FlightsUpdateEvent;
import dev.mf1.tinyradar.core.util.DummyObjects;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;

public class ImageRotationTest {

    @Test
    @Disabled("Manual activation required")
    void demo() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        TinyRadar.pos = new WGS84(51.47002f, -0.45429f);
        FlatMacDarkLaf.setup();
        Markers.load();

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setTitle("Image Rotation Test");
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        LayerAirborne layer = new LayerAirborne();
        frame.add(layer);

        var list = DummyObjects.getList().subList(1, 2);
        var a = list.get(0);

        CompletableFuture.runAsync(() -> {
            for (int i = 0; i < 360; i++) {
                a.setTrack((double) i);
                layer.onFlightsUpdateEvent(new FlightsUpdateEvent(List.of(a)));

                System.out.println(i);

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new TinyRadarException(e);
                }
            }
        });

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                latch.countDown();
            }
        });

        SwingUtilities.invokeLater(() -> frame.setVisible(true));

        latch.await();
    }

}
