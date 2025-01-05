package dev.mf1.tinyradar.core.al;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mf1.tinyradar.core.WGS84;
import org.junit.jupiter.api.Test;

class AirplanesLiveServiceTest {

    @Test
    void demo() throws JsonProcessingException {
        AirplanesLiveService service = new AirplanesLiveService();

        var pos = new WGS84(35.6728f, 139.8174f);

        ObjectMapper mapper = new ObjectMapper();
        var response = mapper.readValue(service.get(pos, 25f), Response.class);

        System.out.println(response);
    }

}