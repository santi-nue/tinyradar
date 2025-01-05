package dev.mf1.tinyradar.gui;

import com.google.common.eventbus.Subscribe;
import dev.mf1.tinyradar.core.TinyRadar;
import dev.mf1.tinyradar.core.al.Aircraft;
import dev.mf1.tinyradar.core.event.FlightsUpdateEvent;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Slf4j
final class AircraftTableModel extends AbstractTableModel {

    private final String[] columnNames = {"", "Callsign", "Type", "Altitude", "Velocity", "Category", "Reg"};
    private final List<Aircraft> flights = new ArrayList<>();

    @Setter
    private Aircraft selectedAircraft;

    @Setter
    private ListSelectionModel selectionModel;

    AircraftTableModel() {
        TinyRadar.BUS.register(this);
    }

    @Override
    public int getRowCount() {
        return flights.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        var item = flights.get(rowIndex);

        return switch (columnIndex) {
            case 0 -> item.getHex();
            case 1 -> item.getFlight();
            case 2 -> item.getT();
            case 3 -> item.getAltBaro();
            case 4 -> item.getAnySpeed();
            case 5 -> item.getCategory();
            case 6 -> item.getR();
            default -> null;
        };
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return switch (columnIndex) {
            case 0, 1, 2, 3, 4, 5, 6 -> String.class;
            default -> null;
        };
    }

    @Override
    public void fireTableDataChanged() {
        super.fireTableDataChanged();

        if (selectedAircraft == null)
            return;

        var index = IntStream.range(0, flights.size())
                .filter(i -> flights.get(i).getHex().equals(selectedAircraft.getHex()))
                .findFirst();

        if (index.isPresent()) {
            selectionModel.setSelectionInterval(index.getAsInt(), index.getAsInt());
        }
    }

    @Subscribe
    @SuppressWarnings("unused")
    public void onFlightsUpdateEvent(FlightsUpdateEvent event) {
        flights.clear();
        flights.addAll(event.flights());

        SwingUtilities.invokeLater(this::fireTableDataChanged);
    }

}
