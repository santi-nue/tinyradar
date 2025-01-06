package dev.mf1.tinyradar.core;

import com.google.common.eventbus.EventBus;
import dev.mf1.tinyradar.core.oa.Airport;
import dev.mf1.tinyradar.core.oa.AirportLookup;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public final class TinyRadar {

    public static WGS84 pos;
    public static int range;
    public static int zoom = 11;

    @Getter
    private List<Airport> airportList = new ArrayList<>();

    @Getter
    private final List<Airport> airportInRangeList = new CopyOnWriteArrayList<>();

    @Getter
    private final Path home = Paths.get(System.getProperty("user.home"), ".tinyradar");

    @Getter
    private final Path thumbnailsDir = Paths.get(home.toString(), "thumbnails");

    @Getter
    private final Path tilesDir = Paths.get(home.toString(), "tiles");

    public static final EventBus BUS = new EventBus();

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private static TinyRadar instance;

    private TinyRadar() {
        initHomeFolder();
    }

    public static TinyRadar of() {
        if (instance == null) {
            instance = new TinyRadar();
        }

        return instance;
    }

    public void launch() {
        var service = new Service();
        scheduler.scheduleAtFixedRate(service::request, 1, 2, TimeUnit.SECONDS);
    }

    public void restart() {

    }

    public void loadAirports() {
        CompletableFuture
                .runAsync(() -> {
                    airportList = CsvReader.read("/airports.csv");
                    log.info("Loaded {} airports", airportList.size());
                })
                .thenRun(this::updateAirportsInRange);
    }

    public void updateAirportsInRange() {
        CompletableFuture.runAsync(() -> {
            airportInRangeList.clear();
            airportInRangeList.addAll(AirportLookup.filterInRange(airportList));
        });
    }

    private void initHomeFolder() {
        createFolder(home);
        createFolder(thumbnailsDir);
        createFolder(tilesDir);
    }

    private void createFolder(Path path) {
        if (Files.notExists(path)) {
            log.info("Creating " + path);

            try {
                Files.createDirectory(path);
            } catch (IOException e) {
                throw new TinyRadarException(e);
            }
        }
    }

}
