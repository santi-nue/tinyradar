package dev.mf1.tinyradar.gui;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.formdev.flatlaf.FlatClientProperties;
import com.google.common.eventbus.Subscribe;
import dev.mf1.tinyradar.core.TinyRadar;
import dev.mf1.tinyradar.core.TinyRadarException;
import dev.mf1.tinyradar.core.al.Aircraft;
import dev.mf1.tinyradar.core.event.AircraftSelectionEvent;
import dev.mf1.tinyradar.core.event.FlightsUpdateEvent;
import dev.mf1.tinyradar.core.ps.PlaneSpotterService;
import dev.mf1.tinyradar.core.ps.PlaneSpottersResponse;
import dev.mf1.tinyradar.core.util.HttpFileDownloader;
import dev.mf1.tinyradar.core.util.IcaoRange;
import lombok.extern.slf4j.Slf4j;
import net.miginfocom.swing.MigLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Container;
import java.awt.Image;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;

@Slf4j
public class AircraftInfoPanel extends JPanel {

    private final JLabel headerFlightLabel = new JLabel();
    private final JLabel headerTypeLabel = new JLabel();
    private final JLabel thumbnailLabel = new JLabel();
    private final JLabel aircraftTypeValue = new JLabel("");
    private final JLabel aircraftRegValue = new JLabel("");
    private final JLabel aircraftCompValue = new JLabel("");
    private final JLabel barometricAlt = new JLabel("");
    private final JLabel verticalSpeed = new JLabel("");
    private final JLabel gpsAlt = new JLabel("");
    private final JLabel track = new JLabel("");
    private final JLabel groundSpeed = new JLabel();
    private final JLabel trueSpeed = new JLabel();
    private final JLabel indicatedSpeed = new JLabel();
    private final JLabel machSpeed = new JLabel();
    private final JLabel sourceIcao = new JLabel("");
    private final JLabel sourceSquawk = new JLabel("");
    private final JLabel sourceLat = new JLabel("");
    private final JLabel sourceLon = new JLabel("");

    private Aircraft aircraft;

    private final ImageIcon noImage = new ImageIcon(scaleImage(new ImageIcon(Resources.get("/no_image.jpg"))));

    AircraftInfoPanel() {
        TinyRadar.BUS.register(this);

        setLayout(new MigLayout("wrap 1, insets 0", "[grow, fill]", "[]"));
        setSize(300, 480);
        setDoubleBuffered(true);

        var header = new JPanel(new MigLayout("wrap 2, insets 0 5 0 5"));

        headerFlightLabel.putClientProperty(FlatClientProperties.STYLE_CLASS, "h3");

        headerTypeLabel.putClientProperty(FlatClientProperties.STYLE, "arc: 10; border: 2,3,2,3");
        headerTypeLabel.putClientProperty(FlatClientProperties.STYLE_CLASS, "h4");
        headerTypeLabel.setBackground(Color.decode("#60B515"));
        headerTypeLabel.setForeground(Color.decode("#1e1e1e"));
        headerTypeLabel.setVisible(false);

        header.add(headerFlightLabel);
        header.add(headerTypeLabel);

        thumbnailLabel.setHorizontalAlignment(SwingConstants.CENTER);

        var mainSection = new JPanel();
        mainSection.setLayout(new MigLayout("wrap 1", "[grow]", "2[]4[]2[]4[]2[]4"));
        mainSection.setBackground(Color.decode("#2a2a2a"));

        var aircraftTypeLabel = new JLabel("AIRCRAFT TYPE");
        aircraftTypeValue.putClientProperty(FlatClientProperties.STYLE_CLASS, "h4");

        var aircraftRegLabel = new JLabel("REGISTRATION");
        aircraftRegValue.putClientProperty(FlatClientProperties.STYLE_CLASS, "h4");

        var aircraftCompLabel = new JLabel("COMPANY");
        aircraftCompValue.putClientProperty(FlatClientProperties.STYLE_CLASS, "h4");

        mainSection.add(aircraftTypeLabel);
        mainSection.add(aircraftTypeValue);
        mainSection.add(new JSeparator(), "growx");
        mainSection.add(aircraftRegLabel);
        mainSection.add(aircraftRegValue);
        mainSection.add(new JSeparator(), "growx");
        mainSection.add(aircraftCompLabel);
        mainSection.add(aircraftCompValue);

        var dirSection = new JPanel();
        dirSection.setLayout(new MigLayout("wrap 2, insets 0", "[grow][grow]", "[][]"));
        dirSection.setBackground(Color.decode("#2a2a2a"));

        barometricAlt.putClientProperty(FlatClientProperties.STYLE_CLASS, "h4");
        verticalSpeed.putClientProperty(FlatClientProperties.STYLE_CLASS, "h4");
        gpsAlt.putClientProperty(FlatClientProperties.STYLE_CLASS, "h4");
        track.putClientProperty(FlatClientProperties.STYLE_CLASS, "h4");
        var barometricAltField = createFieldPanel(new JLabel("BAROMETRIC ALT."), barometricAlt);
        var verticalSpeedField = createFieldPanel(new JLabel("VERTICAL SPEED"), verticalSpeed);
        var gpsAltField = createFieldPanel(new JLabel("GPS ALTITUDE"), gpsAlt);
        var trackField = createFieldPanel(new JLabel("TRACK"), track);
        dirSection.add(barometricAltField);
        dirSection.add(verticalSpeedField);
        dirSection.add(gpsAltField);
        dirSection.add(trackField);

        mainSection.add(new JSeparator(), "growx");
        mainSection.add(dirSection);
        mainSection.add(new JSeparator(), "growx");

        var speedSection = new JPanel();
        speedSection.setLayout(new MigLayout("wrap 2, insets 0", "[grow][grow]", "[][]"));
        speedSection.setBackground(Color.decode("#2a2a2a"));

        groundSpeed.putClientProperty(FlatClientProperties.STYLE_CLASS, "h4");
        trueSpeed.putClientProperty(FlatClientProperties.STYLE_CLASS, "h4");
        indicatedSpeed.putClientProperty(FlatClientProperties.STYLE_CLASS, "h4");
        machSpeed.putClientProperty(FlatClientProperties.STYLE_CLASS, "h4");
        var groundSpeedField = createFieldPanel(new JLabel("GROUND SPEED"), groundSpeed);
        var trueSpeedField = createFieldPanel(new JLabel("TRUE AIRSPEED"), trueSpeed);
        var indicatedSpeedLabel = createFieldPanel(new JLabel("INDICATED AIRSPEED"), indicatedSpeed);
        var machSpeedLabel = createFieldPanel(new JLabel("MACH"), machSpeed);
        speedSection.add(groundSpeedField);
        speedSection.add(trueSpeedField);
        speedSection.add(indicatedSpeedLabel);
        speedSection.add(machSpeedLabel);

        mainSection.add(speedSection);
        mainSection.add(new JSeparator(), "growx");

        var dataSourceSection = new JPanel();
        dataSourceSection.setLayout(new MigLayout("wrap 2, insets 0", "[grow][grow]", "[][]"));
        dataSourceSection.setBackground(Color.decode("#2a2a2a"));

        sourceIcao.putClientProperty(FlatClientProperties.STYLE_CLASS, "h4");
        sourceSquawk.putClientProperty(FlatClientProperties.STYLE_CLASS, "h4");
        sourceLat.putClientProperty(FlatClientProperties.STYLE_CLASS, "h4");
        sourceLon.putClientProperty(FlatClientProperties.STYLE_CLASS, "h4");

        var icao = createFieldPanel(new JLabel("ICAO 24-BIT ADDRESS"), sourceIcao);
        var squawk = createFieldPanel(new JLabel("SQUAWK"), sourceSquawk);
        var lat = createFieldPanel(new JLabel("LATITUDE"), sourceLat);
        var lon = createFieldPanel(new JLabel("LONGITUDE"), sourceLon);

        dataSourceSection.add(icao);
        dataSourceSection.add(squawk);
        dataSourceSection.add(lat);
        dataSourceSection.add(lon);

        mainSection.add(dataSourceSection);

        add(header, "growx");
        add(thumbnailLabel, "growx");
        add(mainSection, "growx");
    }

    public void display(Aircraft aircraft) {
        this.aircraft = aircraft;

        headerFlightLabel.setText(aircraft.getFlight());

        if (aircraft.getT() == null) {
            headerTypeLabel.setVisible(false);
            aircraftTypeValue.setText("N/A (N/A)");
        } else {
            headerTypeLabel.setText(aircraft.getT());
            aircraftTypeValue.setText("%s (%s)".formatted(aircraft.getDesc(), aircraft.getT()));

            if (!headerTypeLabel.isVisible())
                headerTypeLabel.setVisible(true);

        }

        aircraftRegValue.setText(aircraft.getR());

        var country = IcaoRange.getCountry(aircraft.getHex());
        var countryCode = (country == null) ? "xy" : country.getCountryCode();
        var countryName = (country == null) ? "N/A" : country.getCountry();
        var resource = Resources.get("/flags/" + countryCode + ".png");

        if (resource != null) {
            aircraftRegValue.setIcon(new ImageIcon(resource));
            aircraftRegValue.setToolTipText(countryName);
        }

        aircraftCompValue.setText(aircraft.getOwnOp() != null ? aircraft.getOwnOp() : "N/A");

        barometricAlt.setText(aircraft.getAltBaro() != null ? aircraft.getAltBaro().equals("ground")
                ? aircraft.getAltBaro() : aircraft.getAltBaro() + " ft" : "N/A");
        verticalSpeed.setText(aircraft.getBaroRate() != null ? aircraft.getBaroRate() + " fpm" : "N/A");
        gpsAlt.setText("N/A");
        track.setText(aircraft.getTrack() != null ? aircraft.getTrack().intValue() + "°" : "N/A");

        groundSpeed.setText(aircraft.getGs() != null ? aircraft.getGs().intValue() + " kts" : "N/A");
        trueSpeed.setText(aircraft.getTas() != null ? aircraft.getTas() + " kts" : "N/A");
        indicatedSpeed.setText(aircraft.getIas() != null ? aircraft.getIas() + " kts" : "N/A");
        machSpeed.setText(aircraft.getMach() != null ? String.valueOf(aircraft.getMach()) : "N/A");

        sourceIcao.setText(aircraft.getHex() != null ? aircraft.getHex().toUpperCase() : "N/A");
        sourceSquawk.setText(aircraft.getSquawk() != null ? aircraft.getSquawk() : "N/A");
        sourceLat.setText(String.valueOf(aircraft.getLat()));
        sourceLon.setText(String.valueOf(aircraft.getLon()));
    }

    public void displayPicture() {
        Path target = Paths.get(TinyRadar.of().getThumbnailsDir().toString(), aircraft.getR() + ".jpg");

        var image = new ImageIcon(target.toString());
        thumbnailLabel.setIcon(new ImageIcon(scaleImage(image)));
    }

    public void displayPlaceholderThumbnail() {
        thumbnailLabel.setIcon(noImage);
    }

    @Subscribe
    @SuppressWarnings("unused")
    public void onAircraftSelectionEvent(AircraftSelectionEvent event) {
        if (event.aircraft().equals(aircraft))
            return;

        if (aircraft != null)
            displayPlaceholderThumbnail();

        aircraft = event.aircraft();
        display(aircraft);

        Path target = Paths.get(TinyRadar.of().getThumbnailsDir().toString(), aircraft.getR() + ".jpg");
        if (Files.notExists(target)) {
            CompletableFuture.runAsync(() -> {
                PlaneSpotterService service = new PlaneSpotterService();
                var response = service.get(aircraft.getR());

                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    var obj = objectMapper.readValue(response, PlaneSpottersResponse.class);

                    if (obj.getPhotos() == null)
                        return;

                    var future = HttpFileDownloader.asyncDownload(obj.getPhotos().getFirst().getThumbnailLarge().getSrc(), target);
                    future.thenAccept(result -> displayPicture());
                } catch (JsonProcessingException ex) {
                    throw new TinyRadarException(ex);
                }
            });
        } else {
            displayPicture();
        }

    }

    @Subscribe
    @SuppressWarnings("unused")
    public void onFlightsUpdateEvent(FlightsUpdateEvent event) {
        if (aircraft == null)
            return;

        var optionalAircraft = event.flights().stream()
                .filter(obj -> obj.getHex().equals(aircraft.getHex()))
                .findFirst();

        optionalAircraft.ifPresent(this::display);
    }

    private JPanel createFieldPanel(Container title, Container value) {
        var field = new JPanel();
        field.setLayout(new MigLayout("wrap 1, insets 0", "[grow]", "[]2[]"));
        field.setBackground(Color.decode("#2a2a2a"));
        field.add(title);
        field.add(value);
        return field;
    }

    private Image scaleImage(ImageIcon image) {
        Image originalImage = image.getImage();

        int maxWidth = 200;
        int originalWidth = image.getIconWidth();
        int originalHeight = image.getIconHeight();

        if (originalWidth > maxWidth) {
            int newHeight = (originalHeight * maxWidth) / originalWidth;
            return originalImage.getScaledInstance(maxWidth, newHeight, Image.SCALE_SMOOTH);
        } else {
            return originalImage;
        }
    }

}
