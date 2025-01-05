package dev.mf1.tinyradar.gui;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.util.UIScale;
import dev.mf1.tinyradar.core.TinyRadar;
import dev.mf1.tinyradar.core.WGS84;
import dev.mf1.tinyradar.core.event.LocationChangeEvent;
import lombok.extern.slf4j.Slf4j;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Gui {

    public static SimpleFrame rootFrame;
    private static final List<ShutdownListener> shutdownListeners = new ArrayList<>();

    private Gui() {
    }

    public static void main(String[] args) {
        log.info("Starting TinyRadar");

        Thread.currentThread().setUncaughtExceptionHandler(new DefaultExceptionHandler());
        FlatMacDarkLaf.setup();
        Markers.load();

        TinyRadar.pos = new WGS84(40.6397f, -73.7788f);
        TinyRadar.of().loadAirports();
        TinyRadar.of().launch();

        SwingUtilities.invokeLater(() -> {
            rootFrame = new SimpleFrame();
            rootFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    shutdownListeners.forEach(ShutdownListener::onShutdown);
                }
            });

            TinyRadar.BUS.post(new LocationChangeEvent(TinyRadar.pos));
            log.info(systemInfo());
        });
    }

    public static void addShutdownListener(ShutdownListener listener) {
        shutdownListeners.add(listener);
    }

    private static String systemInfo() {
        String javaVendor = System.getProperty( "java.vendor" );
        if( "Oracle Corporation".equals( javaVendor ) )
            javaVendor = null;
        double systemScaleFactor = UIScale.getSystemScaleFactor(rootFrame.getGraphicsConfiguration());
        float userScaleFactor = UIScale.getUserScaleFactor();
        Font font = UIManager.getFont( "Label.font" );

        return "(Java " + System.getProperty( "java.version" )
                + (javaVendor != null ? ("; " + javaVendor) : "")
                + (systemScaleFactor != 1 ? (";  system scale factor " + systemScaleFactor) : "")
                + (userScaleFactor != 1 ? (";  user scale factor " + userScaleFactor) : "")
                + (systemScaleFactor == 1 && userScaleFactor == 1 ? "; no scaling" : "")
                + "; " + font.getFamily() + " " + font.getSize()
                + (font.isBold() ? " BOLD" : "")
                + (font.isItalic() ? " ITALIC" : "")
                + ")";
    }

}
