package dev.mf1.tinyradar.gui;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

class PresetsFrameTest {

    @Test
    @Disabled("Manual activation required")
    void launch() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        FlatMacDarkLaf.setup();
        Markers.load();

        var frame = new PresetsFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setTitle("Presets Test");
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
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