package dev.mf1.tinyradar.core.util;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import dev.mf1.tinyradar.core.oa.Airport;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Slf4j
public final class CsvReader {

    private CsvReader() {
    }

    public static <T> List<T> read(String filename, Class<T> clazz, Predicate<T> filter) {
        CsvMapper mapper = new CsvMapper();
        List<T> list = new ArrayList<>();

        try (MappingIterator<T> it = mapper.readerFor(clazz)
                .with(CsvSchema.emptySchema().withHeader())
                .readValues(CsvReader.class.getResourceAsStream(filename))) {

            while (it.hasNext()) {
                var item = it.next();
                if (filter == null || filter.test(item)) {
                    list.add(item);
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return list;
    }

}
