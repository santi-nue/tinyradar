package dev.mf1.tinyradar.gui;

import lombok.extern.slf4j.Slf4j;
import net.miginfocom.swing.MigLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ScrollPaneConstants;
import java.util.prefs.Preferences;

@Slf4j
final class ControlsPanel extends JPanel implements ShutdownListener {

    public final AircraftInfoPanel infoPanel = new AircraftInfoPanel();

    private final Preferences preferences = Preferences.userNodeForPackage(ControlsPanel.class);
    private final JSplitPane splitPane;

    ControlsPanel() {
        setLayout(new MigLayout("insets 0, gap 0", "[grow]", "[][grow]"));

        AircraftTablePanel tablePanel = new AircraftTablePanel();
        var infoPanelScroll = new JScrollPane(
                infoPanel,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );
        infoPanelScroll.getVerticalScrollBar().setUnitIncrement(13);

        splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, infoPanelScroll, tablePanel);
        splitPane.setDividerLocation(preferences.getInt("controlsDivider", 200));

        add(new ToolbarPanel(), "cell 0 0");
        add(splitPane, "cell 0 1, grow");

        Gui.addShutdownListener(this);
    }

    @Override
    public void onShutdown() {
        preferences.putInt("controlsDivider", splitPane.getDividerLocation());
    }

}
