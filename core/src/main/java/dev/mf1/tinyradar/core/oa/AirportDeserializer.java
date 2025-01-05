package dev.mf1.tinyradar.core.oa;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class AirportDeserializer extends JsonDeserializer<Airport> {

    @Override
    public Airport deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);

        var airport = new Airport();
        airport.setId(node.get("id").asText());
        airport.setIdent(node.get("ident").asText());
        airport.setType(Airport.Type.of(node.get("type").asText()));
        airport.setName(node.get("name").asText());
        airport.setLatitude(node.get("latitude_deg").asDouble());
        airport.setLongitude(node.get("longitude_deg").asDouble());
        airport.setElevation(node.get("elevation_ft").asInt());

        return airport;
    }

}
