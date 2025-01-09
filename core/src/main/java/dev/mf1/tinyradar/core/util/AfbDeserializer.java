package dev.mf1.tinyradar.core.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import dev.mf1.tinyradar.core.TinyRadar;
import dev.mf1.tinyradar.core.WGS84;

import java.io.IOException;

public class AfbDeserializer extends JsonDeserializer<TinyRadar.Afb> {

    @Override
    public TinyRadar.Afb deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);

        var name = node.get("name").asText();
        var area = node.get("area").asText();
        var org = node.get("org").asText();
        var lat = (float) node.get("latitude").asDouble();
        var lon = (float) node.get("longitude").asDouble();

        return new TinyRadar.Afb(name, area, org, new WGS84(lat, lon));
    }

}
