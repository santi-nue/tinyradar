package dev.mf1.tinyradar.gui;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.google.common.eventbus.Subscribe;
import dev.mf1.tinyradar.core.TinyRadar;
import dev.mf1.tinyradar.core.WGS84;
import dev.mf1.tinyradar.core.al.Aircraft;
import dev.mf1.tinyradar.core.event.AircraftSelectionEvent;
import dev.mf1.tinyradar.core.event.FlightsUpdateEvent;
import dev.mf1.tinyradar.core.event.LocationChangeEvent;
import net.miginfocom.swing.MigLayout;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class AircraftTablePanel extends JPanel {

    private final AircraftTableModel tableModel = new AircraftTableModel();
    private final JTable table;

    private final List<Aircraft> flights = new ArrayList<>();
    private final JLabel counterLabel = new JLabel();

    private Aircraft selectedAircraft;
    private Aircraft followedAircraft;

    AircraftTablePanel() {
        setLayout(new MigLayout("fill, wrap 1, insets 0", "[grow, fill]", "[grow, fill][]"));

        table = new JTable(tableModel);
        table.setFocusable(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getColumnModel().getColumn(0).setPreferredWidth(20);
        table.getColumnModel().getColumn(2).setPreferredWidth(50);
        table.getColumnModel().getColumn(0).setCellRenderer(new AircraftTableCellRenderer());
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();

                if (row == -1)
                    return;

                selectedAircraft = flights.get(row);
                tableModel.setSelectedAircraft(selectedAircraft);
                TinyRadar.BUS.post(new AircraftSelectionEvent(selectedAircraft));
            }
        });

        tableModel.setSelectionModel(table.getSelectionModel());

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(300, 400));
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        counterLabel.setText("Tracking 0 flights");

        add(scrollPane, "grow, push");
        add(counterLabel);

        JPopupMenu popupMenu = new JPopupMenu();
        var followAircraft = new JMenuItem(new FlatSVGIcon(Resources.get("/icons/map-pin.svg")));
        followAircraft.setText("Follow Aircraft");
        followAircraft.addActionListener(e -> {
            var index = table.getSelectedRow();
            if (index == -1)
                return;

            var f = flights.get(index);

            followedAircraft = f;

            var lat = f.getLat().floatValue();
            var lon = f.getLon().floatValue();

            var pos = new WGS84(lat, lon);
            TinyRadar.pos = pos;
            TinyRadar.BUS.post(new LocationChangeEvent(pos));
            TinyRadar.of().updateAirportsInRange();
            Gui.rootFrame.repaint();
        });

        popupMenu.add(followAircraft);
        table.setComponentPopupMenu(popupMenu);

        TinyRadar.BUS.register(this);
    }

    @Subscribe
    @SuppressWarnings("unused")
    public void onAircraftSelectionEvent(AircraftSelectionEvent event) {
        if (!event.aircraft().equals(followedAircraft))
            followedAircraft = null;

        if (event.aircraft().equals(selectedAircraft)) {
            return;
        }

        var index = flights.indexOf(event.aircraft());
        table.getSelectionModel().setSelectionInterval(index, index);
        tableModel.setSelectedAircraft(event.aircraft());
    }

    @Subscribe
    @SuppressWarnings("unused")
    public void onFlightsUpdateEvent(FlightsUpdateEvent event) {
        flights.clear();
        flights.addAll(event.flights());

        SwingUtilities.invokeLater(this::updateFlightsCounter);

        if (followedAircraft != null) {
            if (!flights.contains(followedAircraft)) {
                followedAircraft = null;
                return;
            }

            var f = flights.stream()
                    .filter(e -> e.getHex().equals(followedAircraft.getHex()))
                    .findFirst();

            TinyRadar.pos = new WGS84(f.get().getLat().floatValue(), f.get().getLon().floatValue());
            TinyRadar.BUS.post(new LocationChangeEvent(TinyRadar.pos));
            TinyRadar.of().updateAirportsInRange();
        }
    }

    private void updateFlightsCounter() {
        counterLabel.setText("Tracking %d flights".formatted(flights.size()));
    }
}
