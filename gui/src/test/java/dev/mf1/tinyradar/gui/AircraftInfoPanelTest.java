package dev.mf1.tinyradar.gui;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import dev.mf1.tinyradar.core.TinyRadar;
import dev.mf1.tinyradar.core.WGS84;
import dev.mf1.tinyradar.core.util.DummyObjects;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.CountDownLatch;

class AircraftInfoPanelTest {

    @Test
    @Disabled("Manual activation required")
    void demo() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        TinyRadar.pos = new WGS84(51.47002f, -0.45429f);
        FlatMacDarkLaf.setup();
        Markers.load();

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setTitle("Info Panel Test");
        frame.setSize(300, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                latch.countDown();
            }
        });

        var aircraft = DummyObjects.getList().get(1);

        var infoPanel = new AircraftInfoPanel();
        infoPanel.display(aircraft);
        infoPanel.displayPicture();

        frame.add(infoPanel);

        SwingUtilities.invokeLater(() -> frame.setVisible(true));
        latch.await();
    }

}