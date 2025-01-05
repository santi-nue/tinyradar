package dev.mf1.tinyradar.core.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mf1.tinyradar.core.al.Aircraft;
import dev.mf1.tinyradar.core.al.Response;

import java.io.IOException;
import java.util.List;

public final class DummyObjects {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private DummyObjects() {
    }

    public static List<Aircraft> getList() {
        try {
            var a = objectMapper.readValue(DummyObjects.class.getClassLoader().getResourceAsStream("response.json"), Response.class);
            return a.getAc();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
