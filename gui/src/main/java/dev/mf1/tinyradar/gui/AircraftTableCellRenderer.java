package dev.mf1.tinyradar.gui;

import dev.mf1.tinyradar.core.util.IcaoRange;
import lombok.extern.slf4j.Slf4j;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import java.awt.Component;

@Slf4j
final class AircraftTableCellRenderer extends JLabel implements TableCellRenderer {

    AircraftTableCellRenderer() {
        setOpaque(true);
        setHorizontalAlignment(CENTER);
        setVerticalAlignment(CENTER);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
            setBackground(table.getSelectionBackground());
        } else {
            setBackground(table.getBackground());
        }

        var country = IcaoRange.getCountry((String) value);
        var countryCode = (country == null) ? "xy" : country.getCountryCode();
        var countryName = (country == null) ? "N/A" : country.getCountry();
        var resource = Resources.get("/flags/" + countryCode + ".png");

        if (resource != null) {
            setIcon(new ImageIcon(resource));
        } else {
            log.error("Could not find resource: {}", "/flags/" + countryCode + ".png");
        }

        setToolTipText(countryName);
        setText(null);
        return this;
    }
}
