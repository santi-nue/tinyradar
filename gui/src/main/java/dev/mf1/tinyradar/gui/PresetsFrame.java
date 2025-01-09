package dev.mf1.tinyradar.gui;

import dev.mf1.tinyradar.core.TinyRadar;
import dev.mf1.tinyradar.core.WGS84;
import dev.mf1.tinyradar.core.event.LocationChangeEvent;
import lombok.extern.slf4j.Slf4j;
import net.miginfocom.swing.MigLayout;
import org.jetbrains.annotations.NotNull;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.AbstractTableModel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class PresetsFrame extends JFrame {

    private final Map<String, CityPreset> presets = new HashMap<>();
    private final List<TinyRadar.Afb> afbs = TinyRadar.of().getAfbList();

    PresetsFrame() {
        init();

        var pane = new JTabbedPane();

        JPanel cities = new JPanel(new MigLayout("wrap 5", "[grow, fill]5[grow, fill]5[grow, fill]5[grow, fill]5[grow, fill]", "[]10[]"));
        presets.forEach((k, v) -> {
            var resource = Resources.get("/flags/" + v.code + ".png");

            var button = new JButton(k, new ImageIcon(resource));
            button.addActionListener(e -> {
                log.info("Preset: {}", k);

                TinyRadar.pos = v.wgs84();
                TinyRadar.BUS.post(new LocationChangeEvent(v.wgs84));
                TinyRadar.of().updateAirportsInRange();
                Gui.rootFrame.repaint();
            });

            cities.add(button);
        });

        JScrollPane afbScroll = createAfbScroll();

        pane.addTab("Cities", cities);
        pane.addTab("AFB", afbScroll);

        add(pane);
        pack();
        setResizable(false);
        setTitle("Map Preset");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(Gui.rootFrame);
        setVisible(true);
    }

    private @NotNull JScrollPane createAfbScroll() {
        var afbTable = new JTable(new AfbTableModel(afbs));

        var selectionModel = afbTable.getSelectionModel();
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        selectionModel.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = afbTable.getSelectedRow();

                if (selectedRow != -1) {
                    var afb = (TinyRadar.Afb) afbs.get(selectedRow);

                    TinyRadar.pos = afb.location();
                    TinyRadar.BUS.post(new LocationChangeEvent(afb.location()));
                    TinyRadar.of().updateAirportsInRange();
                    Gui.rootFrame.repaint();
                }
            }
        });

        JScrollPane afbScroll = new JScrollPane(afbTable);
        afbScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        return afbScroll;
    }

    private void init() {
        presets.put("Tokyo", new CityPreset(new WGS84(35.6895f, 139.6917f), "jp"));
        presets.put("New York", new CityPreset(new WGS84(40.7128f, -74.0060f), "us"));
        presets.put("London", new CityPreset(new WGS84(51.5074f, -0.1278f), "gb"));
        presets.put("Paris", new CityPreset(new WGS84(48.8566f, 2.3522f), "fr"));
        presets.put("Moscow", new CityPreset(new WGS84(55.7558f, 37.6173f), "ru"));
        presets.put("Beijing", new CityPreset(new WGS84(39.9042f, 116.4074f), "cn"));
        presets.put("Sydney", new CityPreset(new WGS84(-33.8688f, 151.2093f), "au"));
        presets.put("Rio de Janeiro", new CityPreset(new WGS84(-22.9068f, -43.1729f), "br"));
        presets.put("Cape Town", new CityPreset(new WGS84(-33.9249f, 18.4241f), "za"));
        presets.put("Singapore", new CityPreset(new WGS84(1.3521f, 103.8198f), "sg"));
        presets.put("Los Angeles", new CityPreset(new WGS84(34.0522f, -118.2437f), "us"));
        presets.put("Berlin", new CityPreset(new WGS84(52.5200f, 13.4050f), "de"));
        presets.put("Dubai", new CityPreset(new WGS84(25.276987f, 55.296249f), "ae"));
        presets.put("Istanbul", new CityPreset(new WGS84(41.0082f, 28.9784f), "tr"));
        presets.put("Seoul", new CityPreset(new WGS84(37.5665f, 126.9780f), "kr"));
        presets.put("Mexico City", new CityPreset(new WGS84(19.4326f, -99.1332f), "mx"));
        presets.put("Mumbai", new CityPreset(new WGS84(19.0760f, 72.8777f), "in"));
        presets.put("Bangkok", new CityPreset(new WGS84(13.7563f, 100.5018f), "th"));
        presets.put("Buenos Aires", new CityPreset(new WGS84(-34.6037f, -58.3816f), "ar"));
        presets.put("Cairo", new CityPreset(new WGS84(30.0444f, 31.2357f), "eg"));
        presets.put("Toronto", new CityPreset(new WGS84(43.65107f, -79.347015f), "ca"));
        presets.put("Chicago", new CityPreset(new WGS84(41.8781f, -87.6298f), "us"));
        presets.put("Hong Kong", new CityPreset(new WGS84(22.3193f, 114.1694f), "hk"));
        presets.put("Madrid", new CityPreset(new WGS84(40.4168f, -3.7038f), "es"));
        presets.put("Rome", new CityPreset(new WGS84(41.9028f, 12.4964f), "it"));
        presets.put("Jakarta", new CityPreset(new WGS84(-6.2088f, 106.8456f), "id"));
        presets.put("Lagos", new CityPreset(new WGS84(6.5244f, 3.3792f), "ng"));
        presets.put("Delhi", new CityPreset(new WGS84(28.7041f, 77.1025f), "in"));
        presets.put("San Francisco", new CityPreset(new WGS84(37.7749f, -122.4194f), "us"));
        presets.put("Kuala Lumpur", new CityPreset(new WGS84(3.1390f, 101.6869f), "my"));
        presets.put("São Paulo", new CityPreset(new WGS84(-23.5505f, -46.6333f), "br"));
        presets.put("Lima", new CityPreset(new WGS84(-12.0464f, -77.0428f), "pe"));
        presets.put("Johannesburg", new CityPreset(new WGS84(-26.2041f, 28.0473f), "za"));
        presets.put("Kolkata", new CityPreset(new WGS84(22.5726f, 88.3639f), "in"));
        presets.put("Tehran", new CityPreset(new WGS84(35.6892f, 51.3890f), "ir"));
        presets.put("Baghdad", new CityPreset(new WGS84(33.3152f, 44.3661f), "iq"));
        presets.put("Santiago", new CityPreset(new WGS84(-33.4489f, -70.6693f), "cl"));
        presets.put("Karachi", new CityPreset(new WGS84(24.8607f, 67.0011f), "pk"));
        presets.put("Ho Chi Minh City", new CityPreset(new WGS84(10.8231f, 106.6297f), "vn"));
        presets.put("Casablanca", new CityPreset(new WGS84(33.5731f, -7.5898f), "ma"));
        presets.put("Athens", new CityPreset(new WGS84(37.9838f, 23.7275f), "gr"));
        presets.put("Havana", new CityPreset(new WGS84(23.1136f, -82.3666f), "cu"));
        presets.put("Addis Ababa", new CityPreset(new WGS84(9.0084f, 38.7648f), "et"));
        presets.put("Algiers", new CityPreset(new WGS84(36.7372f, 3.0865f), "dz"));
        presets.put("Manila", new CityPreset(new WGS84(14.5995f, 120.9842f), "ph"));
        presets.put("Perth", new CityPreset(new WGS84(-31.9505f, 115.8605f), "au"));
        presets.put("Vancouver", new CityPreset(new WGS84(49.2827f, -123.1207f), "ca"));
        presets.put("Zurich", new CityPreset(new WGS84(47.3769f, 8.5417f), "ch"));
        presets.put("Melbourne", new CityPreset(new WGS84(-37.8136f, 144.9631f), "au"));
        presets.put("Warsaw", new CityPreset(new WGS84(52.2298f, 21.0118f), "pl"));
        presets.put("Boston", new CityPreset(new WGS84(42.3601f, -71.0589f), "us"));
        presets.put("Miami", new CityPreset(new WGS84(25.7617f, -80.1918f), "us"));
        presets.put("Seattle", new CityPreset(new WGS84(47.6062f, -122.3321f), "us"));
        presets.put("Dallas", new CityPreset(new WGS84(32.7767f, -96.7970f), "us"));
        presets.put("Phoenix", new CityPreset(new WGS84(33.4484f, -112.0740f), "us"));
        presets.put("Las Vegas", new CityPreset(new WGS84(36.1699f, -115.1398f), "us"));
        presets.put("San Diego", new CityPreset(new WGS84(32.7157f, -117.1611f), "us"));
        presets.put("Portland", new CityPreset(new WGS84(45.5051f, -122.6750f), "us"));
        presets.put("Denver", new CityPreset(new WGS84(39.7392f, -104.9903f), "us"));
        presets.put("Atlanta", new CityPreset(new WGS84(33.7490f, -84.3880f), "us"));
        presets.put("Philadelphia", new CityPreset(new WGS84(39.9526f, -75.1652f), "us"));
        presets.put("Oslo", new CityPreset(new WGS84(59.9139f, 10.7522f), "no"));
        presets.put("Stockholm", new CityPreset(new WGS84(59.3293f, 18.0686f), "se"));
        presets.put("Copenhagen", new CityPreset(new WGS84(55.6761f, 12.5683f), "dk"));
        presets.put("Helsinki", new CityPreset(new WGS84(60.1695f, 24.9354f), "fi"));
        presets.put("Prague", new CityPreset(new WGS84(50.0755f, 14.4378f), "cz"));
        presets.put("Budapest", new CityPreset(new WGS84(47.4979f, 19.0402f), "hu"));
        presets.put("Vienna", new CityPreset(new WGS84(48.2082f, 16.3738f), "at"));
        presets.put("Munich", new CityPreset(new WGS84(48.1351f, 11.5820f), "de"));
        presets.put("Brussels", new CityPreset(new WGS84(50.8503f, 4.3517f), "be"));
        presets.put("Edinburgh", new CityPreset(new WGS84(55.9533f, -3.1883f), "gb"));
        presets.put("Florence", new CityPreset(new WGS84(43.7696f, 11.2558f), "it"));
    }

    private record CityPreset(WGS84 wgs84, String code) {
    }

    private static class AfbTableModel extends AbstractTableModel {

        private final String[] columnNames = {"Name", "Area", "Org"};
        private final List<TinyRadar.Afb> locations;

        public AfbTableModel(List<TinyRadar.Afb> locations) {
            this.locations = locations;
        }

        @Override
        public int getRowCount() {
            return locations.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            var item = locations.get(rowIndex);

            return switch (columnIndex) {
                case 0 -> item.name();
                case 1 -> item.area();
                case 2 -> item.org();
                default -> null;
            };
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }
    }

}
