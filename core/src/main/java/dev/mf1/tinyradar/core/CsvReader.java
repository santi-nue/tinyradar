package dev.mf1.tinyradar.core;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import dev.mf1.tinyradar.core.oa.Airport;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public final class CsvReader {

    private CsvReader() {
    }

    public static List<Airport> read(String filename) {
        CsvMapper mapper = new CsvMapper();
        List<Airport> list = new ArrayList<>();

        try (MappingIterator<Airport> it = mapper.readerFor(Airport.class)
                .with(CsvSchema.emptySchema().withHeader())
                .readValues(CsvReader.class.getResourceAsStream(filename))) {

            while (it.hasNext()) {
                var airport = it.next();

                if (airport.getType() == Airport.Type.LARGE || airport.getType() == Airport.Type.MEDIUM) {
                    list.add(airport);
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return list;

    }

}
