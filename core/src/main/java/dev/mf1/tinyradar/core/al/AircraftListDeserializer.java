package dev.mf1.tinyradar.core.al;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class AircraftListDeserializer extends JsonDeserializer<List<Aircraft>> {

    @Override
    public List<Aircraft> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        ArrayNode arrayNode = p.getCodec().readTree(p);
        List<Aircraft> result = new ArrayList<>();

        arrayNode.forEach(item -> {
            var a = new Aircraft();
            a.setHex(item.get("hex").asText());
            a.setType(item.get("type").asText());

            result.add(new Aircraft());
        });

        return result;
    }

}
